package com.mineralidentificationservice.model;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @Column(name = "Id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MineralId", referencedColumnName = "Id", nullable = false)
    private Minerals mineralId;

    @ManyToOne
    @JoinColumn(name = "UserId", referencedColumnName = "Id", nullable = false)
    private UserAccount accountId;

    @Column(name = "Name", columnDefinition = "VARCHAR(30)", nullable = false)
    private String name;

    @Column(name = "Comment", columnDefinition = "VARCHAR(30)")
    private String comment;

    @Column(name = "DiscoveryPlace", columnDefinition = "VARCHAR(30)")
    private String discoveryPlace;

    @Column(name = "Value", columnDefinition = "VARCHAR(30)")
    private String value;

    @Column(name = "Weight", columnDefinition = "VARCHAR(30)")
    private String weight;

    @Column(name = "Size", columnDefinition = "VARCHAR(30)")
    private String size;

    @Column(name = "Inclusion", columnDefinition = "VARCHAR(30)")
    private String inclusion;

    @Column(name = "Clarity", columnDefinition = "VARCHAR(30)")
    private String clarity;

    @OneToMany(mappedBy = "foundMineralID", cascade = CascadeType.ALL)
    private List<MineralImages> mineralImages;

    @OneToMany(mappedBy = "foundMineralId", cascade = CascadeType.ALL)
    private List<TagsFoundMineral> tags;

}
