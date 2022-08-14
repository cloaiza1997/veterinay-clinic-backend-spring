package com.test.veterinary.clinic.repository;

import com.test.veterinary.clinic.model.Worker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface WorkerRepository extends JpaRepository<Worker, Long> {
    @Query(nativeQuery = true, value = "SELECT * FROM workers WHERE id <> :workerId AND document_number = :documentNumber")
    Optional<Worker> findByDocumentNumber(@Param(value = "workerId") Long workerId, @Param(value = "documentNumber") Integer documentNumber);
}