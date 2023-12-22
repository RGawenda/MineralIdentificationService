package com.mineralidentificationservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Minerals")
public class Minerals {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id")
    private Long id;
    @JsonIgnore
    @OneToMany(mappedBy = "mineralId",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<FoundMineral> foundMinerals;

    @Column(name = "Name", columnDefinition = "VARCHAR(20)", nullable = false)
    private String mineralName;

    @Column(name = "MohsScale", columnDefinition = "VARCHAR(6)")
    private Double mohsScale;

    @Column(name = "ChemicalFormula", columnDefinition = "VARCHAR(40)")
    private String chemicalFormula;

    @Column(name = "OccurrencePlace", columnDefinition = "VARCHAR(40)")
    private String occurrencePlace;
}
