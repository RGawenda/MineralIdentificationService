package com.mineralidentificationservice.repository;


import com.mineralidentificationservice.model.FoundMineral;
import com.mineralidentificationservice.model.Tags;
import com.mineralidentificationservice.model.TagsFoundMineral;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TagsFoundMineralRepository extends JpaRepository<TagsFoundMineral, Long> {
    List<TagsFoundMineral> findTagsFoundMineralsByFoundMineralId(FoundMineral foundMineral);

    List<TagsFoundMineral> deleteTagsFoundMineralsByFoundMineralId(FoundMineral foundMineral);

    List<TagsFoundMineral> deleteTagsFoundMineralByFoundMineralIdAndTagId(FoundMineral foundMineral, Tags nameToDelete);

    Optional<TagsFoundMineral> findByFoundMineralIdAndTagId(FoundMineral mineral, Tags tag);

}