package com.biolab.escolatec.services;

import com.biolab.escolatec.DTOs.Aluno.AlunoCursoResp;
import com.biolab.escolatec.DTOs.Aluno.AlunoReq;
import com.biolab.escolatec.DTOs.Aluno.AlunoResp;
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

//service
@Service
public class AlunoService {
    //precisa dos dois repo pra puxar certo no json
    private final AlunoRepository alunoRepository;
    private final CursoRepository cursoRepository;

    //inverção
    public AlunoService(AlunoRepository alunoRepository, CursoRepository cursoRepository) {
        this.alunoRepository = alunoRepository;
        this.cursoRepository = cursoRepository;
    }

//cria
    public AlunoResp create(AlunoReq req){
        Alunos aluno = new Alunos(); //cria aluno
        Set<Cursos> cursos = new HashSet<>(); // lista de curso pra enfiar em aluno
        aluno.setNome(req.getNome());
        aluno.setEmail(req.getEmail());
        for (Long id : req.getIdCursos()) {

            // Verifica se o curso existe
            if (!cursoRepository.existsById(id)) {
                return null;
            }

            // Pega o curso
            Cursos curso = cursoRepository.findById(id).get();

            // Adiciona o curso ao aluno
            cursos.add(curso);

            // Mantém os dois lados sincronizados pq não fizeram isso automatico será que era muito trapo ou eu só não soube fazer
            curso.getAlunos().add(aluno);
        }
        //coloca cursos lá dentro e salva no bd
        aluno.setCursos(cursos);
        alunoRepository.save(aluno);

        //converte pra resposta
        AlunoResp resp = new AlunoResp();
        resp.setId(aluno.getId());
        resp.setNome(req.getNome());
        resp.setEmail(req.getEmail());
        resp.setCursos(converterCursos(aluno.getCursos())); //tem que converter pra não ter loop
        return  resp;
    }

    //Pega tudo
    public List<AlunoResp> findAll() {

        //Pega tudo e guard
        List<Alunos> alunos = alunoRepository.findAll();

        //tem que devolver algo né
        List<AlunoResp> resp = new ArrayList<>();

        //foreach pra encher o resp
        for (Alunos aluno : alunos) {

            AlunoResp resp1 = new AlunoResp();

            resp1.setId(aluno.getId());
            resp1.setNome(aluno.getNome());
            resp1.setEmail(aluno.getEmail());
            resp1.setCursos(converterCursos(aluno.getCursos()));//converte sem loop deveria ser o nome

            resp.add(resp1);
        }

        return resp;
    }

    //pega por id
    public AlunoResp getById(Long id) {

        if (!alunoRepository.existsById(id)) {
            return null;
        }

        Alunos aluno = alunoRepository.findById(id).get();

        AlunoResp resp = new AlunoResp();

        resp.setId(aluno.getId());
        resp.setNome(aluno.getNome());
        resp.setEmail(aluno.getEmail());
        resp.setCursos(converterCursos(aluno.getCursos()));//sem loop

        return resp;
    }

    //update
    public AlunoResp update(Long id, AlunoReq req){
        //ve se existe
        if (!alunoRepository.existsById(id)) {
            return null;
        }
        //pega por id
        Alunos aluno = alunoRepository.findById(id).get();
        Set<Cursos> cursos = new HashSet<>();
        //enche
        aluno.setNome(req.getNome());
        aluno.setEmail(req.getEmail());
        //enche o curso
        for (Long idC : req.getIdCursos()) {
            if (!cursoRepository.existsById(idC)) {
                return null;
            }
            Cursos curso1 = cursoRepository.findById(idC).get();
            cursos.add(curso1);
        }
        aluno.setCursos(cursos);
        alunoRepository.save(aluno);
        AlunoResp resp = new AlunoResp();
        resp.setId(aluno.getId());
        resp.setNome(req.getNome());
        resp.setEmail(req.getEmail());
        resp.setCursos(converterCursos(aluno.getCursos()));
        return  resp;
    }

    //deleta
    public String delete(Long id){
        //ve se existe
        if (!alunoRepository.existsById(id)) {
            return null;
        }
        Alunos aluno = alunoRepository.findById(id).get(); //pega valor
        alunoRepository.delete(aluno); //deleta
        return "Aluno deletado com sucesso";
    }

    //matricular, usa a tabela auxiliar
    public AlunoResp matricular(Long alunoId, Long cursoId) {

        //aqui não teve pra onde fugir ou malabarismo pra entregar o erro correto teve que usar o Exception
        Alunos aluno = alunoRepository.findById(alunoId)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));

        Cursos curso = cursoRepository.findById(cursoId)
                .orElseThrow(() -> new RuntimeException("Curso não encontrado"));

        //ve se não ta repetindo matricula o BD tbm não vai deixar caso passe
        if (aluno.getCursos().contains(curso)) {
            throw new RuntimeException("Aluno já está matriculado neste curso");
        }

        //aquela sincronia né,salvar pra ambos os lados
        aluno.getCursos().add(curso);
        curso.getAlunos().add(aluno);

        //agora salva de verdade
        alunoRepository.save(aluno);

        return getById(alunoId); //pega o aluno por id como retorno usa a função lá em cima
    }

    //deleta
    public void desmatricular(Long alunoId, Long cursoId) {

        //mesma logica da matricula pra outros sentidos
        Alunos aluno = alunoRepository.findById(alunoId)
                .orElseThrow(() ->
                        new RuntimeException("Aluno não encontrado: " + alunoId));

        Cursos curso = cursoRepository.findById(cursoId)
                .orElseThrow(() ->
                        new RuntimeException("Curso não encontrado: " + cursoId));

        if (!aluno.getCursos().contains(curso)) {
            throw new RuntimeException("Aluno não está matriculado neste curso");
        }

        // Remove do lado dono
        aluno.getCursos().remove(curso);

        // Mantém o outro lado sincronizado
        curso.getAlunos().remove(aluno);

        alunoRepository.save(aluno); //salva né
    }

    //converte pra não ter loop deveria ser o nome da função
    //talez um de resp tenhqa sido util tbm
    private Set<AlunoCursoResp> converterCursos(Set<Cursos> cursos) {

        Set<AlunoCursoResp> resposta = new HashSet<>(); //lista

        //for pra preencher
        for (Cursos curso : cursos) {

            AlunoCursoResp dto = new AlunoCursoResp();

            dto.setId(curso.getId());
            dto.setNome(curso.getNome());
            dto.setCargaHoraria(curso.getCargaHoraria());

            resposta.add(dto);
        }

        return resposta; //retorna certo
    }
}
