package com.mineralidentificationservice.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FoundMineralId", referencedColumnName = "Id", nullable = false)
    private FoundMineral foundMineralId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TagId", referencedColumnName = "Id", nullable = false)
    private Tags tagId;
}
