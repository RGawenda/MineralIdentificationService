package com.mineralidentificationservice.service;

import com.mineralidentificationservice.enums.AccountType;
import com.mineralidentificationservice.model.UserAccount;
import com.mineralidentificationservice.repository.UserAccountRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class UserAccountService {

    private final UserAccountRepository userAccountRepository;

    @Autowired
    public UserAccountService(UserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
    }

    @Transactional
    public UserAccount addAccount(UserAccount userAccount) {
        return userAccountRepository.save(userAccount);
    }

    @Transactional
    public List<UserAccount> getUsersByAccountType(AccountType accountType) {
        return userAccountRepository.findUserAccountByAccountType(accountType);
    }

    @Transactional
    public UserAccount getAccount(Long id) {
        return userAccountRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Entity not found with id: " + id));
    }

    @Transactional
    public UserAccount getAccountByUsername(String name) {
        return userAccountRepository.findByUsername(name).orElseThrow(() -> new EntityNotFoundException("Entity not found with id: " + name));
    }

    @Transactional
    public UserAccount updateAccount(Long id, UserAccount userAccount) {
        if (!userAccountRepository.existsById(id)) {
            throw new EntityNotFoundException("Entity not found with id: " + id);
        }
        userAccount.setId(id);
        return userAccountRepository.save(userAccount);
    }

    @Transactional
    public void deleteAccount(Long id) {
        if (!userAccountRepository.existsById(id)) {
            throw new EntityNotFoundException("Entity not found with id: " + id);
        }
        userAccountRepository.deleteById(id);
    }

}

