package com.td.wallendar.dtos.request;

import java.util.Set;

public class AddMembersRequest {
    private Set<Long> userIds;

    public AddMembersRequest(Set<Long> userIds) {
        this.userIds = userIds;
    }

    public Set<Long> getUserIds() {
        return userIds;
    }

    public void setUserIds(Set<Long> userIds) {
        this.userIds = userIds;
    }
}
