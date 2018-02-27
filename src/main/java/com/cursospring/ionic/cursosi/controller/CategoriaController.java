package com.cursospring.ionic.cursosi.controller;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.cursospring.ionic.cursosi.dto.CategoriaDTO;
import com.cursospring.ionic.cursosi.model.Categoria;
import com.cursospring.ionic.cursosi.service.CategoriaService;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaController {

	@Autowired
	private CategoriaService categoriaService;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<CategoriaDTO>> listar() {

		List<Categoria> categorias = categoriaService.find();
		List<CategoriaDTO> liesDto = categorias.stream()
				.map(obj -> new CategoriaDTO(obj)).collect(Collectors.toList());
		
		return ResponseEntity.ok().body(liesDto);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Categoria> bucar(@PathVariable Integer id) {

		Categoria categoria = categoriaService.bucar(id);
		return ResponseEntity.ok().body(categoria);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> gravar(@RequestBody Categoria categoria) {

		Categoria novaCategoria = categoriaService.gravar(categoria);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(novaCategoria.getId())
				.toUri();

		return ResponseEntity.created(uri).build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> alterar(@RequestBody Categoria categoria, @PathVariable Integer id) {

		categoria.setId(id);
		categoria = categoriaService.alterar(categoria);

		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleta(@PathVariable Integer id) {

		categoriaService.deleta(id);
		return ResponseEntity.noContent().build();
	}
}
