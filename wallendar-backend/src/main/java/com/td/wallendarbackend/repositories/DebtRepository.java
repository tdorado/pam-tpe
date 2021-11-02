package com.td.wallendarbackend.repositories;

import com.td.wallendarbackend.models.ApplicationUser;
import com.td.wallendarbackend.models.Debt;
import com.td.wallendarbackend.models.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DebtRepository extends JpaRepository<Debt, Long> {
    Debt findByFromAndToAndGroup(ApplicationUser from, ApplicationUser to, Group group);

    @Query("SELECT d from Debt d where d.amount > 0 and (:applicationUser = d.from or :applicationUser = d.to)")
    List<Debt> findAllFromApplicationUser(@Param("applicationUser") ApplicationUser applicationUser);
}
