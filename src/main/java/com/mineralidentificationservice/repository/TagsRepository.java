package com.mineralidentificationservice.repository;

import com.mineralidentificationservice.model.FoundMineral;
import com.mineralidentificationservice.model.Minerals;
import com.mineralidentificationservice.model.Tags;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface TagsRepository extends JpaRepository<Tags, Long> {
    List<Tags> findTagsByTagName(String name);

    @Query("SELECT DISTINCT t FROM Tags t " +
            "JOIN t.tagsFoundMineralsList ft " +
            "JOIN ft.foundMineralId fm " +
            "JOIN fm.accountId u " +
            "WHERE u.id = :userId")
    Set<Tags> findDistinctTagsByUserId(@Param("userId") Long userId);

    List<Tags> findByTagsFoundMineralsListFoundMineralId(FoundMineral tagsFoundMineralsList_foundMineralId);

}
