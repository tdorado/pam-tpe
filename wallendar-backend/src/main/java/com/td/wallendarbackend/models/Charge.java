package com.td.wallendarbackend.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "charges")
public class Charge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private ApplicationUser owner;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<ApplicationUser> debtors;

    private double amount;

    private Date date;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Group group;

    public Charge(String title, ApplicationUser owner, Set<ApplicationUser> debtors, double amount, Date date, Group group) {
        this.title = title;
        this.owner = owner;
        this.debtors = debtors;
        this.amount = amount;
        this.date = date;
        this.group = group;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Charge charge = (Charge) o;
        return id == charge.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
