package com.mineralidentificationservice.service;

import com.jayway.jsonpath.internal.function.numeric.Min;
import com.mineralidentificationservice.model.Minerals;
import com.mineralidentificationservice.repository.MineralRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class MineralService {
    @PersistenceContext
    private EntityManager entityManager;
    private final MineralRepository mineralRepository;

    @Autowired
    public MineralService(MineralRepository mineralRepository) {
        this.mineralRepository = mineralRepository;
    }

    @Transactional
    public Minerals addRecord(Minerals minerals) {
        return mineralRepository.save(minerals);
    }

    @Transactional
    public Minerals getRecord(Long id) {
        return mineralRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Entity not found with id: " + id));
    }

    @Transactional
    public Minerals updateRecord(Long id, Minerals minerals) {
        if (!mineralRepository.existsById(id)) {
            throw new EntityNotFoundException("Entity not found with id: " + id);
        }
        minerals.setId(id);
        return mineralRepository.save(minerals);
    }

    @Transactional
    public void deleteRecord(Long id) {
        if (!mineralRepository.existsById(id)) {
            throw new EntityNotFoundException("Entity not found with id: " + id);
        }
        mineralRepository.deleteById(id);
    }

    public List<Minerals> getMineralsByNames(List<String> names) {
        String jpql = "SELECT m FROM Minerals m WHERE m.mineralName IN :names";
        TypedQuery<Minerals> query = entityManager.createQuery(jpql, Minerals.class);
        query.setParameter("names", names);
        return query.getResultList();
    }

}
