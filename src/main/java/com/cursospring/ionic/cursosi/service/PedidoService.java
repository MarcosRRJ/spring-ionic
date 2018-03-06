package com.cursospring.ionic.cursosi.service;

import java.util.List;

import com.cursospring.ionic.cursosi.model.Pedido;

public interface PedidoService {

	public List<Pedido> find();

	public Pedido bucar(Integer id);

	public Pedido gravar(Pedido pedido);
}
