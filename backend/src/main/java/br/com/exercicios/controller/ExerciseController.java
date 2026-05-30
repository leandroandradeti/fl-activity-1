package br.com.exercicios.controller;

import br.com.exercicios.dto.CalculatorResponse;
import br.com.exercicios.service.TemperaturaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
public class ExerciseController {

    private final TemperaturaService temperaturaService;

    public ExerciseController(TemperaturaService temperaturaService) {
        this.temperaturaService = temperaturaService;
    }

    @GetMapping("/hello/{nome}")
    public ResponseEntity<String> hello(@PathVariable String nome) {
        return ResponseEntity.ok("Olá, " + nome + "!");
    }

    @GetMapping("/calc/soma")
    public ResponseEntity<CalculatorResponse> somaQuery(
            @RequestParam(required = false) BigDecimal a,
            @RequestParam(required = false) BigDecimal b
    ) {
        if (a == null) {
            throw new IllegalArgumentException("Parâmetro 'a' é obrigatório na query.");
        }
        if (b == null) {
            throw new IllegalArgumentException("Parâmetro 'b' é obrigatório na query.");
        }
        return ResponseEntity.ok(new CalculatorResponse(a.add(b)));
    }

    @GetMapping("/calc/soma/{a}/{b}")
    public ResponseEntity<CalculatorResponse> somaPath(
            @PathVariable BigDecimal a,
            @PathVariable BigDecimal b
    ) {
        return ResponseEntity.ok(new CalculatorResponse(a.add(b)));
    }

    @GetMapping("/temperatura/convert")
    public ResponseEntity<Double> convertTemperatura(
            @RequestParam BigDecimal valor,
            @RequestParam String de,
            @RequestParam String para
    ) {
        // TemperaturaService valida apenas C/F/K e lança InvalidTemperatureUnitException
        return ResponseEntity.ok(temperaturaService.converter(valor.doubleValue(), de, para));
    }
}