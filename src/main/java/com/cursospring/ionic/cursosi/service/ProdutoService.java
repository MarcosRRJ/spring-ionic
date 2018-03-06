package com.cursospring.ionic.cursosi.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.cursospring.ionic.cursosi.model.Produto;

public interface ProdutoService {

	public Produto buscar(Integer id);

	public Page<Produto> serach(String nome, List<Integer> ids,
			Integer page, Integer linesPerPage, String orderBy, String direction);

}
