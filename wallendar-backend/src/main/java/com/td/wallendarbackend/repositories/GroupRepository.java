package com.td.wallendarbackend.repositories;

import com.td.wallendarbackend.models.ApplicationUser;
import com.td.wallendarbackend.models.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GroupRepository  extends JpaRepository<Group, Long> {
    Group findById(long id);

    @Query("SELECT g from Group g where :applicationUser in (g.members)")
    List<Group> findGroupsByApplicationUserId(@Param("applicationUser")ApplicationUser applicationUser);
}
