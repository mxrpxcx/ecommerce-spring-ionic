package com.gxdxy.curso.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gxdxy.curso.domain.ItemPedido;
import com.gxdxy.curso.domain.PagamentoComBoleto;
import com.gxdxy.curso.domain.Pedido;
import com.gxdxy.curso.domain.enums.EstadoPagamento;
import com.gxdxy.curso.repository.ItemPedidoRepository;
import com.gxdxy.curso.repository.PagamentoRepository;
import com.gxdxy.curso.repository.PedidoRepository;
import com.gxdxy.curso.service.exception.ObjectNotFoundException;

@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository repo;
	
	@Autowired
	private BoletoService boletoService;
	
	@Autowired
	private PagamentoRepository pagRepo;
	
	@Autowired
	private ProdutoService prodService;
	
	@Autowired
	private ClienteService cliService;
	
	@Autowired
	private ItemPedidoRepository ipRepo;
	
	@Autowired
	private EmailService emailService;
	
	
	public Pedido buscar(Integer id){
			
		  	Optional<Pedido> obj = repo.findById(id); 
		  	return obj.orElseThrow(()->new ObjectNotFoundException(
		  			"Objeto não encontrado, ID: "+id+", TIPO: "+Pedido.class.getName()));
		  	
		  }

	public Pedido inserir(Pedido obj) {
		obj.setId(null);
		obj.setInstante(new Date());
		obj.setCliente(cliService.buscar(obj.getCliente().getId()));
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		
		if(obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagto, obj.getInstante());
		}
		
		obj = repo.save(obj);
		pagRepo.save(obj.getPagamento());
		
		for(ItemPedido ip:obj.getItens()) {
			ip.setDesconto(0.0);
			ip.setProduto(prodService.buscar(ip.getProduto().getId()));
			ip.setPreco(ip.getProduto().getPreco());
			ip.setPedido(obj); 
		}
		
		ipRepo.saveAll(obj.getItens());
		emailService.enviarConfirmacaoPedidoHTML(obj);
		return obj;
	}
	
}
