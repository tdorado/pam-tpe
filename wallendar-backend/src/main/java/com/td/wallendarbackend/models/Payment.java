package com.td.wallendarbackend.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

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
}
