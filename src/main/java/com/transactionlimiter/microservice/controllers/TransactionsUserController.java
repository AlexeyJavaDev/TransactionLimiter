package com.transactionlimiter.microservice.controllers;

import com.transactionlimiter.microservice.dto.TransactionResponse;
import com.transactionlimiter.microservice.services.TransactionsService;
import com.transactionlimiter.microservice.util.ErrorResponse;
import com.transactionlimiter.microservice.util.TransactionNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/v1/transactions")
public class TransactionsUserController {
    private final TransactionsService transactionsService;

    @Autowired
    public TransactionsUserController(TransactionsService transactionsService) {
        this.transactionsService = transactionsService;
    }

    @GetMapping("/{account}")
    public List<TransactionResponse> getTransactionsExceededLimit(@PathVariable("account") String account) {    // Request for transactions that have exceeded the limit
        return transactionsService.getTransactionsExceededLimit(account);
    }
    @ExceptionHandler
    private ResponseEntity<ErrorResponse> transactionsNotFound(TransactionNotFoundException e) {
        ErrorResponse response = new ErrorResponse("There are no transactions that exceeded the limit", System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
