package com.td.wallendar.dtos.response;

import com.td.wallendar.models.UserAlias;

public class UserAliasResponse {
    private long id;
    private String alias;

    public UserAliasResponse() {
    }

    public UserAliasResponse(UserAlias userAlias) {
        this.id = userAlias.getId();
        this.alias = userAlias.getAlias();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}
