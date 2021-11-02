package com.td.wallendar.models;

import java.io.Serializable;
import java.util.Objects;


public class UserAlias implements Serializable {
    private long id;
    private ApplicationUser applicationUser;

    private String alias;

    public UserAlias(long id, ApplicationUser applicationUser, String alias) {
        this.id = id;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ApplicationUser getApplicationUser() {
        return applicationUser;
    }

    public void setApplicationUser(ApplicationUser applicationUser) {
        this.applicationUser = applicationUser;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}
