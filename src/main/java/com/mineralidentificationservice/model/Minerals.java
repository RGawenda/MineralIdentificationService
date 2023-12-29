package com.mineralidentificationservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import lombok.*;

import java.util.List;


@Setter
@Getter
@Entity
@Table(name = "Minerals")
public class Minerals {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id")
    private Long id;
    @JsonIgnore
    @OneToMany(mappedBy = "mineralId", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<FoundMineral> foundMinerals;

    @Column(name = "Name", columnDefinition = "VARCHAR(30)", nullable = false)
    private String mineralName;

    @Column(name = "MohsScale", columnDefinition = "numeric")
    private Double mohsScale;

    @Column(name = "ChemicalFormula", columnDefinition = "TEXT")
    private String chemicalFormula;

    @Column(name = "OccurrencePlace", columnDefinition = "TEXT")
    private String occurrencePlace;
}
