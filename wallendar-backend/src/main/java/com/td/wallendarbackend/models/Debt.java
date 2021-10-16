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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Group group;
}
