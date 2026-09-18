package com.biolab.escolatec.services;

import com.biolab.escolatec.DTOs.Curso.CursoReq;
import com.biolab.escolatec.DTOs.Curso.CursoResp;
import com.biolab.escolatec.entities.Alunos;
import com.biolab.escolatec.entities.Cursos;
import com.biolab.escolatec.repositories.AlunoRepository;
import com.biolab.escolatec.repositories.CursoRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class CursoService {

    private final CursoRepository cursoRepository;
    private final AlunoRepository alunoRepository;

    public CursoService(
            CursoRepository cursoRepository,
            AlunoRepository alunoRepository) {

        this.cursoRepository = cursoRepository;
        this.alunoRepository = alunoRepository;
    }

    public CursoResp create(CursoReq cursoReq) {

        Cursos curso = new Cursos();

        curso.setNome(cursoReq.getNome());
        curso.setCargaHoraria(cursoReq.getCargaHoraria());

        cursoRepository.save(curso);

        return converterParaResponse(curso);
    }

    public List<CursoResp> getAll() {

        List<Cursos> cursos = cursoRepository.findAll();
        List<CursoResp> resp = new ArrayList<>();

        for (Cursos curso : cursos) {
            resp.add(converterParaResponse(curso));
        }

        return resp;
    }

    public CursoResp getById(Long id) {

        Cursos curso = cursoRepository.findById(id)
                .orElseThrow();

        return converterParaResponse(curso);
    }

    public CursoResp update(Long id, CursoReq cursoReq) {

        Cursos curso = cursoRepository.findById(id)
                .orElseThrow();

        curso.setNome(cursoReq.getNome());
        curso.setCargaHoraria(cursoReq.getCargaHoraria());

        cursoRepository.save(curso);

        return converterParaResponse(curso);
    }

    public String delete(Long id) {

        cursoRepository.deleteById(id);

        return "Curso removido com sucesso";
    }

    public Set<Alunos> getAlunos(Long id) {

        Cursos curso = cursoRepository.findById(id)
                .orElseThrow();

        return curso.getAlunos();
    }

    private CursoResp converterParaResponse(Cursos curso) {

        CursoResp resp = new CursoResp();

        resp.setId(curso.getId());
        resp.setNome(curso.getNome());
        resp.setCargaHoraria(curso.getCargaHoraria());
        resp.setAlunos(curso.getAlunos());

        return resp;
    }
}