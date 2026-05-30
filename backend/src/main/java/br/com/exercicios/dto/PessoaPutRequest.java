package br.com.exercicios.dto;

import jakarta.validation.constraints.NotBlank;

public class PessoaPutRequest {

    @NotBlank(message = "nome não pode ser vazio")
    private String nome;

    public PessoaPutRequest() {
    }

    public PessoaPutRequest(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}