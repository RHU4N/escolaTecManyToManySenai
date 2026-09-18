package com.biolab.escolatec.controllers;

import com.biolab.escolatec.services.MatriculaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/matricula")
public class MatriculaController {

    private final MatriculaService matriculaService;

    public MatriculaController(MatriculaService matriculaService) {
        this.matriculaService = matriculaService;
    }

    @PostMapping("/aluno/{idAluno}/curso/{idCurso}")
    public ResponseEntity<?> adicionarCurso(
            @PathVariable Long idAluno,
            @PathVariable Long idCurso) {

        matriculaService.adicionarCurso(idAluno, idCurso);

        return ResponseEntity.ok(
                "Aluno associado ao curso com sucesso"
        );
    }

    @DeleteMapping("/aluno/{idAluno}/curso/{idCurso}")
    public ResponseEntity<?> removerCurso(
            @PathVariable Long idAluno,
            @PathVariable Long idCurso) {

        matriculaService.removerCurso(idAluno, idCurso);

        return ResponseEntity.noContent().build();
    }
}