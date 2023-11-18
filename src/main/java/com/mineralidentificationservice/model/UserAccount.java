package com.mineralidentificationservice.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

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

    @Column(name = "USER_NAME", columnDefinition = "VARCHAR(10)")
    private String userName;
    @Lob
    @Column(name = "LOGIN", columnDefinition = "VARCHAR(10)")
    private String login;
    @Column(name = "PASSWORD", columnDefinition = "VARCHAR(18)")
    private String password;
}
