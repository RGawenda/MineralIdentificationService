package com.mineralidentificationservice.repository;

import com.mineralidentificationservice.model.Minerals;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MineralRepository extends JpaRepository<Minerals, Long> {
    List<Minerals> findMineralsByMineralName(String name);

}
