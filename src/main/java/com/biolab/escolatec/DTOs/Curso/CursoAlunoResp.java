package com.biolab.escolatec.DTOs.Curso;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//DTO criado só pra não ter um loop no Json
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CursoAlunoResp {

    private Long id;

    private String nome;

    private String email;
}