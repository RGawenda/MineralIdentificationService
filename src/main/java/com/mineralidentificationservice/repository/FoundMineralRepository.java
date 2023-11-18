package com.mineralidentificationservice.repository;

import com.mineralidentificationservice.model.FoundMineral;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface FoundMineralRepository extends JpaRepository<FoundMineral, Long> {
}
