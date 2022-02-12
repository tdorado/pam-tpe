package com.td.wallendarbackend.models;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "debt_detail")
public class DebtDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Debt debt;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Charge charge;
    private Double amount;

    public DebtDetail(final Debt debt, final Charge charge, final Double amount) {
        this.debt = debt;
        this.charge = charge;
        this.amount = amount;
    }
}
