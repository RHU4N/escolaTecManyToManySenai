package com.biolab.escolatec.DTOs.Aluno;

import com.biolab.escolatec.entities.Cursos;
import jakarta.validation.constraints.Email;
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
public class AlunoResp {
    @NotNull
    private long id;
    @NotBlank
    private String nome;
    @NotBlank
    @Email
    private String email;
    @NotNull
    private Set<Cursos> Cursos;
}
