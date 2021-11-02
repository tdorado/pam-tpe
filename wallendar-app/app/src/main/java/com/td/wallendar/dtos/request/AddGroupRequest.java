package com.td.wallendar.dtos.request;

public class AddGroupRequest {

    private String title;
    private Long ownerId;

    public AddGroupRequest() {
    }

    public AddGroupRequest(String title, Long ownerId) {
        this.title = title;
        this.ownerId = ownerId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }
}
