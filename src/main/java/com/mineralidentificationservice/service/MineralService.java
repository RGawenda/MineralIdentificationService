package com.mineralidentificationservice.service;

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
    public Minerals addMineral(Minerals minerals) {
        return mineralRepository.save(minerals);
    }

    @Transactional
    public Minerals getMineral(Long id) {
        return mineralRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Entity not found with id: " + id));
    }

    @Transactional
    public Minerals updateMineral(Long id, Minerals minerals) {
        if (!mineralRepository.existsById(id)) {
            throw new EntityNotFoundException("Entity not found with id: " + id);
        }
        minerals.setId(id);
        return mineralRepository.save(minerals);
    }

    @Transactional
    public void deleteMineral(Long id) {
        if (!mineralRepository.existsById(id)) {
            throw new EntityNotFoundException("Entity not found with id: " + id);
        }
        mineralRepository.deleteById(id);
    }
    @Transactional
    public List<Minerals> getMineralsByNames(List<String> names) {
        String jpql = "SELECT m FROM Minerals m WHERE m.mineralName IN :names";
        TypedQuery<Minerals> query = entityManager.createQuery(jpql, Minerals.class);
        query.setParameter("names", names);
        return query.getResultList();
    }

    @Transactional
    public Minerals getMineralByName(String name) {
        String jpql = "SELECT m FROM Minerals m WHERE m.mineralName IN :name";
        TypedQuery<Minerals> query = entityManager.createQuery(jpql, Minerals.class);
        query.setParameter("name", name);
        return query.getSingleResult();
    }

}
