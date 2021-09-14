package com.td.wallendar.repositories.mappers;

import com.td.wallendar.dtos.response.GroupResponse;
import com.td.wallendar.dtos.response.GroupsResponse;
import com.td.wallendar.models.Group;

import java.util.ArrayList;
import java.util.List;

public class GroupsMapper {
    public static List<Group> toModel(final GroupsResponse groupsResponse) {
        List<Group> result = new ArrayList<>();
        for (GroupResponse groupResponse : groupsResponse.getGroups()) {
            result.add(GroupMapper.toModel(groupResponse));
        }
        return result;
    }
}
