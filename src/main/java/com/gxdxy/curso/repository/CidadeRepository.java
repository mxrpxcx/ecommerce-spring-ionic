package com.gxdxy.curso.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gxdxy.curso.domain.Cidade;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Integer> {

	@Transactional
	@Query("SELECT obj FROM Cidade obj WHERE obj.estado.id = :idEstado ORDER BY obj.nome")
	public List<Cidade> buscarTodos(@Param("idEstado") Integer idEstado);
	
}
