package com.td.wallendarbackend.services;

import com.td.wallendarbackend.dtos.requests.GroupRequest;
import com.td.wallendarbackend.models.ApplicationUser;
import com.td.wallendarbackend.models.Group;
import com.td.wallendarbackend.repositories.ApplicationUserRepository;
import com.td.wallendarbackend.repositories.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
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
        ApplicationUser applicationUser = applicationUserRepository.findById(applicationUserId);
        return groupRepository.findGroupsByApplicationUserId(applicationUser);
    }

    public Group createGroup(GroupRequest groupRequest){
        ApplicationUser owner = applicationUserRepository.findById(groupRequest.getOwnerId());
        List<ApplicationUser> members = Collections.singletonList(owner);
        Group group = new Group(groupRequest.getTitle(), owner, members);

        groupRepository.save(group);
        return group;
    }
}
