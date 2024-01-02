package com.mineralidentificationservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@Entity
@Table(name = "Sharing")
public class Sharing {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "userIdFrom")
    private UserAccount userFrom;

    @ManyToOne
    @JoinColumn(name = "userIdTo")
    private UserAccount userTo;

}
