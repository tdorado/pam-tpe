package com.td.wallendar.dtos.response;

import java.util.List;

public class GroupsResponse {
    private List<GroupResponse> groups;

    public GroupsResponse() {
    }

    public GroupsResponse(List<GroupResponse> groups) {
        this.groups = groups;
    }

    public List<GroupResponse> getGroups() {
        return groups;
    }

    public void setGroups(List<GroupResponse> groups) {
        this.groups = groups;
    }

}
