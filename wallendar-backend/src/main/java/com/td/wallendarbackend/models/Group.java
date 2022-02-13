package com.td.wallendarbackend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "groups")
@DiscriminatorColumn(name = "type")
@DiscriminatorValue(value = "0")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private ApplicationUser owner;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<ApplicationUser> members;

    @OneToMany(fetch = FetchType.EAGER)
    private Set<Charge> charges;

    @OneToMany(fetch = FetchType.EAGER)
    private Set<Debt> debts;

    @OneToMany(fetch = FetchType.EAGER)
    private Set<Payment> payments;

    @Column(insertable = false, updatable = false)
    private String type;

    public Group(String title, ApplicationUser owner) {
        this.title = title;
        this.owner = owner;
        this.members = Collections.singleton(owner);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group group = (Group) o;
        return id == group.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
