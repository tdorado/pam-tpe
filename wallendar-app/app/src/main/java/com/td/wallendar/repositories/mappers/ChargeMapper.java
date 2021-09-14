package com.td.wallendar.repositories.mappers;

import com.td.wallendar.dtos.response.ChargeResponse;
import com.td.wallendar.models.Charge;
import com.td.wallendar.models.EqualCharge;
import com.td.wallendar.models.PercentageCharge;
import com.td.wallendar.models.UnequalCharge;
import com.td.wallendar.models.User;

import java.util.ArrayList;
import java.util.List;

public class ChargeMapper {
    public static Charge toModel(ChargeResponse chargeResponse) {
        Charge charge;
        switch (chargeResponse.getChargeType()) {
            case "UNEQUALLY":
                charge = new UnequalCharge();
                break;
            case "PERCENTAGE":
                charge = new PercentageCharge();
                break;
            default:
                // includes EQUALLY
                charge = new EqualCharge();
                charge.setId(chargeResponse.getChargeId());

                User owner = new User();
                owner.setEmail(chargeResponse.getOwner());

                charge.setTitle(chargeResponse.getTitle());

                List<User> debtors = new ArrayList<>();
                for(String u : chargeResponse.getDebtors()){
                    User user = new User();
                    user.setEmail(u);
                    debtors.add(user);
                }
                charge.setDebtors(debtors);

                charge.setAmount(chargeResponse.getAmount());

                charge.setDate(chargeResponse.getDate());
        }
        return charge;
    }
}
