package com.test.veterinary.clinic.controller;

import com.test.veterinary.clinic.helper.Messages;
import com.test.veterinary.clinic.helper.RequestResponse;
import com.test.veterinary.clinic.model.ClinicHistory;
import com.test.veterinary.clinic.model.ClinicHistoryDetail;
import com.test.veterinary.clinic.model.Worker;
import com.test.veterinary.clinic.model.Response;
import com.test.veterinary.clinic.service.ClinicHistoryDetailService;
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
@RequestMapping("/clinicHistoryDetail")
@Api("ClinicHistoryDetail")
public class ClinicHistoryDetailController {
    private ClinicHistoryDetailService clinicHistoryDetailService;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private WorkerController workerController;
    @Autowired
    private ClinicHistoryController clinicHistoryController;

    @Autowired
    public ClinicHistoryDetailController(ClinicHistoryDetailService clinicHistoryDetailService) {
        this.clinicHistoryDetailService = clinicHistoryDetailService;
    }

    @GetMapping(path = "/")
    @ApiOperation(value = "ClinicHistoryDetails list", response = ClinicHistoryDetail.class)
    public ResponseEntity findAll() {
        return RequestResponse.executeRequestAction((response) -> {
            List<ClinicHistoryDetail> clinicHistoryDetails = clinicHistoryDetailService.findAll();

            Map body = new HashMap();

            body.put("clinicHistoryDetails", clinicHistoryDetails);

            return new Response(true, null, body);
        }, getMessage("message.clinicHistoryDetail.findAll.error"));
    }

    @GetMapping(path = "/{id}")
    @ApiOperation(value = "ClinicHistoryDetail by id", response = ClinicHistoryDetail.class)
    public ResponseEntity findById(@PathVariable(value = "id") Long clinicHistoryDetailId) {
        return RequestResponse.executeRequestAction((response) -> {
            Optional<ClinicHistoryDetail> clinicHistoryDetail = clinicHistoryDetailService.findById(clinicHistoryDetailId);

            if (clinicHistoryDetail.isEmpty()) {
                return getValidationClinicHistoryDetailNoExists();
            }

            return getRequestResponse(true, null, clinicHistoryDetail);
        }, getMessage("message.clinicHistoryDetail.findById.error"));
    }

    @PostMapping(path = "/")
    @ApiOperation(value = "ClinicHistoryDetail create", response = ClinicHistoryDetail.class)
    public ResponseEntity save(@Valid @RequestBody ClinicHistoryDetail clinicHistoryDetail, BindingResult bindingResult) {
        return RequestResponse.executeRequestFormAction(bindingResult, (response) -> {
            Response validationResult = validateForeignData(clinicHistoryDetail);

            if (validationResult != null) {
                return validationResult;
            }

            Optional<ClinicHistoryDetail> newClinicHistoryDetail = Optional.ofNullable(clinicHistoryDetailService.save(clinicHistoryDetail));

            return getRequestResponse(true, "message.clinicHistoryDetail.save.success", newClinicHistoryDetail);
        }, getMessage("message.clinicHistoryDetail.save.error"));
    }

    @PutMapping(path = "/{id}")
    @ApiOperation(value = "ClinicHistoryDetail update", response = ClinicHistoryDetail.class)
    public ResponseEntity update(@Valid @PathVariable(value = "id") Long clinicHistoryDetailId, @RequestBody ClinicHistoryDetail clinicHistoryDetail, BindingResult bindingResult) {
        return RequestResponse.executeRequestFormAction(bindingResult, (response) -> {

            Response validationResult = validateForeignData(clinicHistoryDetail);
            System.out.println("***");
            System.out.println(validationResult);
            if (validationResult != null) {
                return validationResult;
            }

            Optional<ClinicHistoryDetail> updatedClinicHistoryDetail = Optional.ofNullable(clinicHistoryDetailService.update(clinicHistoryDetailId, clinicHistoryDetail));

            if (updatedClinicHistoryDetail.isEmpty()) {
                return getValidationClinicHistoryDetailNoExists();
            }

            return getRequestResponse(true, "message.clinicHistoryDetail.update.success", updatedClinicHistoryDetail);
        }, getMessage("message.clinicHistoryDetail.update.error"));
    }

    @DeleteMapping(path = "/{id}")
    @ApiOperation(value = "ClinicHistoryDetail delete", response = ClinicHistoryDetail.class)
    public ResponseEntity delete(@PathVariable(value = "id") Long clinicHistoryDetailId) {
        return RequestResponse.executeRequestAction((response) -> {
            Boolean deleted = clinicHistoryDetailService.delete(clinicHistoryDetailId);

            if (deleted) {
                return getRequestResponse(true, "message.clinicHistoryDetail.delete.success", null);
            }

            return getRequestResponse(false, "message.clinicHistoryDetail.delete.error", null);
        }, getMessage("message.clinicHistoryDetail.delete.error"));
    }

    private Response getValidationClinicHistoryDetailNoExists() {
        return getRequestResponse(false, "message.clinicHistoryDetail.findById.noExists", null);
    }

    private Response getRequestResponse(Boolean status, String message, Optional<ClinicHistoryDetail> clinicHistoryDetail) {
        Map body = new HashMap();

        body.put("clinicHistoryDetail", clinicHistoryDetail);

        return new Response(status, getMessage(message), body);
    }

    private String getMessage(String message) {
        return message == null ? null : Messages.getMessage(messageSource, message);
    }

    private Response validateForeignData(ClinicHistoryDetail clinicHistoryDetail) {
        Optional<Worker> worker = workerController.workerService.findById(clinicHistoryDetail.getWorkerId());

        if (worker.isEmpty()) {
            return workerController.getValidationWorkerNoExists();
        }

        Optional<ClinicHistory> clinicHistory = clinicHistoryController.clinicHistoryService.findById(clinicHistoryDetail.getClinicHistoryId());

        if (clinicHistory.isEmpty()) {
            return clinicHistoryController.getValidationClinicHistoryNoExists();
        }

        return null;
    }
}
