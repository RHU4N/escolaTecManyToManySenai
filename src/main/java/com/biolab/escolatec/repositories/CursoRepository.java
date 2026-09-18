package com.biolab.escolatec.repositories;

import com.biolab.escolatec.entities.Alunos;
import com.biolab.escolatec.entities.Cursos;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CursoRepository extends JpaRepository<Cursos, Long> {


}
