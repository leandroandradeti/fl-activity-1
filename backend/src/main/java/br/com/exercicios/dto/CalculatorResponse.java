package br.com.exercicios.dto;

import java.math.BigDecimal;

public class CalculatorResponse {

    private BigDecimal resultado;

    public CalculatorResponse() {}

    public CalculatorResponse(BigDecimal resultado) {
        this.resultado = resultado;
    }

    public BigDecimal getResultado() {
        return resultado;
    }

    public void setResultado(BigDecimal resultado) {
        this.resultado = resultado;
    }
}