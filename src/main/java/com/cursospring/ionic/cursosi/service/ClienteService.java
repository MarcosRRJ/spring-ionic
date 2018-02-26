package com.cursospring.ionic.cursosi.service;

import java.util.List;

import com.cursospring.ionic.cursosi.model.Cliente;

public interface ClienteService {

	public List<Cliente> find();
	
	public Cliente bucar(Integer id);
}
