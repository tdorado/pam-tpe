package com.td.wallendarbackend.services;

import com.td.wallendarbackend.dtos.requests.AddChargeRequest;
import com.td.wallendarbackend.dtos.responses.ChargeResponse;
import com.td.wallendarbackend.models.*;
import com.td.wallendarbackend.repositories.ApplicationUserRepository;
import com.td.wallendarbackend.repositories.ChargeRepository;
import com.td.wallendarbackend.repositories.DebtDetailRepository;
import com.td.wallendarbackend.repositories.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ChargeService {
    private final ChargeRepository chargeRepository;
    private final ApplicationUserRepository applicationUserRepository;
    private final GroupRepository groupRepository;
    private final DebtDetailRepository debtDetailRepository;

    @Autowired
    public ChargeService(ChargeRepository chargeRepository, ApplicationUserRepository applicationUserRepository,
                         GroupRepository groupRepository, DebtDetailRepository debtDetailRepository) {
        this.chargeRepository = chargeRepository;
        this.applicationUserRepository = applicationUserRepository;
        this.groupRepository = groupRepository;
        this.debtDetailRepository = debtDetailRepository;
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

        group.setDebts(userPaidAmountToAllMembers(owner, addChargeRequest.getAmount(), group.getDebts(), group.getMembers()));

        saveDebtDetails(addChargeRequest.getAmount() / group.getMembers().size(), group.getDebts().stream().filter(it -> it.getFrom() != owner), charge);

        groupRepository.save(group);

        return new ChargeResponse(charge);
    }

    private void saveDebtDetails(final Double amount,
                                 final Stream<Debt> debtStream,
                                 final Charge charge) {
        final Set<DebtDetail> debtDetails = debtStream.map(it -> new DebtDetail(it, charge, amount)).collect(Collectors.toSet());
        debtDetailRepository.saveAll(debtDetails);
    }

    private Set<Debt> userPaidAmountToAllMembers(final ApplicationUser payer, final double amount,
                                                 final Set<Debt> debts, final Set<ApplicationUser> members) {
        double amountPerMember = amount / members.size();

        for (Debt debt : debts) {
            if (debt.getFrom().equals(payer)) {
                // FIXME duplicate code and function must return something, this should be a map or something like this
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
        return debts;
    }
}
