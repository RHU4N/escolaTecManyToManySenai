package com.biolab.escolatec.services;

import com.biolab.escolatec.DTOs.Aluno.AlunoResp;
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

    public List<CursoResp> getAll() {
        List<Cursos> cursos = cursoRepository.findAll();
        List<CursoResp> cursoResp = new ArrayList<>();
        Set<Alunos> alunos = new HashSet<>();
        for (Cursos curso : cursos) {
            CursoResp cursoResp1 = new CursoResp();
            cursoResp1.setId(curso.getId());
            cursoResp1.setNome(curso.getNome());
            cursoResp1.setCargaHoraria(curso.getCargaHoraria());
            alunos.addAll(curso.getAlunos());
            cursoResp1.setAlunos(alunos);
            cursoResp.add(cursoResp1);
        }
        return cursoResp;
    }

    public CursoResp getById(Long id) {
        Cursos curso = cursoRepository.findById(id).orElseThrow();
        Set<Alunos> alunos = curso.getAlunos();
        CursoResp cursoResp = new CursoResp();
        cursoResp.setId(curso.getId());
        cursoResp.setNome(curso.getNome());
        cursoResp.setCargaHoraria(curso.getCargaHoraria());
        cursoResp.setAlunos(alunos);
        return cursoResp;
    }

    public CursoResp update(Long id, CursoReq cursoReq) {
        Cursos curso = cursoRepository.findById(id).orElseThrow();
        Set<Alunos> alunos = new HashSet<>();
        CursoResp cursoResp = new CursoResp();
        curso.setNome(cursoReq.getNome());
        curso.setCargaHoraria(curso.getCargaHoraria());
        alunos.addAll(curso.getAlunos());
        curso.setAlunos(alunos);
        cursoRepository.save(curso);

        cursoResp.setId(curso.getId());
        cursoResp.setNome(curso.getNome());
        cursoResp.setCargaHoraria(curso.getCargaHoraria());
        cursoResp.setAlunos(alunos);
        return cursoResp;
    }

    public String delete(Long id) {
        cursoRepository.deleteById(id);
        return "Curso removido com sucesso";
    }
}

