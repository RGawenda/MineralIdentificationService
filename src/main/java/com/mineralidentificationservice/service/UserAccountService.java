package com.mineralidentificationservice.service;

import com.mineralidentificationservice.model.UserAccount;
import com.mineralidentificationservice.repository.UserAccountRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class UserAccountService {//implements UserDetailsService {

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
    public UserAccount getAccount(Long id) {
        return userAccountRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Entity not found with id: " + id));
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

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user = userAccountRepository.findByUsername(username)
//                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
//
//        return new org.springframework.security.core.userdetails.User(
//                user.getUsername(),
//                user.getPassword(),
//                true,
//                true,
//                true,
//                true,
//                getAuthorities("USER")
//        );
//    }
//
//    private List<GrantedAuthority> getAuthorities(String role) {
//        return Collections.singletonList(new SimpleGrantedAuthority(role));
//    }


}

