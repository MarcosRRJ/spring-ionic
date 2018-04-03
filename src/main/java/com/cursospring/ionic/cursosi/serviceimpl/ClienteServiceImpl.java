package com.cursospring.ionic.cursosi.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.cursospring.ionic.cursosi.dto.ClienteDTO;
import com.cursospring.ionic.cursosi.dto.ClienteNewDTO;
import com.cursospring.ionic.cursosi.model.Cidade;
import com.cursospring.ionic.cursosi.model.Cliente;
import com.cursospring.ionic.cursosi.model.Endereco;
import com.cursospring.ionic.cursosi.model.enums.TipoCliente;
import com.cursospring.ionic.cursosi.repository.CidadeRepository;
import com.cursospring.ionic.cursosi.repository.ClienteRepository;
import com.cursospring.ionic.cursosi.repository.EnderecoRepository;
import com.cursospring.ionic.cursosi.service.ClienteService;
import com.cursospring.ionic.cursosi.service.exceptions.DataIntegrityException;
import com.cursospring.ionic.cursosi.service.exceptions.ObjectNotFoundException;

@Service("clienteService")
public class ClienteServiceImpl implements ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;

	@Autowired
	private BCryptPasswordEncoder bp;
	
	@Override
	public Cliente bucar(Integer id) {

		Cliente cliente = clienteRepository.findOne(id);

		if (cliente == null) {
			throw new ObjectNotFoundException(
					"Objeto não encontrado! Id: " + id + ", tipo: " + Cliente.class.getName());
		}

		return cliente;
	}

	@Override
	public List<Cliente> burcarTodos() {

		List<Cliente> clientes = clienteRepository.findAll();

		if (clientes == null) {
			throw new ObjectNotFoundException("Objetos não encontrado! tipo: " + Cliente.class.getName());
		}

		return clientes;
	}

	// private Cliente verificaSeENulo(Integer id) {
	// Cliente cliente = clienteRepository.findOne(id);
	//
	// if (cliente == null) {
	// throw new ObjectNotFoundException(
	// "Objeto não encontrado! Id: " + id + ", tipo: " +
	// Cliente.class.getName());
	// }
	// return cliente;
	// }

	@Override
	public Cliente gravar(Cliente cliente) {
		cliente.setId(null);
		clienteRepository.save(cliente);
		enderecoRepository.save(cliente.getEnderecos());
		return cliente;
	}

	@Override
	public Cliente alterar(Cliente cliente) {
		Cliente newCli = bucar(cliente.getId());
		alterarData(newCli, cliente);
		return clienteRepository.save(newCli);
	}

	@Override
	public void deleta(Integer id) {
		bucar(id);
		try {
			clienteRepository.delete(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível deletar um cliente porque há pedidos relacionadas");
		}

	}

	@Override
	public Page<Cliente> listaPagina(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = new PageRequest(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return clienteRepository.findAll(pageRequest);
	}

	public Cliente paraDTO(ClienteDTO clienteDTO) {
		return new Cliente(clienteDTO.getId(), clienteDTO.getNome(), clienteDTO.getEmail(), null, null, null);

	}

	private void alterarData(Cliente newCli, Cliente cliente) {
		newCli.setNome(cliente.getNome());
		newCli.setEmail(cliente.getEmail());
	}

	@Override
	public Cliente paraDTO(ClienteNewDTO clienteNewDTO) {
		
		Cliente cli = new Cliente(null, clienteNewDTO.getNome(), clienteNewDTO.getEmail(), clienteNewDTO.getCpfOuCnpj(),
				TipoCliente.toEnum(clienteNewDTO.getTipo()), bp.encode(clienteNewDTO.getSenha()));
		
		Cidade cid = cidadeRepository.findOne(clienteNewDTO.getCidadeId());
		
		Endereco end = new Endereco(null, clienteNewDTO.getLogradouro(), clienteNewDTO.getNumero(),
				clienteNewDTO.getComplemento(), clienteNewDTO.getBairro(), clienteNewDTO.getCep(), cli, cid);
		
		cli.getEnderecos().add(end);
		cli.getTelefones().add(clienteNewDTO.getTelefone1());
		if (clienteNewDTO.getTelefone2() != null) {
			cli.getTelefones().add(clienteNewDTO.getTelefone2());
		}
		if (clienteNewDTO.getTelefone3() != null) {
			cli.getTelefones().add(clienteNewDTO.getTelefone3());
		}
		
		return cli;
	}
}
