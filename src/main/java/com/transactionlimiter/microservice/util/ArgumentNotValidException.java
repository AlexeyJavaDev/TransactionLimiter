package com.transactionlimiter.microservice.util;

import org.springframework.validation.BindingResult;

public class ArgumentNotValidException extends RuntimeException {
    private BindingResult bindingResult;
    public ArgumentNotValidException(BindingResult bindingResult) {
        this.bindingResult = bindingResult;
    }

    public BindingResult getBindingResult() {
        return bindingResult;
    }
}
