package com.td.wallendarbackend.services;

import com.td.wallendarbackend.dtos.requests.AddEventRequest;
import com.td.wallendarbackend.dtos.requests.AddGroupRequest;
import com.td.wallendarbackend.dtos.requests.AddMembersRequest;
import com.td.wallendarbackend.dtos.responses.EventResponse;
import com.td.wallendarbackend.dtos.responses.GroupResponse;
import com.td.wallendarbackend.models.ApplicationUser;
import com.td.wallendarbackend.models.Debt;
import com.td.wallendarbackend.models.Event;
import com.td.wallendarbackend.models.Group;
import com.td.wallendarbackend.repositories.ApplicationUserRepository;
import com.td.wallendarbackend.repositories.DebtRepository;
import com.td.wallendarbackend.repositories.EventRepository;
import com.td.wallendarbackend.repositories.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EventService {
    private final ApplicationUserRepository applicationUserRepository;
    private final EventRepository eventRepository;
    private final DebtRepository debtRepository;

    @Autowired
    public EventService(ApplicationUserRepository applicationUserRepository, EventRepository eventRepository, DebtRepository debtRepository) {
        this.applicationUserRepository = applicationUserRepository;
        this.eventRepository = eventRepository;
        this.debtRepository = debtRepository;
    }

    public EventResponse findById(long id) {
        Optional<Event> eventOptional = eventRepository.findById(id);
        if (eventOptional.isEmpty()) {
            return null;
        }
        return new EventResponse(eventOptional.get());
    }

    public List<EventResponse> findEventsByApplicationUserId(long applicationUserId) {
        ApplicationUser applicationUser = applicationUserRepository.findById(applicationUserId);
        if (applicationUser == null) {
            return null;
        }
        List<Event> events = eventRepository.findEventsByApplicationUserId(applicationUser);
        return events.stream().map(EventResponse::new).collect(Collectors.toList());
    }

    public EventResponse createEvent(AddEventRequest addEventRequest) {
        ApplicationUser owner = applicationUserRepository.findById(addEventRequest.getOwnerId());
        if (owner == null) {
            return null;
        }
        Event event = new Event(addEventRequest.getTitle(), owner, addEventRequest.getDate());

        eventRepository.save(event);
        return new EventResponse(event);
    }

    public boolean addMembers(long groupId, AddMembersRequest addMembersRequest) {
        Optional<Event> eventOptional = eventRepository.findById(groupId);
        if (eventOptional.isEmpty()) {
            return false;
        }
        Event event = eventOptional.get();
        Set<ApplicationUser> membersToAdd = new HashSet<>();
        Set<ApplicationUser> groupMembers = event.getMembers();
        for (String userEmail : addMembersRequest.getUserEmails()) {
            ApplicationUser user = applicationUserRepository.findByEmail(userEmail);
            if (user == null) {
                return false;
            }
            if (!groupMembers.contains(user)) {
                membersToAdd.add(user);
            }
        }

        if (membersToAdd.size() == 0) {
            return true;
        }

        Set<Debt> groupDebts = event.getDebts();
        for (ApplicationUser member : groupMembers) {
            for (ApplicationUser memberToAdd : membersToAdd) {
                Debt fromDebt = new Debt(member, memberToAdd, event);
                Debt toDebt = new Debt(memberToAdd, member, event);
                debtRepository.save(fromDebt);
                debtRepository.save(toDebt);
                //Aca me guardo una referencia al balace inverso, esto es solo para hacer mas rapida la carga de gastos
                fromDebt.setReverseDebt(toDebt);
                toDebt.setReverseDebt(fromDebt);
                //Tengo que volver a guardar esto aca, porque recien ahi ya tiene id ambos debt
                //Solamente es poco performante al agregar miembros, pero como eso se hace solamente 1 vez, es mejor
                //comparado con buscar el otro debt cada vez que agrego un nuevo gasto
                debtRepository.save(fromDebt);
                debtRepository.save(toDebt);
                groupDebts.add(fromDebt);
                groupDebts.add(toDebt);
            }
        }

        event.getMembers().addAll(membersToAdd);
        eventRepository.save(event);
        return true;
    }
}
