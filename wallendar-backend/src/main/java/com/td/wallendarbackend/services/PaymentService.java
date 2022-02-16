package com.td.wallendarbackend.services;

import com.td.wallendarbackend.dtos.requests.AddPaymentRequest;
import com.td.wallendarbackend.dtos.responses.GroupResponse;
import com.td.wallendarbackend.models.ApplicationUser;
import com.td.wallendarbackend.models.Debt;
import com.td.wallendarbackend.models.Group;
import com.td.wallendarbackend.models.Payment;
import com.td.wallendarbackend.repositories.ApplicationUserRepository;
import com.td.wallendarbackend.repositories.DebtRepository;
import com.td.wallendarbackend.repositories.GroupRepository;
import com.td.wallendarbackend.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.HashSet;

@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final DebtRepository debtRepository;
    private final GroupRepository groupRepository;
    private final ApplicationUserRepository applicationUserRepository;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository, DebtRepository debtRepository, GroupRepository groupRepository, ApplicationUserRepository applicationUserRepository) {
        this.paymentRepository = paymentRepository;
        this.debtRepository = debtRepository;
        this.groupRepository = groupRepository;
        this.applicationUserRepository = applicationUserRepository;
    }

    public GroupResponse addPayment(long groupId, AddPaymentRequest addPaymentRequest) {
        Group group = groupRepository.findById(groupId);
        if (group == null) {
            return null;
        }

        ApplicationUser fromUser = applicationUserRepository.findById(addPaymentRequest.getFromUserId());
        if (fromUser == null) {
            return null;
        }

        ApplicationUser toUser = applicationUserRepository.findById(addPaymentRequest.getToUserId());
        if (toUser == null) {
            return null;
        }

        Payment payment = new Payment(fromUser, toUser, addPaymentRequest.getAmount(),
                Calendar.getInstance().getTime(), group);
        paymentRepository.save(payment);
        if (group.getPayments() == null) {
            group.setPayments(new HashSet<>());
        }
        group.getPayments().add(payment);

        Debt debt = debtRepository.findByFromAndToAndGroup(fromUser, toUser, group);
        fromUserPaidAmountToUser(addPaymentRequest.getAmount(), debt);

        groupRepository.save(group);

        return new GroupResponse(group);
    }

    private void fromUserPaidAmountToUser(double amount, Debt debt) {
        double amountFromUserOwes = debt.getAmount() - amount;
        if (amountFromUserOwes < 0) {
            debt.setAmount(0);
            Debt reverseDebt = debt.getReverseDebt();
            reverseDebt.setAmount(reverseDebt.getAmount() + (-amountFromUserOwes));
        } else {
            debt.setAmount(amountFromUserOwes);
        }
    }
}
