package com.td.wallendar.dtos.request;

import java.util.Set;

public class AddMembersRequest {
    private Set<String> userEmails;

    public AddMembersRequest(Set<String> userEmails) {
        this.userEmails = userEmails;
    }

    public Set<String> getUserEmails() {
        return userEmails;
    }

    public void setUserEmails(Set<String> userEmails) {
        this.userEmails = userEmails;
    }
}
