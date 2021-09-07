package com.td.wallendar.models;

import java.util.Date;
import java.util.List;

public class Event extends Group {

    private Date date;
    private List<Task> tasks;

    public Event(final Long id,
                 final String title,
                 final User owner,
                 final List<User> users,
                 final List<Charge> charges,
                 final List<MoneyTransaction> moneyTransactions,
                 final Date date,
                 final List<Task> tasks) {
        super(id, title, owner, users, charges, moneyTransactions);
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
