package com.test.veterinary.clinic.repository;

import com.test.veterinary.clinic.model.Worker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkerRepository extends JpaRepository<Worker, Long> {
}