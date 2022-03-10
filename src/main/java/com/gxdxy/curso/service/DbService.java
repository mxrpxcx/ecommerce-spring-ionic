package com.gxdxy.curso.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.gxdxy.curso.domain.Categoria;
import com.gxdxy.curso.domain.Cidade;
import com.gxdxy.curso.domain.Cliente;
import com.gxdxy.curso.domain.Endereco;
import com.gxdxy.curso.domain.Estado;
import com.gxdxy.curso.domain.ItemPedido;
import com.gxdxy.curso.domain.Pagamento;
import com.gxdxy.curso.domain.PagamentoComBoleto;
import com.gxdxy.curso.domain.PagamentoComCartao;
import com.gxdxy.curso.domain.Pedido;
import com.gxdxy.curso.domain.Produto;
import com.gxdxy.curso.domain.enums.EstadoPagamento;
import com.gxdxy.curso.domain.enums.Perfil;
import com.gxdxy.curso.domain.enums.TipoCliente;
import com.gxdxy.curso.repository.CategoriaRepository;
import com.gxdxy.curso.repository.CidadeRepository;
import com.gxdxy.curso.repository.ClienteRepository;
import com.gxdxy.curso.repository.EnderecoRepository;
import com.gxdxy.curso.repository.EstadoRepository;
import com.gxdxy.curso.repository.ItemPedidoRepository;
import com.gxdxy.curso.repository.PagamentoRepository;
import com.gxdxy.curso.repository.PedidoRepository;
import com.gxdxy.curso.repository.ProdutoRepository;

@Service
public class DbService {
	
	@Autowired
	private CategoriaRepository categoriaRepo;
	
	@Autowired
	private BCryptPasswordEncoder pe;
	
	@Autowired
	private ProdutoRepository produtoRepo;
	
	@Autowired
	private EstadoRepository estadoRepo;
	
	@Autowired
	private CidadeRepository cidadeRepo;
	
	@Autowired
	private ClienteRepository clienteRepo;
	
	@Autowired
	private EnderecoRepository enderecoRepo;
	
	@Autowired
	private PedidoRepository pedidoRepo;
	
	@Autowired
	private PagamentoRepository pagamentoRepo;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepo;
	
	
	
	public void instantiateTestDatabase() throws ParseException {
		
		Categoria c1 = new Categoria(null, "Samsung");
		Categoria c2 = new Categoria(null, "Apple");
		Categoria c3 = new Categoria(null, "Xiaomi");
		Categoria c4 = new Categoria(null, "Motorola");
		Categoria c5 = new Categoria(null, "LG");
		
		Produto p1 = new Produto(null, "S20 FE", 1900.00); 
		Produto p2 = new Produto(null, "iPhone SE", 2500.00);
		Produto p3 = new Produto(null, "Mi 11", 3500.00);
		
		Produto p4 = new Produto(null, "Edge 30 PRO", 5500.00);
		Produto p5 = new Produto(null, "S22 ULTRA", 15000.00);
		Produto p6 = new Produto(null, "Velvet", 15000.00);
		
		c1.getProdutos().addAll(Arrays.asList(p1,p5));
		c2.getProdutos().add(p2);
		c3.getProdutos().add(p3);
		c4.getProdutos().add(p4);
		c5.getProdutos().add(p6);
		
		p1.getCategorias().add(c1);
		p2.getCategorias().add(c2);
		p3.getCategorias().add(c3);
		p4.getCategorias().add(c4);
		p5.getCategorias().add(c1);
		p6.getCategorias().add(c5);
		
		categoriaRepo.saveAll(Arrays.asList(c1,c2, c3, c4, c5));
		produtoRepo.saveAll(Arrays.asList(p1,p2,p3,p4,p5,p6));
		
		Estado e1 = new Estado(null, "Minas Gerais");
		Estado e2 = new Estado(null, "São Paulo");
		
		Cidade cid1 = new Cidade(null, "Uberlandia", e1);
		Cidade cid2 = new Cidade(null, "São Paulo", e2);
		Cidade cid3 = new Cidade(null, "Campinas", e2);
		
		e1.getCidades().add(cid1);
		e2.getCidades().addAll(Arrays.asList(cid2,cid3));
		
		estadoRepo.saveAll(Arrays.asList(e1,e2));
		cidadeRepo.saveAll(Arrays.asList(cid1,cid2,cid3));
		
		Cliente cli1 = new Cliente(null,"Renan Godoy","renanfgfg@gmail.com","88640825068", TipoCliente.PESSOAFISICA,
				pe.encode("Teste"));
		cli1.getTelefones().addAll(Arrays.asList("190","192"));
		cli1.addPerfil(Perfil.ADMIN);
		
		Cliente cli2 = new Cliente(null,"Maria Silva","maria@gmail.com","48345886027", TipoCliente.PESSOAFISICA,
				pe.encode("Testes"));
		cli2.getTelefones().addAll(Arrays.asList("190","192"));
		
		Endereco end1 = new Endereco(null,"Rua Flores","300","Apto 303","Jardim","3030",cli1,cid1);
		Endereco end2 = new Endereco(null,"Avenida Matos","105","Sala 800","Centro","3031",cli1,cid2);
		Endereco end3 = new Endereco(null,"Avenida Teste","105","Sala 800","Centro","3031",cli2,cid2);

		cli1.getEnderecos().addAll(Arrays.asList(end1,end2));
		cli2.getEnderecos().add(end3);
		
		clienteRepo.saveAll(Arrays.asList(cli1,cli2));
		enderecoRepo.saveAll(Arrays.asList(end1,end2,end3));
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");
		Pedido ped1 = new Pedido(null,sdf.parse("01/01/2022 10:30"),cli1,end1);
		Pedido ped2 = new Pedido(null,sdf.parse("05/01/2022 21:20"),cli1,end2);
		
		Pagamento pag1 = new PagamentoComCartao(null,EstadoPagamento.QUITADO,ped1,6);
		ped1.setPagamento(pag1);
		
		Pagamento pag2 = new PagamentoComBoleto(null,EstadoPagamento.PENDENTE,ped2,sdf.parse("05/05/2022 00:00"),null);
		ped2.setPagamento(pag2);
		
		cli1.getPedidos().addAll(Arrays.asList(ped1,ped2));
		
		pedidoRepo.saveAll(Arrays.asList(ped1,ped2));
		pagamentoRepo.saveAll(Arrays.asList(pag1,pag2));
		
		ItemPedido ip1 = new ItemPedido(ped1,p1,0.00,1,p1.getPreco());
		ItemPedido ip2 = new ItemPedido(ped1,p3,0.00,1,p3.getPreco());
		ItemPedido ip3 = new ItemPedido(ped2,p2,100.00,1,p2.getPreco());
		
		ped1.getItens().addAll(Arrays.asList(ip1,ip2));
		ped2.getItens().add(ip3);
		
		p1.getItens().add(ip1);
		p2.getItens().add(ip3);
		p3.getItens().add(ip2);
		
		itemPedidoRepo.saveAll(Arrays.asList(ip1,ip2,ip3));
		
	}
	
}
