package com.test.veterinary.clinic.service;

import com.test.veterinary.clinic.interfaces.PetInterface;
import com.test.veterinary.clinic.model.Pet;
import com.test.veterinary.clinic.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PetService {
    private final PetRepository petRepository;

    @Autowired
    public PetService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    public Boolean delete(Long petId) {
        petRepository.deleteById(petId);

        return true;
    }

    public List<Pet> findAll() {
        return petRepository.findAll();
    }

    public List<PetInterface> findAllWithClinicHistory() {
        return petRepository.findAllWithClinicHistory();
    }

    public Optional<Pet> findById(Long petId) {
        return petRepository.findById(petId);
    }

    public Pet save(Pet pet) {
        return petRepository.save(pet);
    }

    public Pet update(Long petId, Pet pet) {
        Optional<Pet> findPet = petRepository.findById(petId);

        if (findPet.isEmpty() || petId != pet.getId()) {
            return null;
        }

        return petRepository.save(pet);
    }
}
