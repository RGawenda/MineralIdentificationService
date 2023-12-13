package com.mineralidentificationservice.service;

import com.mineralidentificationservice.model.Minerals;
import com.mineralidentificationservice.model.Tags;
import com.mineralidentificationservice.repository.TagsRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@Slf4j
public class TagsService {
    private final TagsRepository tagsRepository;
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    public TagsService(TagsRepository tagsRepository) {
        this.tagsRepository = tagsRepository;
    }

    @Transactional
    public Tags addTag(Tags tags) {
        return tagsRepository.save(tags);
    }

    @Transactional
    public Tags getTag(Long id) {
        return tagsRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Entity not found with id: " + id));
    }

    @Transactional
    public Tags updateTag(Long id, Tags tags) {
        if (!tagsRepository.existsById(id)) {
            throw new EntityNotFoundException("Entity not found with id: " + id);
        }
        tags.setId(id);
        return tagsRepository.save(tags);
    }

    @Transactional
    public void deleteTag(Long id) {
        if (!tagsRepository.existsById(id)) {
            throw new EntityNotFoundException("Entity not found with id: " + id);
        }
        tagsRepository.deleteById(id);
    }

    @Transactional
    public Tags getMineralByName(String name) {
        String jpql = "SELECT m FROM Tags m WHERE m.tagName IN :name";
        TypedQuery<Tags> query = entityManager.createQuery(jpql, Tags.class);
        query.setParameter("name", name);
        return query.getSingleResult();
    }
}
