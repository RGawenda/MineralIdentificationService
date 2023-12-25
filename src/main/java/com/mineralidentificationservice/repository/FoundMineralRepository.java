package com.mineralidentificationservice.repository;

import com.mineralidentificationservice.model.FoundMineral;

import com.mineralidentificationservice.model.UserAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoundMineralRepository extends JpaRepository<FoundMineral, Long> {
    Page<FoundMineral>findFoundMineralsByAccountId(UserAccount userAccount, Pageable pageable);
}
