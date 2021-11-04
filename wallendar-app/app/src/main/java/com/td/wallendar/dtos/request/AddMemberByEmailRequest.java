package com.td.wallendar.dtos.request;

public class AddMemberByEmailRequest {
    private String email;

    public AddMemberByEmailRequest(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}