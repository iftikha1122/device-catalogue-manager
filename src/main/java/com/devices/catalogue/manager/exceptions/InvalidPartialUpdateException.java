package com.devices.catalogue.manager.exceptions;
public class InvalidPartialUpdateException extends RuntimeException {
    public InvalidPartialUpdateException() {
        super("At least one field must be provided for partial update");
    }
}