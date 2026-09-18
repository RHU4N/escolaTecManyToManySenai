package com.biolab.escolatec.DTOs.Aluno;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlunoReq {

    @NotBlank
    private String nome;

    @Email
    @NotBlank
    private String email;
}