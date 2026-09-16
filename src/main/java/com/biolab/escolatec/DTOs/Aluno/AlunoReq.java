package com.biolab.escolatec.DTOs.Aluno;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlunoReq {
    @NotBlank
    private String nome;
    @Email
    @NotBlank
    private String email;
    @NotNull
    private List<Long> idCursos;
}
