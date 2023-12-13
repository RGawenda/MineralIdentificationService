package com.mineralidentificationservice.service;

import com.mineralidentificationservice.model.TagsFoundMineral;
import com.mineralidentificationservice.repository.TagsFoundMineralRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@Slf4j
public class TagsFoundMineralService {
    private final TagsFoundMineralRepository tagsFoundMineralRepository;

    @Autowired
    public TagsFoundMineralService(TagsFoundMineralRepository tagsFoundMineralRepository) {
        this.tagsFoundMineralRepository = tagsFoundMineralRepository;
    }

    @Transactional
    public TagsFoundMineral addRecord(TagsFoundMineral tagsFoundMineral) {
        return tagsFoundMineralRepository.save(tagsFoundMineral);
    }

    @Transactional
    public TagsFoundMineral getRecord(Long id) {
        return tagsFoundMineralRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Entity not found with id: " + id));
    }

    @Transactional
    public TagsFoundMineral updateRecord(Long id, TagsFoundMineral tagsFoundMineral) {
        if (!tagsFoundMineralRepository.existsById(id)) {
            throw new EntityNotFoundException("Entity not found with id: " + id);
        }
        tagsFoundMineral.setId(id);
        return tagsFoundMineralRepository.save(tagsFoundMineral);
    }

    @Transactional
    public void deleteRecord(Long id) {
        if (!tagsFoundMineralRepository.existsById(id)) {
            throw new EntityNotFoundException("Entity not found with id: " + id);
        }
        tagsFoundMineralRepository.deleteById(id);
    }
}
