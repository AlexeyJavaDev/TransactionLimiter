package com.transactionlimiter.microservice.controllers;

import com.transactionlimiter.microservice.services.TransactionsService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user/v1/transactions")
public class TransactionsUserController {
    private final TransactionsService transactionsService;
    private final ModelMapper modelMapper;
    @Autowired
    public TransactionsUserController(TransactionsService transactionsService, ModelMapper modelMapper) {
        this.transactionsService = transactionsService;
        this.modelMapper = modelMapper;
    }
/*    @GetMapping("/{account}")
    public List<TransactionResponse> getTransactionsExceededLimit() {
        return transactionsService.getTransactionsExceededLimit();
    }*/
}
