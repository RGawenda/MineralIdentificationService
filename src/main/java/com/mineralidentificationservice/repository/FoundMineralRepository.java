package com.mineralidentificationservice.repository;

import com.mineralidentificationservice.model.FoundMineral;

import com.mineralidentificationservice.model.UserAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface FoundMineralRepository extends JpaRepository<FoundMineral, Long>, JpaSpecificationExecutor<FoundMineral> {
    Page<FoundMineral> findAll(Specification<FoundMineral> specification, Pageable pageable);
}
