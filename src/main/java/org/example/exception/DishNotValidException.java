package org.example.exception;

public class DishNotValidException extends RuntimeException {
    public DishNotValidException(String message) {
        super(message);
    }

    public DishNotValidException() {
        super("Блюдо не прошло валидацию");
    }
}
