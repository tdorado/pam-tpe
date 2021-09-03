package com.td.wallendar.models;

public class Task {
    private User userAssigned;
    private String title;
    private String description;

    public Task(User userAssigned, String title, String description) {
        this.userAssigned = userAssigned;
        this.title= title;
        this.description = description;
    }

    public User getUserAssigned() {
        return userAssigned;
    }

    public void setUserAssigned(User userAssigned) {
        this.userAssigned = userAssigned;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
