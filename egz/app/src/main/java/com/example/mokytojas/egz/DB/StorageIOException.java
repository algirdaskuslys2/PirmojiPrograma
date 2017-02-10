package com.example.mokytojas.egz.DB;


@SuppressWarnings("serial")
public class StorageIOException extends StorageException {

    public StorageIOException(String message) {
        super(message);
    }

    public StorageIOException(String message, Throwable cause) {
        super(message, cause);
    }

}
