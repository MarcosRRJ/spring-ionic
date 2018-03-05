package com.cursospring.ionic.cursosi.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.cursospring.ionic.cursosi.dto.CategoriaDTO;
import com.cursospring.ionic.cursosi.model.Categoria;
import com.cursospring.ionic.cursosi.repository.CategoriaRepository;
import com.cursospring.ionic.cursosi.service.CategoriaService;
import com.cursospring.ionic.cursosi.service.exceptions.DataIntegrityException;
import com.cursospring.ionic.cursosi.service.exceptions.ObjectNotFoundException;

@Service("categoriaService")
public class CategoriaServiceImpl implements CategoriaService {

	@Autowired
	private CategoriaRepository categoriaRepository;

	@Override
	public Categoria bucar(Integer id) {

		Categoria categoria = verificaSeENulo(id);

		return categoria;
	}

	@Override
	public List<Categoria> find() {

		List<Categoria> categorias = categoriaRepository.findAll();

		if (categorias == null) {
			throw new ObjectNotFoundException("Objetos não encontrado! tipo: " + Categoria.class.getName());
		}

		return categorias;
	}

	@Override
	public Categoria gravar(Categoria categoria) {
		categoria.setId(null);
		return categoriaRepository.save(categoria);
	}

	@Override
	public Categoria alterar(Categoria categoria) {
		Categoria newCat = bucar(categoria.getId());
		alterarData(newCat, categoria);
		return categoriaRepository.save(newCat);
	}

	private void alterarData(Categoria newCat, Categoria categoria) {
		newCat.setNome(categoria.getNome());
	}

	private Categoria verificaSeENulo(Integer id) {
		Categoria categoria = categoriaRepository.findOne(id);

		if (categoria == null) {
			throw new ObjectNotFoundException(
					"Objeto não encontrado! Id: " + id + ", tipo: " + Categoria.class.getName());
		}
		return categoria;
	}

	@Override
	public void deleta(Integer id) {
		bucar(id);
		try {
			categoriaRepository.delete(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível deletar uma categoria que possue produtos");
		}

	}

	@Override
	public Page<Categoria> listaPagina(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = new PageRequest(page, linesPerPage,Direction.valueOf(direction), orderBy);		
		return categoriaRepository.findAll(pageRequest);
	}
	
	public Categoria paraDTO(CategoriaDTO categoriaDTO){
		return new Categoria(categoriaDTO.getId(), categoriaDTO.getNome());
		
	}
}
