package com.biolab.escolatec.repositories;

import com.biolab.escolatec.entities.Alunos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//repositorio para puxar dependencias prontas do JPA
@Repository
public interface AlunoRepository extends JpaRepository<Alunos, Long> {

}
