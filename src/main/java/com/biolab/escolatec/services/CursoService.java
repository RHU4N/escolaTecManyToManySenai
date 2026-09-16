package com.biolab.escolatec.services;

import com.biolab.escolatec.DTOs.Curso.CursoReq;
import com.biolab.escolatec.DTOs.Curso.CursoResp;
import com.biolab.escolatec.entities.Alunos;
import com.biolab.escolatec.entities.Cursos;
import com.biolab.escolatec.repositories.AlunoRepository;
import com.biolab.escolatec.repositories.CursoRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CursoService {
    private final CursoRepository cursoRepository;
    private final AlunoRepository alunoRepository;

    public CursoService(CursoRepository cursoRepository, AlunoRepository alunoRepository) {
        this.cursoRepository = cursoRepository;
        this.alunoRepository = alunoRepository;
    }

    public CursoResp create(CursoReq cursoReq) {
        Cursos cursos = new Cursos();
        Set<Alunos> alunos = new HashSet<>();
        cursos.setNome(cursoReq.getNome());
        cursos.setCargaHoraria(cursoReq.getCargaHoraria());
        for (Alunos aluno : cursos.getAlunos()) {
            Alunos aluno1 = alunoRepository.findById(aluno.getId()).orElseThrow();
            alunos.add(aluno1);
        }
        cursos.setAlunos(alunos);
        cursoRepository.save(cursos);
        CursoResp cursoResp = new CursoResp();
        cursoResp.setId(cursos.getId());
        cursoReq.setNome(cursoReq.getNome());
        cursoReq.setCargaHoraria(cursoReq.getCargaHoraria());
        cursoResp.setAlunos(alunos);

        return cursoResp;
    }

    public CursoResp getAll(){
        Cursos cursos = new Cursos();
        List<Long> alunos = new ArrayList<>();
        CursoResp cursoResp = new CursoResp();
        cursoResp.setId(cursos.getId());
        cursoResp.setNome(cursos.getNome());
        cursoResp.setCargaHoraria(cursos.getCargaHoraria());
        for (Alunos aluno : cursos.getAlunos()) {
            Alunos aluno1 = alunoRepository.findById(aluno.getId()).orElseThrow();

        }

    }
}
