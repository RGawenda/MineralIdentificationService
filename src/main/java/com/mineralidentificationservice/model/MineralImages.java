package com.mineralidentificationservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "MineralImages")
public class MineralImages {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "FoundMineralID", referencedColumnName = "Id", nullable = false)
    private FoundMineral foundMineralID;

    @Column(name = "Path", columnDefinition = "VARCHAR(30)", nullable = false)
    private String path;

    @Column(name = "DisplayOrder", columnDefinition = "VARCHAR(30)", nullable = false)
    private int displayOrder;
}
