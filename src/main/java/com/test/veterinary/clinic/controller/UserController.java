package com.test.veterinary.clinic.controller;

import com.test.veterinary.clinic.helper.Messages;
import com.test.veterinary.clinic.helper.RequestResponse;
import com.test.veterinary.clinic.model.Response;
import com.test.veterinary.clinic.model.User;
import com.test.veterinary.clinic.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/user")
@Api("User")
public class UserController {
    private UserService userService;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "/")
    @ApiOperation(value = "Users list", response = User.class)
    public ResponseEntity findAll() {
        return RequestResponse.executeRequestAction((response) -> {
            List<User> users = userService.findAll();

            Map body = new HashMap();

            body.put("users", users);

            return new Response(true, null, body);
        }, getMessage("message.user.findAll.error"));
    }

    @GetMapping(path = "/{id}")
    @ApiOperation(value = "User by id", response = User.class)
    public ResponseEntity findById(@PathVariable(value = "id") Long userId) {
        return RequestResponse.executeRequestAction((response) -> {
            Optional<User> user = userService.findById(userId);

            if (user.isEmpty()) {
                return getValidationUserNoExists();
            }

            return getRequestResponse(true, null, user);
        }, getMessage("message.user.findById.error"));
    }

    @PostMapping(path = "/")
    @ApiOperation(value = "User create", response = User.class)
    public ResponseEntity save(@Valid @RequestBody User user, BindingResult bindingResult) {
        return RequestResponse.executeRequestFormAction(bindingResult, (response) -> {
            if (isDocumentNumberExists(user)) {
                return getValidationDocumentNumberUnique();
            }

            Optional<User> newUser = Optional.ofNullable(userService.save(user));

            return getRequestResponse(true, "message.user.save.success", newUser);
        }, getMessage("message.user.save.error"));
    }

    @PutMapping(path = "/{id}")
    @ApiOperation(value = "User update", response = User.class)
    public ResponseEntity update(@Valid @PathVariable(value = "id") Long userId, @RequestBody User user, BindingResult bindingResult) {
        return RequestResponse.executeRequestFormAction(bindingResult, (response) -> {
            if (isDocumentNumberExists(user)) {
                return getValidationDocumentNumberUnique();
            }

            Optional<User> updatedUser = Optional.ofNullable(userService.update(userId, user));

            if (updatedUser.isEmpty()) {
                return getValidationUserNoExists();
            }

            return getRequestResponse(true, "message.user.update.success", updatedUser);
        }, getMessage("message.user.update.error"));
    }


    @DeleteMapping(path = "/{id}")
    @ApiOperation(value = "User delete", response = User.class)
    public ResponseEntity delete(@PathVariable(value = "id") Long userId) {
        return RequestResponse.executeRequestAction((response) -> {
            Optional<User> user = userService.findById(userId);

            if (user.isEmpty()) {
                return getValidationUserNoExists();
            }

            if (user.get().getPets().size() > 0) {
                return getRequestResponse(false, "message.user.delete.foreignPets", null);
            }

            Boolean deleted = userService.delete(userId);

            if (deleted) {
                return getRequestResponse(true, "message.user.delete.success", null);
            }

            return getRequestResponse(false, "message.user.delete.error", null);
        }, getMessage("message.user.delete.error"));
    }

    private Response getValidationDocumentNumberUnique() {
        return getRequestResponse(false, "message.user.documentNumber.unique", null);
    }

    private Response getValidationUserNoExists() {
        return getRequestResponse(false, "message.user.findById.noExists", null);
    }

    private Response getRequestResponse(Boolean status, String message, Optional<User> user) {
        Map body = new HashMap();

        body.put("user", user);

        return new Response(status, getMessage(message), body);
    }

    private String getMessage(String message) {
        return message == null ? null : Messages.getMessage(messageSource, message);
    }

    private Boolean isDocumentNumberExists(User user) {
        return userService.userDocumentNumberExist(user.getDocumentNumber());
    }
}
