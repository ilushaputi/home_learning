package ru.liga.exchangerateforecast.exeption;

public class BusinessException extends RuntimeException {
    private String message;

    public BusinessException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
