package com.td.wallendar.dtos.request;

public class AddUserAliasRequest {
    private String alias;

    public AddUserAliasRequest(String alias) {
        this.alias = alias;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}
