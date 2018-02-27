package com.cursospring.ionic.cursosi.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cursospring.ionic.cursosi.model.Categoria;
import com.cursospring.ionic.cursosi.repository.CategoriaRepository;
import com.cursospring.ionic.cursosi.service.CategoriaService;
import com.cursospring.ionic.cursosi.service.exceptions.ObjectNotFoundException;

@Service("categoriaService")
public class CategoriaServiceImpl implements CategoriaService {

	@Autowired
	private CategoriaRepository categoriaRepository;

	@Override
	public Categoria bucar(Integer id) {

		Categoria categoria = categoriaRepository.findOne(id);

		if (categoria == null) {
			throw new ObjectNotFoundException(
					"Objeto não encontrado! Id: " + id + ", tipo: " + Categoria.class.getName());
		}

		return categoria;
	}

	@Override
	public List<Categoria> find() {

		List<Categoria> categorias = categoriaRepository.findAll();

		if (categorias == null) {
			throw new ObjectNotFoundException("Objetos não encontrado! tipo: " + Categoria.class.getName());
		}

		return null;
	}

	@Override
	public Categoria gravar(Categoria categoria) {
		categoria.setId(null);
		return categoriaRepository.save(categoria);
	}

}
