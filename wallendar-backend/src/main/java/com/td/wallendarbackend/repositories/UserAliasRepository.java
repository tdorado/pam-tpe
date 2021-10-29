package com.td.wallendarbackend.repositories;

import com.td.wallendarbackend.models.ApplicationUser;
import com.td.wallendarbackend.models.UserAlias;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserAliasRepository extends JpaRepository<UserAlias, Long> {
    List<UserAlias> findAllByApplicationUser(ApplicationUser applicationUser);
}
