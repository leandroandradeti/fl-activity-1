package br.com.exercicios.service;

import br.com.exercicios.service.exception.InvalidTemperatureUnitException;
import org.springframework.stereotype.Service;

@Service
public class TemperaturaService {

    public double converter(double valor, String de, String para) {
        String origem = normalizar(de);
        String destino = normalizar(para);

        validarUnidade(origem);
        validarUnidade(destino);

        if (origem.equals(destino)) {
            return valor;
        }

        // Converte origem -> Kelvin
        double kelvin;
        switch (origem) {
            case "C" -> kelvin = valor + 273.15;
            case "F" -> kelvin = (valor - 32.0) * 5.0 / 9.0 + 273.15;
            case "K" -> kelvin = valor;
            default -> throw new InvalidTemperatureUnitException("Unidade 'de' inválida: " + de);
        }

        // Converte Kelvin -> destino
        return switch (destino) {
            case "C" -> kelvin - 273.15;
            case "F" -> (kelvin - 273.15) * 9.0 / 5.0 + 32.0;
            case "K" -> kelvin;
            default -> throw new InvalidTemperatureUnitException("Unidade 'para' inválida: " + para);
        };
    }

    private String normalizar(String u) {
        if (u == null) return null;
        return u.trim().toUpperCase();
    }

    private void validarUnidade(String unidade) {
        if (unidade == null) {
            throw new InvalidTemperatureUnitException("Unidade inválida.");
        }
        if (!unidade.equals("C") && !unidade.equals("F") && !unidade.equals("K")) {
            throw new InvalidTemperatureUnitException("Unidade inválida: " + unidade + ". Use apenas C, F ou K.");
        }
    }
}