package com.cursospring.ionic.cursosi.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.cursospring.ionic.cursosi.dto.ClienteDTO;
import com.cursospring.ionic.cursosi.dto.ClienteNewDTO;
import com.cursospring.ionic.cursosi.model.Cliente;

public interface ClienteService {

	public List<Cliente> burcarTodos();
	
	public Cliente bucar(Integer id);
	
	public Cliente alterar(Cliente cliente);

	public void deleta(Integer id);
	
	public Page<Cliente> listaPagina(Integer page, Integer linesPerPage, String orderBy, String direction);
	
	public Cliente paraDTO(ClienteDTO clienteDTO);

	public Cliente gravar(Cliente obj);
	
	public Cliente paraDTO(ClienteNewDTO clienteNewDTO);
}
