package com.td.wallendarbackend.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_aliases")
public class UserAlias {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private ApplicationUser applicationUser;

    private String alias;

    public UserAlias(ApplicationUser applicationUser, String alias) {
        this.applicationUser = applicationUser;
        this.alias = alias;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserAlias userAlias = (UserAlias) o;
        return id == userAlias.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
