package com.test.veterinary.clinic.repository;

import com.test.veterinary.clinic.model.ClinicHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClinicHistoryRepository extends JpaRepository<ClinicHistory, Long> {
}