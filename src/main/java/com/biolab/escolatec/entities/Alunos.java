package com.biolab.escolatec.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

//get e set pra não da hascode
@Getter
@Setter
//entiodade
@Entity
//construtores
@AllArgsConstructor
@NoArgsConstructor
public class Alunos {
    //id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    //nome
    @NotBlank
    private String nome;

    //email verifica se é email se tem @
    @Email
    @NotBlank
    private String email;

    //relaçãoi n:n com curso de qm foi a ideia de fazer só um lado se principal e de que pra persistir o dado tem que ser feito pelo principal
    @ManyToMany
    //relaçãO
    @JoinTable(
            name = "matricula",
            joinColumns = @JoinColumn(name = "aluno_id"), //PRINCIPAL
            inverseJoinColumns = @JoinColumn(name = "curso_id"), //O OUTRO
            //Unique para não ter matricula repetida
    uniqueConstraints = {
            @UniqueConstraint(
                    name = "uk_aluno_curso",
                    columnNames = {"aluno_id", "curso_id"})
    }
    )

    private Set<Cursos> cursos = new HashSet<>();//guarda cursos


}
