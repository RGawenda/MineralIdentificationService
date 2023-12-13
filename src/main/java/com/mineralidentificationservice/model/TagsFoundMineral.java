package com.mineralidentificationservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "TagsFoundMineral")
public class TagsFoundMineral {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "FoundMineralId", referencedColumnName = "Id", nullable = false)
    private FoundMineral foundMineralId;

    @ManyToOne
    @JoinColumn(name = "TagId", referencedColumnName = "Id", nullable = false)
    private Tags tagId;
}
