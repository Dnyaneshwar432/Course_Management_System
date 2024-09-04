package com.newspringboot.newcms.DTO;

public class ErrorResponseDTO {
    private String message;

    // Constructors
    public ErrorResponseDTO(String message) {
        this.message = message;
    }

    // Getter and Setter
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

