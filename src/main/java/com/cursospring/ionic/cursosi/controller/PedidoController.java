package com.cursospring.ionic.cursosi.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cursospring.ionic.cursosi.model.Pedido;
import com.cursospring.ionic.cursosi.service.PedidoService;

@RestController
@RequestMapping(value = "/pedidos")
public class PedidoController {

	@Autowired
	private PedidoService pedidoService;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> listar() {

		List<Pedido> pedidos = new ArrayList<>();

		return new ResponseEntity<>(pedidos, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> bucar(@PathVariable Integer id) {

		Pedido pedido = pedidoService.bucar(id);
		return new ResponseEntity<>(pedido, HttpStatus.OK);
	}
}
