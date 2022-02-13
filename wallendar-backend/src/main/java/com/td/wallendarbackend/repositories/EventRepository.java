package com.td.wallendarbackend.repositories;

import com.td.wallendarbackend.models.ApplicationUser;
import com.td.wallendarbackend.models.Event;
import com.td.wallendarbackend.models.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

    @Query("SELECT e FROM Event e WHERE :applicationUser MEMBER OF e.members AND TYPE(e) = Event")
    List<Event> findEventsByApplicationUserId(@Param("applicationUser") ApplicationUser applicationUser);

}
