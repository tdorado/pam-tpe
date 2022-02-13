package com.td.wallendarbackend.models;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue(value = "1")
@Table(name = "events")
public class Event extends Group {

    private Date date;

    public Event(final String title, final ApplicationUser owner, final Date date) {
        super(title, owner);
        this.date = date;
    }

}
