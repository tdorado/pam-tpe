package com.td.wallendarbackend.dtos.responses;

import com.td.wallendarbackend.models.Event;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
public class EventResponse extends GroupResponse {
    private Date date;

    public EventResponse(final Event event) {
        super(event);
        this.date = event.getDate();
    }
}
