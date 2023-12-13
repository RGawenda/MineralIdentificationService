package com.mineralidentificationservice.service;

import com.mineralidentificationservice.model.FoundMineral;
import com.mineralidentificationservice.repository.FoundMineralRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class FoundMineralService {

    private final FoundMineralRepository foundMineralRepository;

    @Autowired
    public FoundMineralService(FoundMineralRepository foundMineralRepository) {
        this.foundMineralRepository = foundMineralRepository;
    }

    @Transactional
    public FoundMineral addMineralImages(FoundMineral foundMineral) {
        return foundMineralRepository.save(foundMineral);
    }

    @Transactional
    public FoundMineral getMineralImages(Long id) {
        return foundMineralRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Entity not found with id: " + id));
    }

    @Transactional
    public FoundMineral updateMineralImages(Long id, FoundMineral foundMineral) {
        if (!foundMineralRepository.existsById(id)) {
            throw new EntityNotFoundException("Entity not found with id: " + id);
        }
        foundMineral.setId(id);
        return foundMineralRepository.save(foundMineral);
    }

    @Transactional
    public void deleteMineralImages(Long id) {
        if (!foundMineralRepository.existsById(id)) {
            throw new EntityNotFoundException("Entity not found with id: " + id);
        }
        foundMineralRepository.deleteById(id);
    }
}
