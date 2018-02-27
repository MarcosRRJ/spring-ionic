package com.cursospring.ionic.cursosi.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.cursospring.ionic.cursosi.model.Categoria;
import com.cursospring.ionic.cursosi.service.CategoriaService;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaController {

	@Autowired
	private CategoriaService categoriaService;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> listar() {

		List<Categoria> categorias = new ArrayList<>();

		return new ResponseEntity<>(categorias, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> bucar(@PathVariable Integer id) {

		Categoria categoria = categoriaService.bucar(id);
		return new ResponseEntity<>(categoria, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> gravar(@RequestBody Categoria categoria) {

		Categoria novaCategoria = categoriaService.gravar(categoria);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(novaCategoria.getId())
				.toUri();

		return ResponseEntity.created(uri).build();
	}

}