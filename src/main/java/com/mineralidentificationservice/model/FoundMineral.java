package com.mineralidentificationservice.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.annotation.Nonnull;
import jakarta.persistence.*;

import lombok.*;

import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "FoundMineral")
public class FoundMineral {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MineralId", referencedColumnName = "Id", nullable = false)
    private Minerals mineralId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UserId", referencedColumnName = "Id", nullable = false)
    private UserAccount accountId;

    @Column(name = "Name", columnDefinition = "VARCHAR(30)", nullable = false)
    private String name;

    @Column(name = "Comment", columnDefinition = "TEXT")
    private String comment;

    @Column(name = "DiscoveryPlace", columnDefinition = "TEXT")
    private String discoveryPlace;

    @Column(name = "Value", columnDefinition = "numeric")
    private Double value;

    @Column(name = "Weight", columnDefinition = "numeric")
    private Double weight;

    @Column(name = "Size", columnDefinition = "numeric")
    private Double size;

    @Column(name = "Inclusion", columnDefinition = "VARCHAR(300)")
    private String inclusion;

    @Column(name = "Clarity", columnDefinition = "VARCHAR(200)")
    private String clarity;

    @OneToMany(mappedBy = "foundMineralID", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<MineralImages> mineralImages;

    @OneToMany(mappedBy = "foundMineralId", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<TagsFoundMineral> tags;

}
