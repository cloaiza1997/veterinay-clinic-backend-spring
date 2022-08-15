package com.test.veterinary.clinic.repository;

import com.test.veterinary.clinic.model.ClinicHistoryDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClinicHistoryDetailRepository extends JpaRepository<ClinicHistoryDetail, Long> {
    @Query(nativeQuery = true, value = "SELECT * " +
            "FROM clinic_histories_detail " +
            "WHERE clinic_history_id = :clinicHistoryId " +
            "ORDER BY date_time")
    List<ClinicHistoryDetail> findAllByClinicHistoryId(@Param(value = "clinicHistoryId") Long clinicHistoryId);

}