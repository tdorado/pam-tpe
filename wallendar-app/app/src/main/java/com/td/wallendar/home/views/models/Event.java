package com.td.wallendar.home.views.models;

import java.util.Date;
import java.util.List;

public class Event extends Group {

    private Date date;
    private List<Task> tasks;

    public Event(final String groupName,
                 final List<User> users,
                 final List<Debt> debts,
                 final Date date,
                 final List<Task> tasks) {
        super(groupName, users, debts);
        this.date = date;
        this.tasks = tasks;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}
