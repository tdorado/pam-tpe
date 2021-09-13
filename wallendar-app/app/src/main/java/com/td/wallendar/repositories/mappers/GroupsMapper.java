package com.td.wallendar.repositories.mappers;

import com.td.wallendar.dtos.response.GroupResponse;
import com.td.wallendar.dtos.response.GroupsResponse;
import com.td.wallendar.models.Group;
import com.td.wallendar.models.User;

import java.util.ArrayList;
import java.util.List;

public class GroupsMapper {
    public static List<Group> toModel(final GroupsResponse groupsResponse){
        List<Group> result = new ArrayList<>();
        for(GroupResponse groupResponse : groupsResponse.getGroups()){
            Group group = new Group();

            group.setTitle(groupResponse.getTitle());

            User owner = new User();
            owner.setEmail(groupResponse.getOwner());
            group.setOwner(owner);

            List<User> usersList = new ArrayList<>();
            for(String user : groupResponse.getUsers()){
                User newUser =  new User();
                newUser.setEmail(user);
                usersList.add(newUser);
            }
            group.setUsers(usersList);

            result.add(group);
        }
        return result;
    }
}
