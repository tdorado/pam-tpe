package com.td.wallendarbackend.repositories;

import com.td.wallendarbackend.models.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationUserRepository extends JpaRepository<ApplicationUser, Long> {
    ApplicationUser findByEmail(String email);
}
