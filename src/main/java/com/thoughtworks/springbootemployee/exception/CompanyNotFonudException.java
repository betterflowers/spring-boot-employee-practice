package com.thoughtworks.springbootemployee.exception;

public class CompanyNotFonudException extends RuntimeException {

    @Override
    public String getMessage() {
        return "Company Not Found.";
    }
}
