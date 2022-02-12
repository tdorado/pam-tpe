package com.td.wallendarbackend.dtos.responses;


import com.td.wallendarbackend.models.DebtDetail;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DebtDetailResponse {
    private Double amount;
    private Date date;
    private String description;

    public DebtDetailResponse(final DebtDetail debtDetail) {
        this.amount = debtDetail.getAmount();
        this.date = debtDetail.getCharge().getDate();
        this.description = debtDetail.getCharge().getTitle();
    }
}
