package com.mineralidentificationservice.repository;

import com.mineralidentificationservice.model.FoundMineral;
import com.mineralidentificationservice.model.Minerals;
import com.mineralidentificationservice.model.Tags;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TagsRepository extends JpaRepository<Tags, Long> {
    List<Tags> findTagsByTagName(String name);

    List<Tags> findByTagsFoundMineralsListFoundMineralId(FoundMineral tagsFoundMineralsList_foundMineralId);

}
