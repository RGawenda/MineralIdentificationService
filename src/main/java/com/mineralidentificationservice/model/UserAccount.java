package com.mineralidentificationservice.model;


import com.mineralidentificationservice.enums.AccountType;
import com.mineralidentificationservice.enums.Role;
import lombok.*;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "Account")
public class UserAccount implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id")
    private Long id;

    @OneToMany(mappedBy = "accountId", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<FoundMineral> minerals;

    @Column(name = "UserName", columnDefinition = "VARCHAR(20)", nullable = false)
    private String username;

    @Column(name = "Password", columnDefinition = "VARCHAR", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
