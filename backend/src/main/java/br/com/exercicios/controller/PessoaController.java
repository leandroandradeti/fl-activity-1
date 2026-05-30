package br.com.exercicios.controller;

import br.com.exercicios.dto.PessoaPatchRequest;
import br.com.exercicios.dto.PessoaPutRequest;
import br.com.exercicios.model.Pessoa;
import br.com.exercicios.repository.PessoaRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pessoa")
public class PessoaController {

    private final PessoaRepository pessoaRepository;

    public PessoaController(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    // Exercício 2 - Listagem
    @GetMapping
    public ResponseEntity<List<Pessoa>> listPessoas() {
        return ResponseEntity.ok(pessoaRepository.findAll());
    }

    // (opcional) GET por id
    @GetMapping("/{id}")
    public ResponseEntity<Pessoa> getPessoa(@PathVariable Long id) {
        return pessoaRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pessoa> putPessoa(
            @PathVariable Long id,
            @RequestBody @Valid PessoaPutRequest request
    ) {
        Pessoa pessoa = pessoaRepository.findById(id)
                .orElseGet(() -> new Pessoa(id, null));

        pessoa.setId(id);
        pessoa.setNome(request.getNome());

        Pessoa saved = pessoaRepository.save(pessoa);
        return ResponseEntity.ok(saved);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Pessoa> patchPessoa(
            @PathVariable Long id,
            @RequestBody @Valid PessoaPatchRequest request
    ) {
        Pessoa pessoa = pessoaRepository.findById(id)
                .orElseGet(() -> new Pessoa(id, null));

        pessoa.setNome(request.getNome());
        Pessoa saved = pessoaRepository.save(pessoa);

        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePessoa(@PathVariable Long id) {
        if (!pessoaRepository.existsById(id)) {
            return ResponseEntity.noContent().build(); // mantendo 204
        }

        pessoaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}