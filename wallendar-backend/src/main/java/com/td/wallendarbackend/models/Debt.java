package com.td.wallendarbackend.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "debts")
public class Debt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private ApplicationUser from;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private ApplicationUser to;

    private double amount;

    @OneToOne(fetch = FetchType.EAGER)
    private Debt reverseDebt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Group group;

    @OneToMany(fetch = FetchType.EAGER)
    private Set<DebtDetail> details;

    public Debt(ApplicationUser from, ApplicationUser to, Group group) {
        this.from = from;
        this.to = to;
        this.amount = 0;
        this.group = group;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Debt debt = (Debt) o;
        return id == debt.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
