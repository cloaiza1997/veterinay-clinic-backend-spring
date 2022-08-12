package com.test.veterinary.clinic.repository;

import com.test.veterinary.clinic.model.ClinicHistoryDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClinicHistoryDetailRepository extends JpaRepository<ClinicHistoryDetail, Long> {
}