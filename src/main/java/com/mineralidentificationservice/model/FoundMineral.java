package com.mineralidentificationservice.model;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "FoundMineral")
public class FoundMineral {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MINERAL_ID", referencedColumnName = "ID")
    private Minerals mineralId;

    @ManyToOne
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
    private UserAccount accountId;

    @Column(name = "NAME", columnDefinition = "VARCHAR(30)")
    private String name;

    @Column(name = "COMMENT", columnDefinition = "VARCHAR(30)")
    private String comment;

    @Column(name = "DISCOVERY_PLACE", columnDefinition = "VARCHAR(30)")
    private String discoveryPlace;

    @Column(name = "VALUE", columnDefinition = "VARCHAR(30)")
    private String value;

    @Column(name = "WEIGHT", columnDefinition = "VARCHAR(30)")
    private String weight;

    @Column(name = "SIZE", columnDefinition = "VARCHAR(30)")
    private String size;

    @Column(name = "INCLUSION", columnDefinition = "VARCHAR(30)")
    private String inclusion;

    @Column(name = "CLARITY", columnDefinition = "VARCHAR(30)")
    private String clarity;

    @Column(name = "PATHS")
    @ElementCollection
    private List<String> paths;

    @Column(name = "TAGS", columnDefinition = "VARCHAR(30)")
    @ElementCollection
    private List<String> tags;
}
