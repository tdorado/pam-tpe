package com.td.wallendarbackend.services;

import com.td.wallendarbackend.dtos.requests.AddMembersRequest;
import com.td.wallendarbackend.dtos.requests.GroupRequest;
import com.td.wallendarbackend.models.ApplicationUser;
import com.td.wallendarbackend.models.Group;
import com.td.wallendarbackend.repositories.ApplicationUserRepository;
import com.td.wallendarbackend.repositories.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

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
        if(applicationUser == null){
            return null;
        }
        return groupRepository.findGroupsByApplicationUserId(applicationUser);
    }

    public Group createGroup(GroupRequest groupRequest){
        ApplicationUser owner = applicationUserRepository.findById(groupRequest.getOwnerId());
        if(owner == null){
            return null;
        }
        Set<ApplicationUser> members = Collections.singleton(owner);
        Group group = new Group(groupRequest.getTitle(), owner, members);

        groupRepository.save(group);
        return group;
    }

    public Group addMembers(long groupId, AddMembersRequest addMembersRequest){
        Group group = groupRepository.findById(groupId);
        if(group == null){
            return null;
        }
        Set<ApplicationUser> membersToAdd = new HashSet<>();
        for(Long userId : addMembersRequest.getUserIds()){
            ApplicationUser user = applicationUserRepository.findById(userId.longValue());
            if(user == null){
                return null;
            }
            membersToAdd.add(user);
        }
        group.getMembers().addAll(membersToAdd);
        groupRepository.save(group);
        return group;
    }
}
