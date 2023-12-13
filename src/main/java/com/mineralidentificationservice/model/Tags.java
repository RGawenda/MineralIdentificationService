package com.mineralidentificationservice.model;

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
@Table(name = "Tags")
public class Tags {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id")
    private Long id;

    @OneToMany(mappedBy = "tagId", cascade = CascadeType.ALL)
    private List<TagsFoundMineral> tagsFoundMineralsList;

    @ManyToOne
    @JoinColumn(name = "MineralId", referencedColumnName = "Id", nullable = false)
    private Minerals mineralId;

    @Column(name = "TagName", columnDefinition = "VARCHAR(30)", nullable = false)
    private String tagName;
}
