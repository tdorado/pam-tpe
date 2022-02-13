package com.td.wallendarbackend.repositories;

import com.td.wallendarbackend.models.ApplicationUser;
import com.td.wallendarbackend.models.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GroupRepository extends JpaRepository<Group, Long> {
    Group findById(long id);

    @Query("SELECT g FROM Group g WHERE :applicationUser MEMBER OF g.members AND g.type = :type")
    List<Group> findGroupsByApplicationUserId(@Param("applicationUser") ApplicationUser applicationUser, @Param("type") String type);
}
