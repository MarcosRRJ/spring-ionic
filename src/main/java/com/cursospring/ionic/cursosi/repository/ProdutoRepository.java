package com.cursospring.ionic.cursosi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cursospring.ionic.cursosi.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

}
