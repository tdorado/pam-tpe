package com.td.wallendarbackend.services;

import com.td.wallendarbackend.dtos.requests.ChargeRequest;
import com.td.wallendarbackend.dtos.responses.GroupResponse;
import com.td.wallendarbackend.models.ApplicationUser;
import com.td.wallendarbackend.models.Charge;
import com.td.wallendarbackend.models.Debt;
import com.td.wallendarbackend.models.Group;
import com.td.wallendarbackend.repositories.ApplicationUserRepository;
import com.td.wallendarbackend.repositories.ChargeRepository;
import com.td.wallendarbackend.repositories.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

@Component
public class ChargeService {
    private final ChargeRepository chargeRepository;
    private final ApplicationUserRepository applicationUserRepository;
    private final GroupRepository groupRepository;

    @Autowired
    public ChargeService(ChargeRepository chargeRepository, ApplicationUserRepository applicationUserRepository, GroupRepository groupRepository) {
        this.chargeRepository = chargeRepository;
        this.applicationUserRepository = applicationUserRepository;
        this.groupRepository = groupRepository;
    }

    public GroupResponse addCharge(long groupId, ChargeRequest chargeRequest) {
        Group group = groupRepository.findById(groupId);
        if (group == null) {
            return null;
        }

        ApplicationUser owner = applicationUserRepository.findById(chargeRequest.getOwnerId());
        if (owner == null) {
            return null;
        }

        Charge charge = new Charge(chargeRequest.getTitle(), owner, chargeRequest.getAmount(),
                Calendar.getInstance().getTime(), group);
        chargeRepository.save(charge);
        if (group.getCharges() == null) {
            group.setCharges(new HashSet<>());
        }
        group.getCharges().add(charge);

        userPaidAmountToAllMembers(owner, chargeRequest.getAmount(), group.getDebts(), group.getMembers());

        groupRepository.save(group);

        return new GroupResponse(group);
    }

    private void userPaidAmountToAllMembers(ApplicationUser payer, double amount, Set<Debt> debts, Set<ApplicationUser> members) {
        double amountPerMember = amount / members.size();

        for (Debt debt : debts) {
            if (debt.getFrom().equals(payer)) {
                double amountPayerOwes = debt.getAmount() - amountPerMember;
                if (amountPayerOwes < 0) {
                    debt.setAmount(0);
                    Debt reverseDebt = debt.getReverseDebt();
                    reverseDebt.setAmount(reverseDebt.getAmount() + (-amountPayerOwes));
                } else {
                    debt.setAmount(amountPayerOwes);
                }
            }
        }
    }
}
