package com.cursospring.ionic.cursosi.serviceimpl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cursospring.ionic.cursosi.model.ItemPedido;
import com.cursospring.ionic.cursosi.model.PagamentoComBoleto;
import com.cursospring.ionic.cursosi.model.Pedido;
import com.cursospring.ionic.cursosi.model.enums.EstadoPagamento;
import com.cursospring.ionic.cursosi.repository.ClienteRepository;
import com.cursospring.ionic.cursosi.repository.ItemPedidoRepository;
import com.cursospring.ionic.cursosi.repository.PagamentoRepository;
import com.cursospring.ionic.cursosi.repository.PedidoRepository;
import com.cursospring.ionic.cursosi.repository.ProdutoRepository;
import com.cursospring.ionic.cursosi.service.EmailService;
import com.cursospring.ionic.cursosi.service.PedidoService;
import com.cursospring.ionic.cursosi.service.exceptions.ObjectNotFoundException;

@Service("pedidoService")
public class PedidoServiceImpl implements PedidoService {

	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	private BoletoService boletoService;

	@Autowired
	private PagamentoRepository pagamentoRepository;

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private ItemPedidoRepository itemPedidoRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private EmailService emailService;

	@Override
	public Pedido bucar(Integer id) {

		Pedido pedido = pedidoRepository.findOne(id);

		if (pedido == null) {
			throw new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", tipo: " + Pedido.class.getName());
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

	@Override
	public Pedido gravar(Pedido pedido) {

		pedido.setId(null);
		pedido.setInstante(new Date());

		pedido.setCliente(clienteRepository.findOne(pedido.getCliente().getId()));

		pedido.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		pedido.getPagamento().setPedido(pedido);

		if (pedido.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto) pedido.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagto, pedido.getInstante());
		}

		pedido = pedidoRepository.save(pedido);
		pagamentoRepository.save(pedido.getPagamento());

		for (ItemPedido ip : pedido.getItens()) {
			ip.setDesconto(0.0);
			ip.setProduto(produtoRepository.findOne(ip.getProduto().getId()));
			ip.setPreco(ip.getProduto().getPreco());
			ip.setPedido(pedido);
		}

		itemPedidoRepository.save(pedido.getItens());
		System.out.println(pedido);

		emailService.sendOrderConfimationEmail(pedido);
		
		return pedido;
	}

}
