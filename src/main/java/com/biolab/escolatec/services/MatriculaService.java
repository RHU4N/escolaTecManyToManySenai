package com.biolab.escolatec.services;

import com.biolab.escolatec.entities.Alunos;
import com.biolab.escolatec.entities.Cursos;
import com.biolab.escolatec.repositories.AlunoRepository;
import com.biolab.escolatec.repositories.CursoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class MatriculaService {

    private final AlunoRepository alunoRepository;
    private final CursoRepository cursoRepository;

    public MatriculaService(
            AlunoRepository alunoRepository,
            CursoRepository cursoRepository) {

        this.alunoRepository = alunoRepository;
        this.cursoRepository = cursoRepository;
    }

    @Transactional
    public void adicionarCurso(Long idAluno, Long idCurso) {

        Alunos aluno = alunoRepository.findById(idAluno)
                .orElseThrow();

        Cursos curso = cursoRepository.findById(idCurso)
                .orElseThrow();

        aluno.getCursos().add(curso);
    }

    @Transactional
    public void removerCurso(Long idAluno, Long idCurso) {

        Alunos aluno = alunoRepository.findById(idAluno)
                .orElseThrow();

        Cursos curso = cursoRepository.findById(idCurso)
                .orElseThrow();

        aluno.getCursos().remove(curso);
    }
}