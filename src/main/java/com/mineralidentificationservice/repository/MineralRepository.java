package com.mineralidentificationservice.repository;

import com.mineralidentificationservice.model.Minerals;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
public interface MineralRepository extends JpaRepository<Minerals, Long> {
}
