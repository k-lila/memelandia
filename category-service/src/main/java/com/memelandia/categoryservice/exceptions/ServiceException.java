package com.memelandia.categoryservice.exceptions;

public class ServiceException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ServiceException(String serviceName, Throwable cause) {
        super("\n\nFalha na comunicação com " + serviceName + ": " + cause.getMessage(), cause);
    }
}
