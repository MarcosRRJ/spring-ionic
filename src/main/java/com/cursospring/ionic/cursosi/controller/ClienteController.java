package com.cursospring.ionic.cursosi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cursospring.ionic.cursosi.model.Cliente;
import com.cursospring.ionic.cursosi.service.ClienteService;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteController {

	@Autowired
	private ClienteService clienteService;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Cliente>> listar() {

		List<Cliente> clientes = clienteService.find();

		return ResponseEntity.ok(clientes);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> bucar(@PathVariable Integer id) {

		Cliente cliente = clienteService.bucar(id);
		return new ResponseEntity<>(cliente, HttpStatus.OK);
	}
}
