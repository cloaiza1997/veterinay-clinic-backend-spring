package com.test.veterinary.clinic.helper;

import com.test.veterinary.clinic.model.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Clase que define la estructura de las respuestas HTTP
 */
public abstract class RequestResponse {
    public static ResponseEntity generateResponse(Boolean status, String message, Object body, List<ObjectError> formError) {
        Map response = new HashMap<>();

        response.put("status", status);
        response.put("message", message);
        response.put("body", body);
        response.put("formError", formError);

        return new ResponseEntity<>(response, status ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    public static ResponseEntity executeRequestFormAction(BindingResult bindingResult, Function<Map, Response> function, String errorMessage) {
        if (bindingResult.hasErrors()) {
            return RequestResponse.generateResponse(false, errorMessage, null, bindingResult.getAllErrors());
        }

        return executeRequestAction(function, errorMessage);
    }

    public static ResponseEntity executeRequestAction(Function<Map, Response> function, String errorMessage) {
        Map response = new HashMap<>();

        try {
            Response functionResponse = function.apply(response);

            return RequestResponse.generateResponse(functionResponse.getStatus(), functionResponse.getMessage(), functionResponse.getBody(), null);
        } catch (Exception e) {
            return RequestResponse.generateResponse(false, errorMessage, null, null);
        }
    }
}

