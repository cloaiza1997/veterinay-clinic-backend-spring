package com.test.veterinary.clinic.repository;

import com.test.veterinary.clinic.model.ClinicHistoryDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClinicHistoryDetailRepository extends JpaRepository<ClinicHistoryDetail, Long> {
    /**
     * Consulta los detalles de una historia clínica
     *
     * @param clinicHistoryId Id de la historia clínica para agrupar los detralles
     * @return Listado de detalles
     */
    @Query(nativeQuery = true, value = "SELECT * " +
            "FROM clinic_histories_detail " +
            "WHERE clinic_history_id = :clinicHistoryId " +
            "ORDER BY date_time")
    List<ClinicHistoryDetail> findAllByClinicHistoryId(@Param(value = "clinicHistoryId") Long clinicHistoryId);

}