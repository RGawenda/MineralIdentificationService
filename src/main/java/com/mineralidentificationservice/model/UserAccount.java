package com.mineralidentificationservice.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Account")
public class UserAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @OneToMany(mappedBy = "accountId", cascade = CascadeType.ALL)
    private List<FoundMineral> minerals;

    @Column(name = "USER_NAME", columnDefinition = "VARCHAR(10)")
    private String username;

    @Column(name = "LOGIN", columnDefinition = "VARCHAR(10)")
    private String login;

    @Column(name = "PASSWORD", columnDefinition = "VARCHAR(18)")
    private String password;
}
