package com.td.wallendarbackend.repositories;

import com.td.wallendarbackend.models.Charge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChargeRepository extends JpaRepository<Charge, Long> {
}
