package com.mineralidentificationservice.repository;

import com.mineralidentificationservice.model.UserAccount;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {
}
