package com.biolab.escolatec.DTOs.Curso;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CursoReq {
    @NotBlank
    private String nome;
    @NotBlank
    private String cargaHoraria;
    @NotNull
    private List<Long> idAlunos;
}
