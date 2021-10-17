package com.td.wallendarbackend.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private ApplicationUser from;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private ApplicationUser to;

    private double amount;

    private Date date;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Group group;

    public Payment(ApplicationUser from, ApplicationUser to, double amount, Date date, Group group) {
        this.from = from;
        this.to = to;
        this.amount = amount;
        this.date = date;
        this.group = group;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payment payment = (Payment) o;
        return id == payment.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
