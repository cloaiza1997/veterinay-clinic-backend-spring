package com.test.veterinary.clinic.service;

import com.test.veterinary.clinic.model.User;
import com.test.veterinary.clinic.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Boolean delete(Long userId) {
        userRepository.deleteById(userId);

        return true;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(Long userId) {
        return userRepository.findById(userId);
    }


    public User save(User user) {
        return userRepository.save(user);
    }

    public User update(Long userId, User user) {
        Optional<User> findUser = userRepository.findById(userId);

        if (findUser.isEmpty() || userId != user.getId()) {
            return null;
        }

        return userRepository.save(user);
    }

    public Boolean userDocumentNumberExist(Integer documentNumber) {
        Optional<User> user = userRepository.findByDocumentNumber(documentNumber);

        return !user.isEmpty();
    }
}
