package com.memelandia.memeservice.exceptions;

public class ServiceException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ServiceException(String message, String serviceName, Throwable cause) {
        super("\n\n#" + message + "\nFalha na comunicação com " + serviceName + ": " + cause.getMessage(), cause);
    }
}
