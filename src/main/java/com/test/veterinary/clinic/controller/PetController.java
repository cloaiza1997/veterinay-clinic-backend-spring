package com.test.veterinary.clinic.controller;

import com.test.veterinary.clinic.helper.Messages;
import com.test.veterinary.clinic.helper.RequestResponse;
import com.test.veterinary.clinic.interfaces.PetInterface;
import com.test.veterinary.clinic.model.Response;
import com.test.veterinary.clinic.model.Pet;
import com.test.veterinary.clinic.model.User;
import com.test.veterinary.clinic.service.PetService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Controlador para gestión de mascotas
 */
@RestController
@RequestMapping("/pet")
@Api("Pet")
public class PetController {
    public PetService petService;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private UserController userController;

    @Autowired
    public PetController(PetService petService) {
        this.petService = petService;
    }

    @GetMapping(path = "/")
    @ApiOperation(value = "Pets list", response = Pet.class)
    public ResponseEntity findAll() {
        return RequestResponse.executeRequestAction((response) -> {
            List<Pet> pets = petService.findAll();

            Map body = new HashMap();

            body.put("pets", pets);

            return new Response(true, null, body);
        }, getMessage("message.pet.findAll.error"));
    }

    @GetMapping(path = "/clinicHistory")
    @ApiOperation(value = "Pets  with clinic history", response = Pet.class)
    public ResponseEntity findAllWithClinicHistory() {
        return RequestResponse.executeRequestAction((response) -> {
            List<PetInterface> pets = petService.findAllWithClinicHistory();

            Map body = new HashMap();

            body.put("pets", pets);

            return new Response(true, null, body);
        }, getMessage("message.pet.findAll.error"));
    }

    @GetMapping(path = "/{id}")
    @ApiOperation(value = "Pet by id", response = Pet.class)
    public ResponseEntity findById(@PathVariable(value = "id") Long petId) {
        return RequestResponse.executeRequestAction((response) -> {
            Optional<Pet> pet = petService.findById(petId);

            if (pet.isEmpty()) {
                return getValidationPetNoExists();
            }

            return getRequestResponse(true, null, pet);
        }, getMessage("message.pet.findById.error"));
    }

    @PostMapping(path = "/")
    @ApiOperation(value = "Pet create", response = Pet.class)
    public ResponseEntity save(@Valid @RequestBody Pet pet, BindingResult bindingResult) {
        return RequestResponse.executeRequestFormAction(bindingResult, (response) -> {
            Optional<User> user = userController.userService.findById(pet.getUserId());

            if (user.isEmpty()) {
                return userController.getValidationUserNoExists();
            }

            Optional<Pet> newPet = Optional.ofNullable(petService.save(pet));

            return getRequestResponse(true, "message.pet.save.success", newPet);
        }, getMessage("message.pet.save.error"));
    }

    @PutMapping(path = "/{id}")
    @ApiOperation(value = "Pet update", response = Pet.class)
    public ResponseEntity update(@Valid @PathVariable(value = "id") Long petId, @RequestBody Pet pet, BindingResult bindingResult) {
        return RequestResponse.executeRequestFormAction(bindingResult, (response) -> {
            Optional<User> user = userController.userService.findById(pet.getUserId());

            if (user.isEmpty()) {
                return userController.getValidationUserNoExists();
            }

            Optional<Pet> updatedPet = Optional.ofNullable(petService.update(petId, pet));

            if (updatedPet.isEmpty()) {
                return getValidationPetNoExists();
            }

            return getRequestResponse(true, "message.pet.update.success", updatedPet);
        }, getMessage("message.pet.update.error"));
    }


    @DeleteMapping(path = "/{id}")
    @ApiOperation(value = "Pet delete", response = Pet.class)
    public ResponseEntity delete(@PathVariable(value = "id") Long petId) {
        return RequestResponse.executeRequestAction((response) -> {
            Optional<Pet> pet = petService.findById(petId);

            if (pet.isEmpty()) {
                return getValidationPetNoExists();
            }

            if (pet.get().getClinicHistory() != null) {
                return getRequestResponse(false, "message.pet.delete.foreignClinicHistory", null);
            }

            Boolean deleted = petService.delete(petId);

            if (deleted) {
                return getRequestResponse(true, "message.pet.delete.success", null);
            }

            return getRequestResponse(false, "message.pet.delete.error", null);
        }, getMessage("message.pet.delete.error"));
    }

    public Response getValidationPetNoExists() {
        return getRequestResponse(false, "message.pet.findById.noExists", null);
    }

    private Response getRequestResponse(Boolean status, String message, Optional<Pet> pet) {
        Map body = new HashMap();

        body.put("pet", pet);

        return new Response(status, getMessage(message), body);
    }

    private String getMessage(String message) {
        return message == null ? null : Messages.getMessage(messageSource, message);
    }
}
