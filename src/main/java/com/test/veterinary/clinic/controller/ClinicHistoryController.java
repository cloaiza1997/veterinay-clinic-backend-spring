package com.test.veterinary.clinic.controller;

import com.test.veterinary.clinic.helper.Messages;
import com.test.veterinary.clinic.helper.RequestResponse;
import com.test.veterinary.clinic.model.ClinicHistory;
import com.test.veterinary.clinic.model.Response;
import com.test.veterinary.clinic.model.Pet;
import com.test.veterinary.clinic.service.ClinicHistoryService;
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

@RestController
@RequestMapping("/clinicHistory")
@Api("ClinicHistory")
public class ClinicHistoryController {
    public ClinicHistoryService clinicHistoryService;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private PetController petController;

    @Autowired
    public ClinicHistoryController(ClinicHistoryService clinicHistoryService) {
        this.clinicHistoryService = clinicHistoryService;
    }

    @GetMapping(path = "/")
    @ApiOperation(value = "ClinicHistories list", response = ClinicHistory.class)
    public ResponseEntity findAll() {
        return RequestResponse.executeRequestAction((response) -> {
            List<ClinicHistory> clinicHistorys = clinicHistoryService.findAll();

            Map body = new HashMap();

            body.put("clinicHistorys", clinicHistorys);

            return new Response(true, null, body);
        }, getMessage("message.clinicHistory.findAll.error"));
    }

    @GetMapping(path = "/{id}")
    @ApiOperation(value = "ClinicHistory by id", response = ClinicHistory.class)
    public ResponseEntity findById(@PathVariable(value = "id") Long clinicHistoryId) {
        return RequestResponse.executeRequestAction((response) -> {
            Optional<ClinicHistory> clinicHistory = clinicHistoryService.findById(clinicHistoryId);

            if (clinicHistory.isEmpty()) {
                return getValidationClinicHistoryNoExists();
            }

            return getRequestResponse(true, null, clinicHistory);
        }, getMessage("message.clinicHistory.findById.error"));
    }

    @PostMapping(path = "/")
    @ApiOperation(value = "ClinicHistory create", response = ClinicHistory.class)
    public ResponseEntity save(@Valid @RequestBody ClinicHistory clinicHistory, BindingResult bindingResult) {
        return RequestResponse.executeRequestFormAction(bindingResult, (response) -> {
            Optional<Pet> pet = petController.petService.findById(clinicHistory.getPetId());

            if (pet.isEmpty()) {
                return petController.getValidationPetNoExists();
            }

            if (isPetIdExists(clinicHistory)) {
                return getValidationClinicHistoryPetExists();
            }

            Optional<ClinicHistory> newClinicHistory = Optional.ofNullable(clinicHistoryService.save(clinicHistory));

            return getRequestResponse(true, "message.clinicHistory.save.success", newClinicHistory);
        }, getMessage("message.clinicHistory.save.error"));
    }

    @PutMapping(path = "/{id}")
    @ApiOperation(value = "ClinicHistory update", response = ClinicHistory.class)
    public ResponseEntity update(@Valid @PathVariable(value = "id") Long clinicHistoryId, @RequestBody ClinicHistory clinicHistory, BindingResult bindingResult) {
        return RequestResponse.executeRequestFormAction(bindingResult, (response) -> {
            Optional<Pet> pet = petController.petService.findById(clinicHistory.getPetId());

            if (pet.isEmpty()) {
                return petController.getValidationPetNoExists();
            }

            if (isPetIdExists(clinicHistory)) {
                return getValidationClinicHistoryPetExists();
            }

            Optional<ClinicHistory> updatedClinicHistory = Optional.ofNullable(clinicHistoryService.update(clinicHistoryId, clinicHistory));

            if (updatedClinicHistory.isEmpty()) {
                return getValidationClinicHistoryNoExists();
            }

            return getRequestResponse(true, "message.clinicHistory.update.success", updatedClinicHistory);
        }, getMessage("message.clinicHistory.update.error"));
    }

    @DeleteMapping(path = "/{id}")
    @ApiOperation(value = "ClinicHistory delete", response = ClinicHistory.class)
    public ResponseEntity delete(@PathVariable(value = "id") Long clinicHistoryId) {
        return RequestResponse.executeRequestAction((response) -> {
            Optional<ClinicHistory> clinicHistory = clinicHistoryService.findById(clinicHistoryId);

            if (clinicHistory.isEmpty()) {
                return getValidationClinicHistoryNoExists();
            }

            if (clinicHistory.get().getClinicHistoryDetails().size() > 0) {
                return getRequestResponse(false, "message.clinicHistory.delete.foreignClinicDetails", null);
            }

            Boolean deleted = clinicHistoryService.delete(clinicHistoryId);

            if (deleted) {
                return getRequestResponse(true, "message.clinicHistory.delete.success", null);
            }

            return getRequestResponse(false, "message.clinicHistory.delete.error", null);
        }, getMessage("message.clinicHistory.delete.error"));
    }

    public Response getValidationClinicHistoryNoExists() {
        return getRequestResponse(false, "message.clinicHistory.findById.noExists", null);
    }

    private Response getValidationClinicHistoryPetExists() {
        return getRequestResponse(false, "message.clinicHistory.petId.unique", null);
    }

    private Response getRequestResponse(Boolean status, String message, Optional<ClinicHistory> clinicHistory) {
        Map body = new HashMap();

        body.put("clinicHistory", clinicHistory);

        return new Response(status, getMessage(message), body);
    }

    private String getMessage(String message) {
        return message == null ? null : Messages.getMessage(messageSource, message);
    }

    private Boolean isPetIdExists(ClinicHistory clinicHistory) {
        return clinicHistoryService.clinicHistoryPetExists(clinicHistory.getId(), clinicHistory.getPetId());
    }
}
