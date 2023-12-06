package com.mineralidentificationservice.model;

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
    @Column(name = "ID")
    private Long id;

    @OneToMany(mappedBy = "mineralId", cascade = CascadeType.ALL)
    private List<FoundMineral> minerals;

    @Column(name = "Name", columnDefinition = "VARCHAR(20)")
    private String mineralName;

    @Column(name = "Mohs_scale", columnDefinition = "VARCHAR(6)")
    private String mohsScale;

    @Column(name = "CHEMICAL_FORMULA", columnDefinition = "VARCHAR(12)")
    private String chemicalFormula;

    @Column(name = "OCCURRENCE_PLACE", columnDefinition = "VARCHAR(40)")
    private String occurrencePlace;
}
