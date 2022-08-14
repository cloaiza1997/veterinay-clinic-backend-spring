package com.test.veterinary.clinic.repository;

import com.test.veterinary.clinic.interfaces.PetInterface;
import com.test.veterinary.clinic.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PetRepository extends JpaRepository<Pet, Long> {
    @Query(nativeQuery = true, value = "SELECT " +
            "pt.id AS id, pt.name AS name, pt.gender AS gender, pt.breed AS breed, pt.user_id AS userId, " +
            "ch.id AS clinicHistoryId, us.first_name || ' ' || us.last_name AS userName " +
            "FROM pets AS pt " +
            "LEFT JOIN clinic_histories AS ch ON ch.pet_id = pt.id " +
            "LEFT JOIN users AS us ON us.id = pt.user_id")
    List<PetInterface> findAllWithClinicHistory();
}