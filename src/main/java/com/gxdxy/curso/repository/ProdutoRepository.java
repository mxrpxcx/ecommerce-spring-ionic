package com.gxdxy.curso.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gxdxy.curso.domain.Categoria;
import com.gxdxy.curso.domain.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

	@Query("SELECT DISTINCT obj FROM Produto obj INNER JOIN obj.categorias cat WHERE obj.nome LIKE %:nome%"
			+ " AND cat IN :categorias")
	Page<Produto> buscar(@Param("nome") String nome, @Param("categorias") List<Categoria> categorias, Pageable pageRequest);
}
