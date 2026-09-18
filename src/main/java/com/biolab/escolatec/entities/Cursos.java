package com.biolab.escolatec.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

//é uma entidade
@Entity
//usado pra não dar erro HashCode e gerar get e set
@Getter
@Setter
//construtores
@AllArgsConstructor
@NoArgsConstructor
public class Cursos {
    //id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    //nome
    @NotBlank
    private String nome;

    //carga horaria
    @NotBlank
    private String cargaHoraria;

    //relação n:n com aluno só que não é a principal pois a principal ta em aluno
    @ManyToMany(mappedBy = "cursos")
//    @JsonIgnore annotation pra não ter loop infinito no json porem decidir ir para DTO
    //venho do futuro falar q me arrepedi do DTO pois tive que converter um valor pro DTO pelo menos virou uma função isso mas tive que mexer em tudo
    private Set<Alunos> alunos = new HashSet<>(); //onde guarda os alunos
}
