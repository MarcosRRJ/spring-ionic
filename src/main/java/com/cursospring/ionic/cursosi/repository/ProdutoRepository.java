package com.cursospring.ionic.cursosi.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cursospring.ionic.cursosi.model.Categoria;
import com.cursospring.ionic.cursosi.model.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

	// Script velho
	// @Query("SELECT DISTINCT obj FROM Produto obj INNER JOIN obj.categorias
	// cat WHERE "
	// + "obj.nome LIKE %:nome% AND cat IN :categorias")
	// public Page<Produto> serach(@Param("nome") String nome,
	// @Param("categorias") List<Categoria> categorias,
	// Pageable pageRequest);

	@Transactional(readOnly = true)
	public Page<Produto> findDistinctByNomeContainingAndCategoriasIn(String nome, List<Categoria> categorias,
			Pageable pageRequest);

}
