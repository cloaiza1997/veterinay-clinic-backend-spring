package com.test.veterinary.clinic.service;

import com.test.veterinary.clinic.model.ClinicHistory;
import com.test.veterinary.clinic.repository.ClinicHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClinicHistoryService {
    private final ClinicHistoryRepository clinicHistoryRepository;

    @Autowired
    public ClinicHistoryService(ClinicHistoryRepository clinicHistoryRepository) {
        this.clinicHistoryRepository = clinicHistoryRepository;
    }

    public Boolean clinicHistoryPetExists(Long clinicHistoryId, Long petId) {
        Optional<ClinicHistory> clinicHistory = clinicHistoryRepository.findByPetId(clinicHistoryId, petId);

        return !clinicHistory.isEmpty();
    }

    public Boolean delete(Long clinicHistoryId) {
        clinicHistoryRepository.deleteById(clinicHistoryId);

        return true;
    }

    public List<ClinicHistory> findAll() {
        return clinicHistoryRepository.findAll();
    }

    public Optional<ClinicHistory> findById(Long clinicHistoryId) {
        return clinicHistoryRepository.findById(clinicHistoryId);
    }

    public ClinicHistory save(ClinicHistory pet) {
        return clinicHistoryRepository.save(pet);
    }

    public ClinicHistory update(Long clinicHistoryId, ClinicHistory clinicHistory) {
        Optional<ClinicHistory> findClinicHistory = clinicHistoryRepository.findById(clinicHistoryId);

        if (findClinicHistory.isEmpty() || clinicHistoryId != clinicHistory.getId()) {
            return null;
        }

        return clinicHistoryRepository.save(clinicHistory);
    }
}
