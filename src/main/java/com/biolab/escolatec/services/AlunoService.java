package com.biolab.escolatec.services;

import com.biolab.escolatec.DTOs.Aluno.AlunoReq;
import com.biolab.escolatec.DTOs.Aluno.AlunoResp;
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
public class AlunoService {
    private final AlunoRepository alunoRepository;
    private final CursoRepository cursoRepository;

    public AlunoService(AlunoRepository alunoRepository, CursoRepository cursoRepository) {
        this.alunoRepository = alunoRepository;
        this.cursoRepository = cursoRepository;
    }

    public AlunoResp create(AlunoReq req){
        Alunos aluno = new Alunos();
        List<Cursos> cursos = new ArrayList<>();
        aluno.setNome(req.getNome());
        aluno.setEmail(req.getEmail());
        req.getIdCursos().forEach(id -> {
            Cursos curso = cursoRepository.findById(id).get();
            cursos.add(curso);
        });
        alunoRepository.save(aluno);

        AlunoResp resp = new AlunoResp();
        resp.setId(aluno.getId());
        resp.setNome(req.getNome());
        resp.setEmail(req.getEmail());
        resp.setCursos(cursos);
        return  resp;
    }

    public List<AlunoResp>  findAll(){
        List<Alunos> alunos = alunoRepository.findAll();
        List<AlunoResp> resp = new ArrayList<>();
        Set<Cursos> cursoResp = new HashSet<>();
        for (Alunos aluno : alunos) {
            AlunoResp resp1 = new AlunoResp();
            resp1.setId(aluno.getId());
            resp1.setNome(aluno.getNome());
            resp1.setEmail(aluno.getEmail());
            for (Cursos curso : aluno.getCursos()) {
               cursoResp.add(curso.getId());
            }
            resp1.setCursos(cursoResp);
            resp.add(resp1);
        }
        return resp;
    }

    public AlunoResp getById(Long id){
        Alunos aluno = alunoRepository.findById(id).orElseThrow();
        Set<Long> cursoResp = new HashSet<>();
        AlunoResp resp = new AlunoResp();
        resp.setId(aluno.getId());
        resp.setNome(aluno.getNome());
        resp.setEmail(aluno.getEmail());
        for (Cursos curso : aluno.getCursos()) {
            cursoResp.add(curso.getId());
        }
        resp.setCursos(cursoResp);
        return resp;
    }

    public AlunoResp update(Long id, AlunoReq req){
        Alunos aluno = alunoRepository.findById(id).orElseThrow();
        Set<Cursos> cursos = new HashSet<>();
        aluno.setNome(req.getNome());
        aluno.setEmail(req.getEmail());
        for (Cursos curso : aluno.getCursos()) {
            Cursos curso1 = cursoRepository.findById(curso.getId()).orElseThrow();
            cursos.add(curso1);
        }
        aluno.setCursos(cursos);
        alunoRepository.save(aluno);
        AlunoResp resp = new AlunoResp();
        resp.setId(aluno.getId());
        resp.setNome(req.getNome());
        resp.setEmail(req.getEmail());
        resp.setCursos(cursos);
        return  resp;
    }

    public String delete(Long id){
        Alunos aluno = alunoRepository.findById(id).orElseThrow();
        alunoRepository.delete(aluno);
        return "Aluno deletado com sucesso";
    }
}
