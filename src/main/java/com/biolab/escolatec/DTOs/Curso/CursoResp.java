package com.biolab.escolatec.DTOs.Curso;

import com.biolab.escolatec.entities.Alunos;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CursoResp {
    @NotNull
    private long id;
    @NotBlank
    private String nome;
    @NotBlank
    private String cargaHoraria;
    @NotNull
    private Set<Alunos> Alunos;
}
