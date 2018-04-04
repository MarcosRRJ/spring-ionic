package com.cursospring.ionic.cursosi.controller;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.cursospring.ionic.cursosi.dto.ClienteDTO;
import com.cursospring.ionic.cursosi.dto.ClienteNewDTO;
import com.cursospring.ionic.cursosi.model.Cliente;
import com.cursospring.ionic.cursosi.repository.ClienteRepository;
import com.cursospring.ionic.cursosi.service.ClienteService;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteController {

	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private ClienteRepository repo;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> bucar(@PathVariable Integer id) {

		Cliente cliente = clienteService.bucar(id);
		return new ResponseEntity<>(cliente, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> gravar(@RequestBody @Valid ClienteNewDTO clienteNewDTO) {
		Cliente obj = clienteService.paraDTO(clienteNewDTO);
		
		obj = clienteService.gravar(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId())
				.toUri();

		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> alterar(@Valid @RequestBody ClienteDTO clienteDTO, @PathVariable Integer id) {

		Cliente obj = clienteService.paraDTO(clienteDTO);
		obj.setId(id);
		obj = clienteService.alterar(obj);

		return ResponseEntity.noContent().build();
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleta(@PathVariable Integer id) {

		clienteService.deleta(id);
		return ResponseEntity.noContent().build();
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<ClienteDTO>> listar() {

		List<Cliente> clientes = clienteService.burcarTodos();
		List<ClienteDTO> liesDto = clientes.stream()
				.map(obj -> new ClienteDTO(obj)).collect(Collectors.toList());
		
		return ResponseEntity.ok().body(liesDto);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value = "/nomeEmail", method = RequestMethod.GET)
	public ResponseEntity<List<ClienteDTO>> listarNomeEmail() {

		List<ClienteDTO> liesDto = repo.findNomeAndEmail().stream()
				.map(obj -> new ClienteDTO(obj.getId(),obj.getNome(), obj.getEmail())).collect(Collectors.toList());
		
		return ResponseEntity.ok().body(liesDto);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value="/pagina", method = RequestMethod.GET)
	public ResponseEntity<Page<ClienteDTO>> listarPagina(@RequestParam(value="page" , defaultValue="0") Integer page, 
			@RequestParam(value="linesPerPage" , defaultValue="24") Integer linesPerPage,
			@RequestParam(value="orderBy" , defaultValue="nome") String orderBy, 
			@RequestParam(value="direction" , defaultValue="ASC") String direction) {

		Page<Cliente> clientes = clienteService.listaPagina(page, 
				linesPerPage, orderBy, direction);
		
		Page<ClienteDTO> liesDto = clientes.map(obj -> new ClienteDTO(obj));
		
		return ResponseEntity.ok().body(liesDto);
	}
}
