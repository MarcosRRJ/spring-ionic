package com.cursospring.ionic.cursosi.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.cursospring.ionic.cursosi.dto.CategoriaDTO;
import com.cursospring.ionic.cursosi.model.Categoria;

public interface CategoriaService {

	public List<Categoria> find();

	public Categoria bucar(Integer id);

	public Categoria gravar(Categoria categoria);

	public Categoria alterar(Categoria categoria);

	public void deleta(Integer id);
	
	public Page<Categoria> listaPagina(Integer page, Integer linesPerPage, String orderBy, String direction);
	
	public Categoria paraDTO(CategoriaDTO categoriaDTO);
}
