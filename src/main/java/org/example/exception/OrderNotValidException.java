package org.example.exception;

public class OrderNotValidException extends RuntimeException{
    public OrderNotValidException() {
        super("Заказ недействителен");
    }

    public OrderNotValidException(String message) {
        super(message);
    }
}
