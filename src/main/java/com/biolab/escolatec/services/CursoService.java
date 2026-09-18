package com.biolab.escolatec.services;

import com.biolab.escolatec.DTOs.Curso.CursoAlunoResp;
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

//Service
@Service
public class CursoService {

    //Repository de ambos pq precisa
    private final CursoRepository cursoRepository;
    private final AlunoRepository alunoRepository;

    public CursoService(CursoRepository cursoRepository, AlunoRepository alunoRepository) {
        this.cursoRepository = cursoRepository;
        this.alunoRepository = alunoRepository;
    }

    //criar coisa no banco
    public CursoResp create(CursoReq cursoReq) {

        Cursos curso = new Cursos();

        //Aqui começa o role pq o outro é o principal
        Set<Alunos> alunos = new HashSet<>();

        for (Long id : cursoReq.getIdAlunos()) {

            if (!alunoRepository.existsById(id)) {
                return null;
            }

            Alunos aluno = alunoRepository.findById(id).get();

            alunos.add(aluno);
        }
        //pega os dados
        curso.setNome(cursoReq.getNome());
        curso.setCargaHoraria(cursoReq.getCargaHoraria());

        // Salva primeiro para gerar o ID do curso
        cursoRepository.save(curso);

        //associação pois o outro é o principal
        for (Alunos aluno : alunos) {

            aluno.getCursos().add(curso);
            curso.getAlunos().add(aluno);

            alunoRepository.save(aluno);
        }

        //Transformando em Resp pra retorna
        CursoResp cursoResp = new CursoResp();

        cursoResp.setId(curso.getId());
        cursoResp.setNome(curso.getNome());
        cursoResp.setCargaHoraria(curso.getCargaHoraria());
        cursoResp.setAlunos(converterAlunos(curso.getAlunos())); //função pq existe um DTO pra não ter um loop inifinito no json

        return cursoResp;
    }

    //retorna geral
    public List<CursoResp> getAll() {

        //faz a lista com geral
        List<Cursos> cursos = cursoRepository.findAll();
        List<CursoResp> cursoResp = new ArrayList<>(); //lista para resposta

        //foreach para preencher a lista
        for (Cursos curso : cursos) {

            //preenchenco a lista
            CursoResp cursoResp1 = new CursoResp();

            cursoResp1.setId(curso.getId());
            cursoResp1.setNome(curso.getNome());
            cursoResp1.setCargaHoraria(curso.getCargaHoraria());
            cursoResp1.setAlunos(converterAlunos(curso.getAlunos())); //Conertendo pra não ter loop

            cursoResp.add(cursoResp1);
        }

        return cursoResp;
    }

//achar por id
    public CursoResp getById(Long id) {

        //ve se existe pra retornar httpcode certo
        if (!cursoRepository.existsById(id)) {
            return null;
        }

        //acha por id
        Cursos curso = cursoRepository.findById(id).get();

        CursoResp cursoResp = new CursoResp();

        cursoResp.setId(curso.getId());
        cursoResp.setNome(curso.getNome());
        cursoResp.setCargaHoraria(curso.getCargaHoraria());
        cursoResp.setAlunos(converterAlunos(curso.getAlunos())); //Sem loop no json

        return cursoResp;
    }

    //atualiza
    public CursoResp update(Long id, CursoReq cursoReq) {

        if (!cursoRepository.existsById(id)) {
            return null;
        }

        Cursos curso = cursoRepository.findById(id).get();

        curso.setNome(cursoReq.getNome());
        curso.setCargaHoraria(cursoReq.getCargaHoraria());

        // Alunos que devem ficar matriculados depois do update, tem que fazer todo um role por esse ser o principal e ter chance de altera um aluno aaaa
        Set<Alunos> novosAlunos = new HashSet<>();

        //for pra pegar os id
        for (Long idAluno : cursoReq.getIdAlunos()) {

            //ve se existe
            if (!alunoRepository.existsById(idAluno)) {
                return null;
            }
            //pega por id
            Alunos aluno = alunoRepository.findById(idAluno).get();

            //atualiza lista
            novosAlunos.add(aluno);
        }

        // Alunos que estavam matriculados anteriormente tem que deixar sincronizado essa #####
        Set<Alunos> alunosAntigos = new HashSet<>(curso.getAlunos());

        // Remove a matrícula dos alunos que não estão mais na requisição
        for (Alunos aluno : alunosAntigos) {

            //verifica a lista nova com a antiga pra remover do sync
            if (!novosAlunos.contains(aluno)) {

                aluno.getCursos().remove(curso);
                curso.getAlunos().remove(aluno);

                alunoRepository.save(aluno);//salva em aluno pois é o principal
            }
        }

        // Adiciona os novos alunos
        for (Alunos aluno : novosAlunos) {

            aluno.getCursos().add(curso);
            curso.getAlunos().add(aluno);

            alunoRepository.save(aluno); //salava pois é o principal e tem que deixar sincronizado
        }

        cursoRepository.save(curso);//salva no banco

        //transformar em Response
        CursoResp cursoResp = new CursoResp();

        cursoResp.setId(curso.getId());
        cursoResp.setNome(curso.getNome());
        cursoResp.setCargaHoraria(curso.getCargaHoraria());
        cursoResp.setAlunos(converterAlunos(curso.getAlunos()));

        return cursoResp;
    }

    //deleta
    public String delete(Long id) {

        if (!cursoRepository.existsById(id)) {
            return null;
        }

        Cursos curso = cursoRepository.findById(id).get();

        // Remove o curso das matrículas dos alunos
        Set<Alunos> alunos = new HashSet<>(curso.getAlunos());

        for (Alunos aluno : alunos) {
            aluno.getCursos().remove(curso);
            alunoRepository.save(aluno);
        }

        cursoRepository.delete(curso);

        return "Curso removido com sucesso";
    }

    //pra ser mais rapido de converter pra não ter loop será que eu deveria ter criado um pra resp noraml tbm seria tão mais facil
    private Set<CursoAlunoResp> converterAlunos(Set<Alunos> alunos) {

        //cria lista
        Set<CursoAlunoResp> resposta = new HashSet<>();

        //pega o valor do parametro e divide com o foreach
        for (Alunos aluno : alunos) {

            //pra salva na lista
            CursoAlunoResp dto = new CursoAlunoResp();

            //converteu pro DTO
            dto.setId(aluno.getId());
            dto.setNome(aluno.getNome());
            dto.setEmail(aluno.getEmail());

            resposta.add(dto);
        }

        return resposta; //retorna convertido sepá deveria ter feito um pra respose tbm
    }

}