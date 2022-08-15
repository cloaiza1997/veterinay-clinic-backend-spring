package com.test.veterinary.clinic.controller;

import com.test.veterinary.clinic.helper.Messages;
import com.test.veterinary.clinic.helper.RequestResponse;
import com.test.veterinary.clinic.model.Response;
import com.test.veterinary.clinic.model.Worker;
import com.test.veterinary.clinic.service.WorkerService;
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
 * Controlador para gestión de colaboradores
 */
@RestController
@RequestMapping("/worker")
@Api("Worker")
public class WorkerController {
    public WorkerService workerService;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    public WorkerController(WorkerService workerService) {
        this.workerService = workerService;
    }

    @GetMapping(path = "/")
    @ApiOperation(value = "Workers list", response = Worker.class)
    public ResponseEntity findAll() {
        return RequestResponse.executeRequestAction((response) -> {
            List<Worker> workers = workerService.findAll();

            Map body = new HashMap();

            body.put("workers", workers);

            return new Response(true, null, body);
        }, getMessage("message.worker.findAll.error"));
    }

    @GetMapping(path = "/{id}")
    @ApiOperation(value = "Worker by id", response = Worker.class)
    public ResponseEntity findById(@PathVariable(value = "id") Long workerId) {
        return RequestResponse.executeRequestAction((response) -> {
            Optional<Worker> worker = workerService.findById(workerId);

            if (worker.isEmpty()) {
                return getValidationWorkerNoExists();
            }

            return getRequestResponse(true, null, worker);
        }, getMessage("message.worker.findById.error"));
    }

    @PostMapping(path = "/")
    @ApiOperation(value = "Worker create", response = Worker.class)
    public ResponseEntity save(@Valid @RequestBody Worker worker, BindingResult bindingResult) {
        return RequestResponse.executeRequestFormAction(bindingResult, (response) -> {
            if (isDocumentNumberExists(worker)) {
                return getValidationDocumentNumberUnique();
            }

            Optional<Worker> newWorker = Optional.ofNullable(workerService.save(worker));

            return getRequestResponse(true, "message.worker.save.success", newWorker);
        }, getMessage("message.worker.save.error"));
    }

    @PutMapping(path = "/{id}")
    @ApiOperation(value = "Worker update", response = Worker.class)
    public ResponseEntity update(@Valid @PathVariable(value = "id") Long workerId, @RequestBody Worker worker, BindingResult bindingResult) {
        return RequestResponse.executeRequestFormAction(bindingResult, (response) -> {
            if (isDocumentNumberExists(worker)) {
                return getValidationDocumentNumberUnique();
            }

            Optional<Worker> updatedWorker = Optional.ofNullable(workerService.update(workerId, worker));

            if (updatedWorker.isEmpty()) {
                return getValidationWorkerNoExists();
            }

            return getRequestResponse(true, "message.worker.update.success", updatedWorker);
        }, getMessage("message.worker.update.error"));
    }


    @DeleteMapping(path = "/{id}")
    @ApiOperation(value = "Worker delete", response = Worker.class)
    public ResponseEntity delete(@PathVariable(value = "id") Long workerId) {
        return RequestResponse.executeRequestAction((response) -> {
            Optional<Worker> worker = workerService.findById(workerId);

            if (worker.isEmpty()) {
                return getValidationWorkerNoExists();
            }

            if (worker.get().getClinicHistoryDetails().size() > 0) {
                return getRequestResponse(false, "message.worker.delete.foreignClinicDetails", null);
            }

            Boolean deleted = workerService.delete(workerId);

            if (deleted) {
                return getRequestResponse(true, "message.worker.delete.success", null);
            }

            return getRequestResponse(false, "message.worker.delete.error", null);
        }, getMessage("message.worker.delete.error"));
    }

    private Response getValidationDocumentNumberUnique() {
        return getRequestResponse(false, "message.worker.documentNumber.unique", null);
    }

    public Response getValidationWorkerNoExists() {
        return getRequestResponse(false, "message.worker.findById.noExists", null);
    }

    private Response getRequestResponse(Boolean status, String message, Optional<Worker> worker) {
        Map body = new HashMap();

        body.put("worker", worker);

        return new Response(status, getMessage(message), body);
    }

    private String getMessage(String message) {
        return message == null ? null : Messages.getMessage(messageSource, message);
    }

    private Boolean isDocumentNumberExists(Worker worker) {
        return workerService.workerDocumentNumberExist(worker);
    }
}
