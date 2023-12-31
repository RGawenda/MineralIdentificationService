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
    @JoinColumn(name = "user_id_from")
    private UserAccount userFrom;

    @ManyToOne
    @JoinColumn(name = "user_id_to")
    private UserAccount userTo;

}
