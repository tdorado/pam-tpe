package com.td.wallendarbackend.services;

import com.td.wallendarbackend.dtos.requests.AddChargeRequest;
import com.td.wallendarbackend.dtos.responses.ChargeResponse;
import com.td.wallendarbackend.dtos.responses.GroupResponse;
import com.td.wallendarbackend.models.ApplicationUser;
import com.td.wallendarbackend.models.Charge;
import com.td.wallendarbackend.models.Debt;
import com.td.wallendarbackend.models.Group;
import com.td.wallendarbackend.repositories.ApplicationUserRepository;
import com.td.wallendarbackend.repositories.ChargeRepository;
import com.td.wallendarbackend.repositories.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

@Service
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

    public ChargeResponse addCharge(long groupId, AddChargeRequest addChargeRequest) {
        Group group = groupRepository.findById(groupId);
        if (group == null) {
            return null;
        }

        ApplicationUser owner = applicationUserRepository.findById(addChargeRequest.getOwnerId());
        if (owner == null) {
            return null;
        }

        Charge charge = new Charge(addChargeRequest.getTitle(), owner, new HashSet<>(group.getMembers()), addChargeRequest.getAmount(),
                Calendar.getInstance().getTime(), group);
        chargeRepository.save(charge);
        if (group.getCharges() == null) {
            group.setCharges(new HashSet<>());
        }
        group.getCharges().add(charge);

        userPaidAmountToAllMembers(owner, addChargeRequest.getAmount(), group.getDebts(), group.getMembers());

        groupRepository.save(group);

        return new ChargeResponse(charge);
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
