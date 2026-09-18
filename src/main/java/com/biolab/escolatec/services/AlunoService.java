package com.biolab.escolatec.services;

import com.biolab.escolatec.DTOs.Aluno.AlunoReq;
import com.biolab.escolatec.DTOs.Aluno.AlunoResp;
import com.biolab.escolatec.entities.Alunos;
import com.biolab.escolatec.entities.Cursos;
import com.biolab.escolatec.repositories.AlunoRepository;
import com.biolab.escolatec.repositories.CursoRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class AlunoService {

    private final AlunoRepository alunoRepository;
    private final CursoRepository cursoRepository;

    public AlunoService(
            AlunoRepository alunoRepository,
            CursoRepository cursoRepository) {

        this.alunoRepository = alunoRepository;
        this.cursoRepository = cursoRepository;
    }

    public AlunoResp create(AlunoReq req) {

        Alunos aluno = new Alunos();

        aluno.setNome(req.getNome());
        aluno.setEmail(req.getEmail());

        alunoRepository.save(aluno);

        return converterParaResponse(aluno);
    }

    public List<AlunoResp> findAll() {

        List<Alunos> alunos = alunoRepository.findAll();
        List<AlunoResp> resp = new ArrayList<>();

        for (Alunos aluno : alunos) {
            resp.add(converterParaResponse(aluno));
        }

        return resp;
    }

    public AlunoResp getById(Long id) {

        Alunos aluno = alunoRepository.findById(id)
                .orElseThrow();

        return converterParaResponse(aluno);
    }

    public AlunoResp update(Long id, AlunoReq req) {

        Alunos aluno = alunoRepository.findById(id)
                .orElseThrow();

        aluno.setNome(req.getNome());
        aluno.setEmail(req.getEmail());

        alunoRepository.save(aluno);

        return converterParaResponse(aluno);
    }

    public String delete(Long id) {

        Alunos aluno = alunoRepository.findById(id)
                .orElseThrow();

        alunoRepository.delete(aluno);

        return "Aluno deletado com sucesso";
    }

    public Set<Cursos> getCursos(Long id) {

        Alunos aluno = alunoRepository.findById(id)
                .orElseThrow();

        return aluno.getCursos();
    }

    private AlunoResp converterParaResponse(Alunos aluno) {

        AlunoResp resp = new AlunoResp();

        resp.setId(aluno.getId());
        resp.setNome(aluno.getNome());
        resp.setEmail(aluno.getEmail());
        resp.setCursos(aluno.getCursos());

        return resp;
    }
}