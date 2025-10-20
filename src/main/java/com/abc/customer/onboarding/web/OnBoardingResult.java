package com.abc.customer.onboarding.web;

import org.springframework.http.HttpStatus;

public class OnBoardingResult {
    HttpStatus status;
    String message;

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
