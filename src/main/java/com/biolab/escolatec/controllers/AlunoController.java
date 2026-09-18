package com.biolab.escolatec.controllers;

import com.biolab.escolatec.DTOs.Aluno.AlunoReq;
import com.biolab.escolatec.services.AlunoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//COntroller do ALuno
@RestController
@RequestMapping("aluno") //rota do endpoint
public class AlunoController {
    //Service
    private final AlunoService alunoService;

    //inversão de dependencias
    public AlunoController(AlunoService alunoService) {
        this.alunoService = alunoService;
    }

    //Post para criar
    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid AlunoReq alunoReq) {
        if (alunoService.create(alunoReq) == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Curso não encontrado");
        }
        else {
            return ResponseEntity.status(HttpStatus.CREATED).body(alunoService.create(alunoReq));
        }

    }

    //get pra ler
    @GetMapping
    public ResponseEntity<?> read() {
        return ResponseEntity.status(HttpStatus.OK).body(alunoService.findAll());
    }

    //get por id
    @GetMapping("/{id}")
    public ResponseEntity<?> readById(@PathVariable Long id) {
        if (alunoService.getById(id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aluno não encontrado");
        }else {
            return ResponseEntity.status(HttpStatus.OK).body(alunoService.getById(id));
        }

    }

    //put pra atualizar
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody @Valid AlunoReq alunoReq) {
        if (alunoService.update(id, alunoReq) == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Curso ou aluno não encontrado");
        }else {
            return ResponseEntity.status(HttpStatus.OK).body(alunoService.update(id, alunoReq));
        }

    }

    //delte pra deletar
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {

        if (alunoService.delete(id) == null) {
            return ResponseEntity.badRequest().body("Aluno não encontrado");
        }else {
            alunoService.delete(id);//faz a função
            return ResponseEntity.noContent().build();//http code
        }

    }

    //fazer a matricula, é em aluno pois ele é o dono da relação
    @PostMapping("/{alunoId}/cursos/{cursoId}")
    public ResponseEntity<?> matricular(
            @PathVariable Long alunoId,
            @PathVariable Long cursoId) {

        //tenta rodar
        try {

            //faz matricula
            alunoService.matricular(alunoId, cursoId);

            //retorna 201 mas texto
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body("Aluno matriculado com sucesso");

            //se der erro
        } catch (RuntimeException e) {

            //tratando tipo de erro pela mensangem, se mudar a msg explode
            if (e.getMessage().equals("Aluno não encontrado")) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(e.getMessage());
            }

            if (e.getMessage().equals("Curso não encontrado")) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(e.getMessage());
            }

            if (e.getMessage().equals("Aluno já está matriculado neste curso")) {
                return ResponseEntity
                        .status(HttpStatus.CONFLICT)
                        .body(e.getMessage());
            }

            //se não for nenhum deles
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro interno");
        }
    }

    //deletar matricula
    @DeleteMapping("/{alunoId}/cursos/{cursoId}")
    public ResponseEntity<?> desmatricular(
            @PathVariable Long alunoId,
            @PathVariable Long cursoId) {

        //mesma logica que o de cima
        try {
            alunoService.desmatricular(alunoId, cursoId);
            return ResponseEntity.noContent().build();
        }catch (RuntimeException e) {
            if (e.getMessage().startsWith("Aluno não encontrado")) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(e.getMessage());
            }

            if (e.getMessage().startsWith("Curso não encontrado")) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(e.getMessage());
            }

            if (e.getMessage().startsWith("Aluno não está matriculado")) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(e.getMessage());
            }

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro interno");
        }
    }
}
