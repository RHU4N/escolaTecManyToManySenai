package com.biolab.escolatec.repositories;

import com.biolab.escolatec.entities.Alunos;
import com.biolab.escolatec.entities.Cursos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//repositorio para puxar dependencias prontas do JPA
@Repository
public interface CursoRepository extends JpaRepository<Cursos, Long> {

}
