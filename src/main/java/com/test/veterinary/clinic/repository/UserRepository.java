package com.test.veterinary.clinic.repository;

import com.test.veterinary.clinic.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}