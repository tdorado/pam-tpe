package com.td.wallendarbackend.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "charges")
public class Charge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private ApplicationUser owner;

    private String title;

    @OneToMany(fetch = FetchType.EAGER)
    private List<ApplicationUser> debtors;

    private double amount;

    private Date date;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Group group;
}
