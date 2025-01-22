package org.example.exception;

public class TableNotFoundException extends RuntimeException{
    public TableNotFoundException() {
        super("Стол не найден");
    }

    public TableNotFoundException(String message) {
        super(message);
    }
}
