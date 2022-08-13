package com.test.veterinary.clinic.repository;

import com.test.veterinary.clinic.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query(nativeQuery = true, value = "SELECT * FROM users WHERE id <> :userId AND document_number = :documentNumber")
    Optional<User> findByDocumentNumber(@Param(value = "userId") Long userId, @Param(value = "documentNumber") Integer documentNumber);
}