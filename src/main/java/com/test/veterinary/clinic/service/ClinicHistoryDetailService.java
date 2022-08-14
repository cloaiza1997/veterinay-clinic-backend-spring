package com.test.veterinary.clinic.service;

import com.test.veterinary.clinic.model.ClinicHistoryDetail;
import com.test.veterinary.clinic.repository.ClinicHistoryDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClinicHistoryDetailService {
    private final ClinicHistoryDetailRepository clinicHistoryDetailRepository;

    @Autowired
    public ClinicHistoryDetailService(ClinicHistoryDetailRepository clinicHistoryDetailRepository) {
        this.clinicHistoryDetailRepository = clinicHistoryDetailRepository;
    }

    public Boolean delete(Long clinicHistoryDetailId) {
        clinicHistoryDetailRepository.deleteById(clinicHistoryDetailId);

        return true;
    }

    public List<ClinicHistoryDetail> findAll() {
        return clinicHistoryDetailRepository.findAll();
    }

    public List<ClinicHistoryDetail> findAllByClinicHistoryId(Long clinicHistoryId) {
        return clinicHistoryDetailRepository.findAllByClinicHistoryId(clinicHistoryId);
    }

    public Optional<ClinicHistoryDetail> findById(Long clinicHistoryDetailId) {
        return clinicHistoryDetailRepository.findById(clinicHistoryDetailId);
    }

    public ClinicHistoryDetail save(ClinicHistoryDetail clinicHistoryDetail) {
        return clinicHistoryDetailRepository.save(clinicHistoryDetail);
    }

    public ClinicHistoryDetail update(Long clinicHistoryDetailId, ClinicHistoryDetail clinicHistoryDetail) {
        Optional<ClinicHistoryDetail> findClinicHistoryDetail = clinicHistoryDetailRepository.findById(clinicHistoryDetailId);

        if (findClinicHistoryDetail.isEmpty() || clinicHistoryDetailId != clinicHistoryDetail.getId()) {
            return null;
        }

        return clinicHistoryDetailRepository.save(clinicHistoryDetail);
    }
}
