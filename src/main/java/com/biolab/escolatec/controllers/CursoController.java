package com.biolab.escolatec.controllers;

import com.biolab.escolatec.DTOs.Curso.CursoReq;
import com.biolab.escolatec.services.CursoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/curso")
public class CursoController {

    private final CursoService cursoService;

    public CursoController(CursoService cursoService) {
        this.cursoService = cursoService;
    }

    @PostMapping
    public ResponseEntity<?> create(
            @RequestBody @Valid CursoReq cursoReq) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(cursoService.create(cursoReq));
    }

    @GetMapping
    public ResponseEntity<?> read() {

        return ResponseEntity.ok(
                cursoService.getAll()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> readById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                cursoService.getById(id)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @RequestBody @Valid CursoReq cursoReq,
            @PathVariable Long id) {

        return ResponseEntity.ok(
                cursoService.update(id, cursoReq)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
            @PathVariable Long id) {

        cursoService.delete(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/alunos")
    public ResponseEntity<?> getAlunos(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                cursoService.getAlunos(id)
        );
    }
}