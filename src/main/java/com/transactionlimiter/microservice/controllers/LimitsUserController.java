package com.transactionlimiter.microservice.controllers;

import com.transactionlimiter.microservice.dto.LimitRequest;
import com.transactionlimiter.microservice.dto.LimitResponse;
import com.transactionlimiter.microservice.services.LimitsService;
import com.transactionlimiter.microservice.util.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user/v1/limits")
public class LimitsUserController {
    private final LimitsService limitsService;
    @Autowired
    public LimitsUserController(LimitsService limitsService) {
        this.limitsService = limitsService;
    }
    @PostMapping
    public void setLimit(@RequestBody @Valid LimitRequest limitRequest, BindingResult bindingResult) {    // Setting a new limit by user
        if(bindingResult.hasErrors())
            throw new ArgumentNotValidException(bindingResult);
        limitsService.setLimit(limitRequest);
    }
    @GetMapping("/{account}")
    public List<LimitResponse> getAllLimits(@PathVariable("account") String account) {   // Request for all set limits for all time
        return limitsService.toDTO(limitsService.findAllByAccount(account));
    }
    @GetMapping("/{account}/actual")
    public List<LimitResponse> findActualLimits(@PathVariable("account") String account,  // Request for actual limits by category (service, product or all)
                                               @RequestParam(value = "category", required = false) String category) {
        if(category == null)
            throw new LimitParameterNotFoundException();
        return limitsService.toDTO(limitsService.findActualLimits(account, category));
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> limitAlreadyExistsHandler(LimitAlreadyExistsException e) {
        ErrorResponse response = new ErrorResponse("This limit already exists", System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler
    private ResponseEntity<ErrorResponse> limitNotFoundHandler(LimitNotFoundException e) {
        ErrorResponse response = new ErrorResponse("Limit not found", System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler
    private ResponseEntity<ErrorResponse> limitParameterNotFoundHandler(LimitParameterNotFoundException e) {
        ErrorResponse response = new ErrorResponse("Add a parameter to the request: category; values: product/service/all", System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


}
