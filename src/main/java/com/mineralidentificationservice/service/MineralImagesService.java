package com.mineralidentificationservice.service;

import com.mineralidentificationservice.model.FoundMineral;
import com.mineralidentificationservice.model.MineralImages;
import com.mineralidentificationservice.repository.MineralImagesRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class MineralImagesService {
    private final MineralImagesRepository mineralImagesRepository;

    @Autowired
    public MineralImagesService(MineralImagesRepository mineralImagesRepository) {
        this.mineralImagesRepository = mineralImagesRepository;
    }

    @Transactional
    public MineralImages addMineralImages(MineralImages mineralImages) {
        return mineralImagesRepository.save(mineralImages);
    }

    @Transactional
    public List<MineralImages> addAllMineralImages(List<MineralImages> mineralImagesList) {
        return mineralImagesRepository.saveAll(mineralImagesList);
    }

    @Transactional
    public MineralImages getMineralImage(Long id) {
        return mineralImagesRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Entity not found with id: " + id));
    }

    @Transactional
    public List<MineralImages> getMineralImages(FoundMineral foundMineral) {
        return mineralImagesRepository.findMineralImagesByFoundMineralID(foundMineral);
    }


    @Transactional
    public MineralImages updateMineralImages(Long id, MineralImages mineralImages) {
        if (!mineralImagesRepository.existsById(id)) {
            throw new EntityNotFoundException("Entity not found with id: " + id);
        }
        mineralImages.setId(id);
        return mineralImagesRepository.save(mineralImages);
    }

    @Transactional
    public void deleteMineralImages(Long id) {
        if (!mineralImagesRepository.existsById(id)) {
            throw new EntityNotFoundException("Entity not found with id: " + id);
        }
        mineralImagesRepository.deleteById(id);
    }
}
