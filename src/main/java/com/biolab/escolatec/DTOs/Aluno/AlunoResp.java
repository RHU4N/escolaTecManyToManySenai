package com.biolab.escolatec.DTOs.Aluno;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

//DTO pra mostrar como resposta
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlunoResp {

    @NotNull
    private Long id;

    @NotBlank
    private String nome;

    @NotBlank
    @Email
    private String email;

    @NotNull
    private Set<AlunoCursoResp> cursos;
}