package br.com.exercicios.dto;

import jakarta.validation.constraints.NotNull;

public class TemperaturaConvertRequest {

    @NotNull(message = "valor é obrigatório")
    private Double valor;

    @NotNull(message = "de é obrigatório")
    private String de;

    @NotNull(message = "para é obrigatório")
    private String para;

    public TemperaturaConvertRequest() {
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String getDe() {
        return de;
    }

    public void setDe(String de) {
        this.de = de;
    }

    public String getPara() {
        return para;
    }

    public void setPara(String para) {
        this.para = para;
    }
}