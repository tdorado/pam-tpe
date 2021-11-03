package com.td.wallendar.dtos.request;

public class FindApplicationUserByEmailRequest {
    private String email;

    public FindApplicationUserByEmailRequest(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
