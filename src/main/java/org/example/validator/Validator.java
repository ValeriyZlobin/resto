package org.example.validator;

public interface Validator<E> {
    boolean validate(E value);
}
