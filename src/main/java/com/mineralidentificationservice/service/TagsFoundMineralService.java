package com.mineralidentificationservice.service;

import com.mineralidentificationservice.model.FoundMineral;
import com.mineralidentificationservice.model.Tags;
import com.mineralidentificationservice.model.TagsFoundMineral;
import com.mineralidentificationservice.repository.TagsFoundMineralRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
    public void deleteRecordsByFoundMineral(FoundMineral id) {
        tagsFoundMineralRepository.deleteTagsFoundMineralsByFoundMineralId(id);
    }

    @Transactional
    public void deleteRecordsByFoundMineralAndTagName(FoundMineral id, List<Tags> stringList) {
        for(Tags tagName:stringList){
            tagsFoundMineralRepository.deleteTagsFoundMineralByFoundMineralIdAndTagId(id, tagName);
        }
    }

    @Transactional
    public Optional<TagsFoundMineral> checkIfRelations(FoundMineral id, Tags tagsId) {
        return tagsFoundMineralRepository.findByFoundMineralIdAndTagId(id, tagsId);
    }

    @Transactional
    public void deleteRecord(Long id) {
        if (!tagsFoundMineralRepository.existsById(id)) {
            throw new EntityNotFoundException("Entity not found with id: " + id);
        }
        tagsFoundMineralRepository.deleteById(id);
    }
}
