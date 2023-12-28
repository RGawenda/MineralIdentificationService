package com.mineralidentificationservice.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "Tags")
public class Tags {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id")
    private Long id;

    @OneToMany(mappedBy = "tagId", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<TagsFoundMineral> tagsFoundMineralsList;

    @Column(name = "TagName", columnDefinition = "VARCHAR(30)", nullable = false)
    private String tagName;
}
