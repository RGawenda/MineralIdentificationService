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
@Table(name = "Minerals")
public class Minerals {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;
    @Column(name = "Name", columnDefinition = "VARCHAR(20)")
    private String mineralName;
    @Column(name = "VSS", columnDefinition = "VARCHAR(20)")
    private String vss;
    @Column(name = "Mohs_scale", columnDefinition = "VARCHAR(6)")
    private Double MohsScale;
    @Column(name = "CHEMICAL_FORMULA", columnDefinition = "VARCHAR(12)")
    private String ChemicalFormula;
    @Column(name = "OCCURANCE_PLACE", columnDefinition = "VARCHAR(40)")
    private String occurancePlace;
}
