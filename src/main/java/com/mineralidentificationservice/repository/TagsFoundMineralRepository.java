package com.mineralidentificationservice.repository;


import com.mineralidentificationservice.model.TagsFoundMineral;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagsFoundMineralRepository extends JpaRepository<TagsFoundMineral, Long> {
}