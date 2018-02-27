package com.cursospring.ionic.cursosi.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cursospring.ionic.cursosi.model.Pedido;
import com.cursospring.ionic.cursosi.repository.PedidoRepository;
import com.cursospring.ionic.cursosi.service.PedidoService;
import com.cursospring.ionic.cursosi.service.exceptions.ObjectNotFoundException;

@Service("pedidoService")
public class PedidoServiceImpl implements PedidoService {

	@Autowired
	private PedidoRepository pedidoRepository;

	@Override
	public Pedido bucar(Integer id) {

		Pedido pedido = pedidoRepository.findOne(id);

		if (pedido == null) {
			throw new ObjectNotFoundException(
					"Objeto não encontrado! Id: " + id + ", tipo: " + Pedido.class.getName());
		}

		return pedido;
	}

	@Override
	public List<Pedido> find() {

		List<Pedido> pedidos = pedidoRepository.findAll();

		if (pedidos == null) {
			throw new ObjectNotFoundException("Objetos não encontrado! tipo: " + Pedido.class.getName());
		}

		return null;
	}

}
