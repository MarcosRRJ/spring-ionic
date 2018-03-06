package com.cursospring.ionic.cursosi.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.cursospring.ionic.cursosi.model.Categoria;
import com.cursospring.ionic.cursosi.model.Produto;
import com.cursospring.ionic.cursosi.repository.CategoriaRepository;
import com.cursospring.ionic.cursosi.repository.ProdutoRepository;
import com.cursospring.ionic.cursosi.service.ProdutoService;
import com.cursospring.ionic.cursosi.service.exceptions.ObjectNotFoundException;

@Service("produtoService")
public class ProdutoServiceImpl implements ProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private CategoriaRepository categoriaRepository;

	@Override
	public Produto buscar(Integer id) {

		Produto produto = produtoRepository.findOne(id);

		if (produto == null) {
			throw new ObjectNotFoundException(
					"Objeto não encontrado! Id: " + id + ", tipo: " + Produto.class.getName());
		}

		return produto;
	}

	@Override
	public Page<Produto> serach(String nome, List<Integer> ids, Integer page,
			Integer linesPerPage, String orderBy, String direction) {

		PageRequest pageRequest = new PageRequest(page, linesPerPage, Direction.valueOf(direction), orderBy);
		List<Categoria> categorias = categoriaRepository.findAll(ids);

		return produtoRepository.findDistinctByNomeContainingAndCategoriasIn(nome, categorias, pageRequest);
	}

}
