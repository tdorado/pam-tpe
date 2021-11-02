package com.td.wallendar.utils.mappers;

import com.td.wallendar.dtos.response.GroupResponse;
import com.td.wallendar.models.Group;

import java.util.ArrayList;
import java.util.List;

public class GroupMapper {

    public static List<Group> toModel(List<GroupResponse> groupsResponses) {
        List<Group> groups = new ArrayList<>();
        for (GroupResponse group : groupsResponses) {
            groups.add(toModel(group));
        }
        return groups;
    }

    public static Group toModel(GroupResponse groupResponse) {
        return new Group(
                groupResponse.getId(),
                groupResponse.getTitle(),
                ApplicationUserMapper.toModel(groupResponse.getOwner()),
                ApplicationUserMapper.toModel(groupResponse.getMembers()),
                ChargeMapper.toModel(groupResponse.getCharges()),
                DebtMapper.toModel(groupResponse.getDebts()),
                PaymentMapper.toModel(groupResponse.getPayments())
        );
    }

}
