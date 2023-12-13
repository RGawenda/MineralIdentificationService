package com.mineralidentificationservice.repository;

import com.mineralidentificationservice.model.MineralImages;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MineralImagesRepository extends JpaRepository<MineralImages, Long> {
}

