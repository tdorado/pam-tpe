package com.td.wallendarbackend.repositories;

import com.td.wallendarbackend.models.ApplicationUser;
import com.td.wallendarbackend.models.Debt;
import com.td.wallendarbackend.models.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DebtRepository extends JpaRepository<Debt, Long> {
    Debt findByFromAndToAndGroup(ApplicationUser from, ApplicationUser to, Group group);
}
