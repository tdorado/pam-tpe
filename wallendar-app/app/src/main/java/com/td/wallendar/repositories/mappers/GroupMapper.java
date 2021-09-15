package com.td.wallendar.repositories.mappers;

import com.td.wallendar.dtos.response.ChargeResponse;
import com.td.wallendar.dtos.response.BalanceResponse;
import com.td.wallendar.dtos.response.GroupResponse;
import com.td.wallendar.models.Charge;
import com.td.wallendar.models.Debt;
import com.td.wallendar.models.Group;
import com.td.wallendar.models.User;

import java.util.ArrayList;
import java.util.List;

public class GroupMapper {
    public static Group toModel(GroupResponse groupResponse) {
        Group group = new Group();

        group.setTitle(groupResponse.getTitle());

        User owner = new User();
        owner.setEmail(groupResponse.getOwner());
        group.setOwner(owner);

        List<User> usersList = new ArrayList<>();
        for (String user : groupResponse.getUsers()) {
            User newUser = new User();
            newUser.setEmail(user);
            usersList.add(newUser);
        }
        group.setUsers(usersList);

        List<Debt> debts = new ArrayList<>();
        for (BalanceResponse balance : groupResponse.getBalances()) {
            debts.add(BalanceMapper.toModel(balance));
        }
        group.setDebts(debts);

        List<Charge> charges = new ArrayList<>();
        for (ChargeResponse charge : groupResponse.getCharges()) {
            charges.add(ChargeMapper.toModel(charge));
        }
        group.setCharges(charges);


        return group;
    }
}
