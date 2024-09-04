package com.newspringboot.newcms.Exception;

public class AdminDuplicateException extends RuntimeException {
    public AdminDuplicateException(String message) {
        super(message);
    }
}