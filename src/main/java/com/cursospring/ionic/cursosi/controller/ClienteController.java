package com.cursospring.ionic.cursosi.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cursospring.ionic.cursosi.dto.ClienteDTO;
import com.cursospring.ionic.cursosi.model.Cliente;
import com.cursospring.ionic.cursosi.service.ClienteService;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteController {

	@Autowired
	private ClienteService clienteService;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> bucar(@PathVariable Integer id) {

		Cliente cliente = clienteService.bucar(id);
		return new ResponseEntity<>(cliente, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> alterar(@Valid @RequestBody ClienteDTO clienteDTO, @PathVariable Integer id) {

		Cliente obj = clienteService.paraDTO(clienteDTO);
		obj.setId(id);
		obj = clienteService.alterar(obj);

		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleta(@PathVariable Integer id) {

		clienteService.deleta(id);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<ClienteDTO>> listar() {

		List<Cliente> clientes = clienteService.burcarTodos();
		List<ClienteDTO> liesDto = clientes.stream()
				.map(obj -> new ClienteDTO(obj)).collect(Collectors.toList());
		
		return ResponseEntity.ok().body(liesDto);
	}
	
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
