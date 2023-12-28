package com.mineralidentificationservice.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;


@Setter
@Getter
@Entity
@Table(name = "MineralImages")
public class MineralImages {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FoundMineralID", referencedColumnName = "Id", nullable = false)
    private FoundMineral foundMineralID;

    @Column(name = "Path", columnDefinition = "VARCHAR", nullable = false)
    private String path;

}
