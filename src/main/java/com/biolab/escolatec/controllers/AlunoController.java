package com.biolab.escolatec.controllers;

import com.biolab.escolatec.DTOs.Aluno.AlunoReq;
import com.biolab.escolatec.services.AlunoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("aluno")
public class AlunoController {
    private final AlunoService alunoService;

    public AlunoController(AlunoService alunoService) {
        this.alunoService = alunoService;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid AlunoReq alunoReq) {
        return ResponseEntity.status(HttpStatus.CREATED).body(alunoService.create(alunoReq));
    }

    @GetMapping
    public ResponseEntity<?> read() {
        return ResponseEntity.status(HttpStatus.OK).body(alunoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> readById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(alunoService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody @Valid AlunoReq alunoReq) {
        return ResponseEntity.status(HttpStatus.OK).body(alunoService.update(id, alunoReq));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(alunoService.delete(id));
    }
}
