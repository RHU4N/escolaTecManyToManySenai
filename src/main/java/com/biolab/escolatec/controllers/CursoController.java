package com.biolab.escolatec.controllers;

import com.biolab.escolatec.DTOs.Curso.CursoReq;
import com.biolab.escolatec.services.CursoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//Controller
@RestController
@RequestMapping("curso")//endpoint controller
public class CursoController {
    //Service
    private final CursoService cursoService;

    //inversão de dependencia
    public CursoController(CursoService cursoService) {
        this.cursoService = cursoService;
    }

    //post pra criar
    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid CursoReq cursoReq) {
        if (cursoService.create(cursoReq) == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Aluno nao encontrado");
        }else {
            return ResponseEntity.status(HttpStatus.CREATED).body(cursoService.create(cursoReq));
        }
    }

    //get pra ler
    @GetMapping
    public ResponseEntity<?> read() {
        return ResponseEntity.ok(cursoService.getAll());
    }

    //get pra ler com id
    @GetMapping("/{id}")
    public ResponseEntity<?> readById(@PathVariable Long id) {
        if (cursoService.getById(id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Curso nao encontrado");
        }else {
            return ResponseEntity.ok(cursoService.getById(id));
        }
    }

    //put pra atualizar
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody @Valid CursoReq cursoReq, @PathVariable Long id) {
        if (cursoService.getById(id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Curso ou Aluno nao encontrado ");
        }else {
            return ResponseEntity.ok(cursoService.getById(id));
        }

    }

    //deleta
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        if (cursoService.getById(id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Curso nao encontrado ");
        }else {
            cursoService.delete(id);
            return ResponseEntity.noContent().build();
        }

    }


}
