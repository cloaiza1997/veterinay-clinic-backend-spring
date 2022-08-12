package com.test.veterinary.clinic.repository;

import com.test.veterinary.clinic.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepository extends JpaRepository<Pet, Long> {

}