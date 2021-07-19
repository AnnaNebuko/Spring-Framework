package ru.geekbrains.rest;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String product_id_should_be_null) {
        super(product_id_should_be_null);
    }
}
