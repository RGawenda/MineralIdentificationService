package com.mineralidentificationservice.service;

import com.mineralidentificationservice.model.FoundMineral;
import com.mineralidentificationservice.model.UserAccount;
import com.mineralidentificationservice.repository.UserAccountRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class UserAccountService {

    private final UserAccountRepository userAccountRepository;

    @Autowired
    public UserAccountService(UserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
    }

    @Transactional
    public UserAccount addRecord(UserAccount userAccount){
        return userAccountRepository.save(userAccount);
    }

    @Transactional
    public UserAccount getRecord(Long id){
        return userAccountRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Entity not found with id: " + id));
    }

    @Transactional
    public UserAccount updateRecord(Long id, UserAccount userAccount){
        if (!userAccountRepository.existsById(id)) {
            throw new EntityNotFoundException("Entity not found with id: " + id);
        }
        userAccount.setId(id);
        return userAccountRepository.save(userAccount);
    }

    @Transactional
    public void deleteRecord(Long id){
        if (!userAccountRepository.existsById(id)) {
            throw new EntityNotFoundException("Entity not found with id: " + id);
        }
        userAccountRepository.deleteById(id);
    }

}

