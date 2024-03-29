package com.mineralidentificationservice.repository;

import com.mineralidentificationservice.enums.AccountType;
import com.mineralidentificationservice.model.UserAccount;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {
    Optional<UserAccount> findByUsername(String username);

    List<UserAccount> findUserAccountByAccountType(AccountType accountType);
}
