package com.td.wallendarbackend.services;

import com.td.wallendarbackend.dtos.requests.GroupRequest;
import com.td.wallendarbackend.models.ApplicationUser;
import com.td.wallendarbackend.models.Group;
import com.td.wallendarbackend.repositories.ApplicationUserRepository;
import com.td.wallendarbackend.repositories.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GroupService {

    private final ApplicationUserRepository applicationUserRepository;
    private final GroupRepository groupRepository;

    @Autowired
    public GroupService(ApplicationUserRepository applicationUserRepository, GroupRepository groupRepository) {
        this.applicationUserRepository = applicationUserRepository;
        this.groupRepository = groupRepository;
    }

    public Group findById(long id){
        return groupRepository.findById(id);
    }

    public List<Group> findGroupsByApplicationUserId(long applicationUserId){
        ApplicationUser applicationUser = new ApplicationUser();
        applicationUser.setId(applicationUserId);
        return groupRepository.findGroupsByApplicationUserId(applicationUser);
    }

    public void createGroup(GroupRequest groupRequest){
        ApplicationUser owner = applicationUserRepository.findById(groupRequest.getOwnerId());
        Group group = new Group(groupRequest.getTitle(), owner);
        groupRepository.save(group);
    }
}
