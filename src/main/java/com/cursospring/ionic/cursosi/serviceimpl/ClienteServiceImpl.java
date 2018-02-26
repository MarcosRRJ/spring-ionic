package com.cursospring.ionic.cursosi.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cursospring.ionic.cursosi.model.Cliente;
import com.cursospring.ionic.cursosi.repository.ClienteRepository;
import com.cursospring.ionic.cursosi.service.ClienteService;
import com.cursospring.ionic.cursosi.service.exceptions.ObjectNotFoundException;

@Service("clienteService")
public class ClienteServiceImpl implements ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	@Override
	public Cliente bucar(Integer id) {

		Cliente cliente = clienteRepository.findOne(id);
		
		if (cliente == null) {
			throw new ObjectNotFoundException("Objeto não encontrado! Id: "+ id
					+", tipo: " + Cliente.class.getName());
		}

		return cliente;
	}

	@Override
	public List<Cliente> find() {
		
		List<Cliente> clientes = clienteRepository.findAll(); 
		
		if (clientes == null) {
			throw new ObjectNotFoundException("Objetos não encontrado! tipo: " + Cliente.class.getName());
		}

		
		return clientes;
	}

}
