package br.com.exercicios.dto;

import jakarta.validation.constraints.NotBlank;

public class PessoaPatchRequest {

    @NotBlank(message = "nome não pode ser vazio")
    private String nome;

    public PessoaPatchRequest() {
    }

    public PessoaPatchRequest(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}