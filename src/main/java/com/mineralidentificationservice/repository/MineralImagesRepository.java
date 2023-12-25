package com.mineralidentificationservice.repository;

import com.mineralidentificationservice.model.FoundMineral;
import com.mineralidentificationservice.model.MineralImages;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MineralImagesRepository extends JpaRepository<MineralImages, Long> {
    List<MineralImages> findMineralImagesByFoundMineralID(FoundMineral foundMineral);
}

