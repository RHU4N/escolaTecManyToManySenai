package com.biolab.escolatec.DTOs.Curso;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

//Dto pra mostrar como resposta
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CursoResp {

    @NotNull
    private Long id;

    @NotBlank
    private String nome;

    @NotBlank
    private String cargaHoraria;

    @NotNull
    private Set<CursoAlunoResp> alunos;
}