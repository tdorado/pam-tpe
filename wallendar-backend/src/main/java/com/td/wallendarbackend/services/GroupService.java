package com.td.wallendarbackend.services;

import com.td.wallendarbackend.dtos.requests.AddMembersRequest;
import com.td.wallendarbackend.dtos.requests.AddGroupRequest;
import com.td.wallendarbackend.dtos.responses.GroupResponse;
import com.td.wallendarbackend.models.ApplicationUser;
import com.td.wallendarbackend.models.Debt;
import com.td.wallendarbackend.models.Group;
import com.td.wallendarbackend.repositories.ApplicationUserRepository;
import com.td.wallendarbackend.repositories.DebtRepository;
import com.td.wallendarbackend.repositories.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GroupService {
    private final ApplicationUserRepository applicationUserRepository;
    private final GroupRepository groupRepository;
    private final DebtRepository debtRepository;

    @Autowired
    public GroupService(ApplicationUserRepository applicationUserRepository, GroupRepository groupRepository, DebtRepository debtRepository) {
        this.applicationUserRepository = applicationUserRepository;
        this.groupRepository = groupRepository;
        this.debtRepository = debtRepository;
    }

    public GroupResponse findById(long id) {
        Group group = groupRepository.findById(id);
        if (group == null) {
            return null;
        }
        return new GroupResponse(group);
    }

    public List<GroupResponse> findGroupsByApplicationUserId(long applicationUserId) {
        ApplicationUser applicationUser = applicationUserRepository.findById(applicationUserId);
        if (applicationUser == null) {
            return null;
        }
        List<Group> groups = groupRepository.findGroupsByApplicationUserId(applicationUser, "1");
        return groups.stream().map(GroupResponse::new).collect(Collectors.toList());
    }

    public GroupResponse createGroup(AddGroupRequest addGroupRequest) {
        ApplicationUser owner = applicationUserRepository.findById(addGroupRequest.getOwnerId());
        if (owner == null) {
            return null;
        }
        Group group = new Group(addGroupRequest.getTitle(), owner);

        groupRepository.save(group);
        return new GroupResponse(group);
    }

    public GroupResponse addMembers(long groupId, AddMembersRequest addMembersRequest) {
        Group group = groupRepository.findById(groupId);
        if (group == null) {
            return null;
        }
        Set<ApplicationUser> membersToAdd = new HashSet<>();
        Set<ApplicationUser> groupMembers = group.getMembers();
        for (String userEmail : addMembersRequest.getUserEmails()) {
            ApplicationUser user = applicationUserRepository.findByEmail(userEmail);
            if (user == null) {
                return null;
            }
            if (!groupMembers.contains(user)) {
                membersToAdd.add(user);
            }
        }

        if(membersToAdd.size() == 0){
            return new GroupResponse(group);
        }

        Set<Debt> groupDebts = group.getDebts();
        for (ApplicationUser member : groupMembers) {
            for (ApplicationUser memberToAdd : membersToAdd) {
                Debt fromDebt = new Debt(member, memberToAdd, group);
                Debt toDebt = new Debt(memberToAdd, member, group);
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

        group.getMembers().addAll(membersToAdd);
        groupRepository.save(group);
        return new GroupResponse(group);
    }
}
