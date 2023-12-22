package com.mineralidentificationservice.model;


import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @Column(name = "Id")
    private Long id;

    @OneToMany(mappedBy = "accountId",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<FoundMineral> minerals;

    @Column(name = "UserName", columnDefinition = "VARCHAR(10)", nullable = false)
    private String username;

    @Column(name = "Login", columnDefinition = "VARCHAR(10)", nullable = false)
    private String login;

    @Column(name = "Password", columnDefinition = "VARCHAR(18)", nullable = false)
    private String password;
}
