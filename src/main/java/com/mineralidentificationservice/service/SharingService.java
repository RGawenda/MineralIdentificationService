package com.mineralidentificationservice.service;

import com.mineralidentificationservice.model.FoundMineral;
import com.mineralidentificationservice.model.Sharing;
import com.mineralidentificationservice.model.Tags;
import com.mineralidentificationservice.model.UserAccount;
import com.mineralidentificationservice.repository.SharingRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Service
public class SharingService {
    private final SharingRepository sharingRepository;

    @Autowired
    public SharingService(SharingRepository sharingRepository) {
        this.sharingRepository = sharingRepository;
    }

    @Transactional
    public Sharing add(Sharing sharing) {
        return sharingRepository.save(sharing);
    }

    @Transactional
    public List<String> getByUserTO(Long id) {
        return sharingRepository.findUsernamesSharingWithUserTo(id);
    }

    @Transactional
    public List<String> getByUserFrom(Long id) {
        return sharingRepository.findUsernamesSharingWithUserFrom(id);
    }

    @Transactional
    public void deleteRecord(UserAccount userFrom, UserAccount userTo) {
        sharingRepository.deleteSharingByUserFromAndUserTo(userFrom, userTo);
    }
}
