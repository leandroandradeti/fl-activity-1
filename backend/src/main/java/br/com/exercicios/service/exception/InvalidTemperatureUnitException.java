package br.com.exercicios.service.exception;

public class InvalidTemperatureUnitException extends RuntimeException {
    public InvalidTemperatureUnitException(String message) {
        super(message);
    }
}