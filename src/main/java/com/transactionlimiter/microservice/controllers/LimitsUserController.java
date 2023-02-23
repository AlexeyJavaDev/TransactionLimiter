package com.transactionlimiter.microservice.controllers;

import com.transactionlimiter.microservice.dto.LimitRequest;
import com.transactionlimiter.microservice.models.Limit;
import com.transactionlimiter.microservice.dto.LimitResponse;
import com.transactionlimiter.microservice.services.LimitsService;
import com.transactionlimiter.microservice.util.ErrorResponse;
import com.transactionlimiter.microservice.util.LimitAlreadyExistsException;
import com.transactionlimiter.microservice.util.LimitNotFoundException;
import com.transactionlimiter.microservice.util.LimitParameterNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/user/v1/limits")
public class LimitsUserController {
    private final LimitsService limitsService;
    @Autowired
    public LimitsUserController(LimitsService limitsService) {
        this.limitsService = limitsService;
    }
    @PostMapping
    public void setLimit(@RequestBody LimitRequest limitRequest) {    // Клиент устанавливает новый лимит
        limitsService.set(limitRequest);
    }
    @GetMapping("/{account}")
    public List<LimitResponse> getAllLimits(@PathVariable("account") String account) {   // Запрос всех выставленных когда-то лимитов по аккаунту
        List<Limit> resultList = limitsService.findAllByAccount(account);
        System.out.println(resultList);
        if(resultList.isEmpty())
            throw new LimitNotFoundException();
        else
            return limitsService.toDTO(resultList);
    }
    @GetMapping("/{account}/actual")
    public List<LimitResponse> getActualLimits(@PathVariable("account") String account,  // Запрос актуальных лимитов по категориям отдельно, либо по всем категориям
                                               @RequestParam(value = "category", required = false) String category) {
        if(category == null)
            throw new LimitParameterNotFoundException();
        List<Limit> resultList = new ArrayList<>();
        switch (category) {
            case ("service"), ("product") -> {
                limitsService.findOneActualByAccountAndCategory(account, category).ifPresent(resultList::add);
            }
            case ("all") -> {
                resultList.addAll(limitsService.findAllActualByAccountAndCategory(account));
            }
        }
        if(resultList.isEmpty())
            throw new LimitNotFoundException();
        return limitsService.toDTO(resultList);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> limitAlreadyExists(LimitAlreadyExistsException e) {
        ErrorResponse response = new ErrorResponse("This limit already exists", System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler
    private ResponseEntity<ErrorResponse> limitNotFound(LimitNotFoundException e) {
        ErrorResponse response = new ErrorResponse("Limit not found", System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler
    private ResponseEntity<ErrorResponse> limitParameterNotFound(LimitParameterNotFoundException e) {
        ErrorResponse response = new ErrorResponse("Add a parameter to the request: category; values: product/service/all", System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
