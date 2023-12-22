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
        List<Minerals> minerals =  mineralRepository.findMineralsByMineralName(name);
        if(minerals != null && !minerals.isEmpty()){
            return minerals.get(0);
        }
        log.info("error not found mineral"+ name);
        return new Minerals();
    }


    @Transactional
    public List<String> getAllMineralNames() {
        String jpql = "SELECT DISTINCT m.mineralName FROM Minerals m";
        TypedQuery<String> query = entityManager.createQuery(jpql, String.class);
        return query.getResultList();
    }

}
