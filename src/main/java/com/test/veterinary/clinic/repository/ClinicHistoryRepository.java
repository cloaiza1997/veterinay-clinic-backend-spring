package com.test.veterinary.clinic.repository;

import com.test.veterinary.clinic.model.ClinicHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ClinicHistoryRepository extends JpaRepository<ClinicHistory, Long> {
    @Query(nativeQuery = true, value = "SELECT * FROM clinic_histories WHERE id <> :clinicHistoryId AND pet_id = :petId")
    Optional<ClinicHistory> findByPetId(@Param(value = "clinicHistoryId") Long clinicHistoryId, @Param(value = "petId") Long petId);
}