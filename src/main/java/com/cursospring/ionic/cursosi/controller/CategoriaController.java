package com.cursospring.ionic.cursosi.controller;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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

	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> gravar(@RequestBody @Valid CategoriaDTO categoriaDTO) {
		Categoria obj = categoriaService.paraDTO(categoriaDTO);
		
		obj = categoriaService.gravar(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId())
				.toUri();

		return ResponseEntity.created(uri).build();
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> alterar(@Valid @RequestBody CategoriaDTO categoriaDTO, @PathVariable Integer id) {

		Categoria obj = categoriaService.paraDTO(categoriaDTO);
		obj.setId(id);
		obj = categoriaService.alterar(obj);

		return ResponseEntity.noContent().build();
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleta(@PathVariable Integer id) {

		categoriaService.deleta(id);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value="/pagina", method = RequestMethod.GET)
	public ResponseEntity<Page<CategoriaDTO>> listarPagina(@RequestParam(value="page" , defaultValue="0") Integer page, 
			@RequestParam(value="linesPerPage" , defaultValue="24") Integer linesPerPage,
			@RequestParam(value="orderBy" , defaultValue="nome") String orderBy, 
			@RequestParam(value="direction" , defaultValue="ASC") String direction) {

		Page<Categoria> categorias = categoriaService.listaPagina(page, 
				linesPerPage, orderBy, direction);
		
		Page<CategoriaDTO> liesDto = categorias.map(obj -> new CategoriaDTO(obj));
		
		return ResponseEntity.ok().body(liesDto);
	}
	
}
