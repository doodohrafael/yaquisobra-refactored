
package br.com.rafael.yaquisobra.beans;

import static br.com.rafael.yaquisobra.beans.LoginMBean.usuarioLogado;
import static br.com.rafael.yaquisobra.util.Constantes.*;
import static br.com.rafael.yaquisobra.util.Mensagens.*;
import static org.primefaces.PrimeFaces.current;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.rafael.yaquisobra.controller.ClienteController;
import br.com.rafael.yaquisobra.controller.ItemController;
import br.com.rafael.yaquisobra.controller.ProdutoController;
import br.com.rafael.yaquisobra.controller.VendaController;
import br.com.rafael.yaquisobra.domain.model.Cidade;
import br.com.rafael.yaquisobra.domain.model.Cliente;
import br.com.rafael.yaquisobra.domain.model.Contato;
import br.com.rafael.yaquisobra.domain.model.Endereco;
import br.com.rafael.yaquisobra.domain.model.Estado;
import br.com.rafael.yaquisobra.domain.model.FormaPagamento;
import br.com.rafael.yaquisobra.domain.model.Item;
import br.com.rafael.yaquisobra.domain.model.Produto;
import br.com.rafael.yaquisobra.domain.model.Usuario;
import br.com.rafael.yaquisobra.domain.model.Venda;
import br.com.rafael.yaquisobra.util.Global;
import br.com.rafael.yaquisobra.util.Validacao;
import lombok.Data;

@SuppressWarnings("deprecation")
@ManagedBean
@SessionScoped
@Data
public class VendaMBean {

	private Produto produto;
	private Usuario usuarioLogado;
	private Venda venda;
	private Date dataInicial;
	private Date dataFinal;
	private Integer vendasPorData = 0;
	private boolean teste;
	private Validacao validar;
	private FormaPagamento formaPagamento;
	private Contato contato;
	private Endereco endereco;
	
	private ProdutoController produtoController;
	private ItemController itemController;
	private VendaController vendaController;
	private ClienteController clienteController;
	
	private List<Produto> colecaoProdutos;
	private List<Produto> colecaoProdutosItens;
	private List<Venda> colecaoVendas;
	private List<Venda> vendasPorDatas;
	private List<Item> colecaoItens;
	
	public VendaMBean() {
		if (produto == null) {
			produto = new Produto();
		}
		produtoController = new ProdutoController();
		vendaController = new VendaController();
		usuarioLogado = usuarioLogado();
		colecaoProdutos = produtoController.listarProdutos();
		colecaoItens = new ArrayList<>();
		venda = new Venda();
		venda.setValorTotal(new BigDecimal("0.00"));
		venda.setValorFrete(new BigDecimal("0.00"));
	}
	
	public String salvar() {
		try {
			if(validarCampos()) {
				venda.setUsuario(usuarioLogado);
				
				List<Endereco> enderecos = new ArrayList<>();
				enderecos.add(endereco);
				venda.getCliente().setEnderecos(enderecos);
				
				List<Contato> contatos = new ArrayList<>();
				contatos.add(contato);
				venda.getCliente().setContatos(contatos);
				
				clienteController.incluir(venda.getCliente());

				List<FormaPagamento> formaPagamentos = new ArrayList<>();
				formaPagamentos.add(formaPagamento);
				venda.setFormaPagamentos(formaPagamentos);
				
				vendaController.incluir(venda);
				for(Item item : colecaoItens) {
					item.setVenda(venda);
					vendaController.incluirItem(item);
				}
				venda.setItens(colecaoItens);
				

				limparAposVenda();
				
				listarProdutos();
				MensagemInfo(MSG_VENDA_SUCESSO);
				fecharDialogFinalizar();
				return telaConsultarVenda;
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
			MensagemErro(MSG_NAO_SALVO_SUCESSO);
		}
		return null;
	}
	
	public void limparAposVenda() {
		venda = new Venda();
		venda.setValorTotal(new BigDecimal("0.00"));
		colecaoItens = new ArrayList<>();
		carrinho();
	}
	
	public void carrinho() {
		venda.setValorFrete(new BigDecimal("0.00"));
		venda.setCliente(new Cliente());
		venda.setUsuario(new Usuario());
		endereco = new Endereco();
		contato = new Contato();
		endereco.setCidade(new Cidade());
		endereco.getCidade().setEstado(new Estado());
		formaPagamento = new FormaPagamento();
		vendaController = new VendaController();
		clienteController = new ClienteController();
	}
	
	public List<FormaPagamento> getFormaPagamentos() {
		List<FormaPagamento> formaPagamentos = new ArrayList<>();
		formaPagamentos = vendaController.listarFormaPagamentos();
		return formaPagamentos;
	}

	public void consultarVendas() {
		try {
			venda = new Venda();
			if (vendasPorData == 1) {
				colecaoVendas = vendaController.listarVendasPorData(dataInicial, dataFinal);
			} else {
				colecaoVendas = vendaController.listarVendas();
			}
			Global.limparItens = 1;
		} catch (RuntimeException e) {
			e.printStackTrace();
			MensagemErro(MSG_DADOS_NAO_ENCONTRADOS);
		}
	}

	public void adicionarItens(Produto produto) {

		int posicaoEncontrada = -1;
		for (int p = 0; p < colecaoItens.size() && posicaoEncontrada < 0; p++) {
			Item itemTemporario = colecaoItens.get(p);
			if (itemTemporario.getProduto().equals(produto)) {
				posicaoEncontrada = p;
			}
		}

		Item item = new Item();
		item.setProduto(produto);
		if (posicaoEncontrada < 0 && produto.getQuantidade() > 0) {
			item.setQuantidade(1);
			item.setValorParcial(produto.getPreco());
			colecaoItens.add(item);
		} else if (produto.getQuantidade() > 0) {
			Item itemTemporario = colecaoItens.get(posicaoEncontrada);
			item.setQuantidade(itemTemporario.getQuantidade() + 1);
			item.setValorParcial(itemTemporario.getProduto().getPreco()
					.multiply(new BigDecimal(item.getQuantidade())));
			colecaoItens.set(posicaoEncontrada, item);

		}

		if (produto.getQuantidade() > 0) {
			produto.setQuantidade(produto.getQuantidade() - 1);
			if (venda.getQuantidadeItensTotal() == null) {
				venda.setQuantidadeItensTotal(1);
			} else {
				venda.setQuantidadeItensTotal(venda.getQuantidadeItensTotal() + 1);
			}

			produtoController.alterar(produto);
			if (venda.getValorTotal() == null) {
				venda.setValorTotal(new BigDecimal("0.00"));
			}
			venda.setValorTotal(venda.getValorTotal().add(produto.getPreco()));

		} else if (produto.getQuantidade() == 0) {
			MensagemAlerta("Produto deste tipo sem estoque.");
		}

		System.out.println("VALOR TOTAL: " + venda.getValorTotal());

	}

	public void removerItem(Item item) {
		int posicaoEncontrada = -1;
		for (int p = 0; p < colecaoItens.size() && posicaoEncontrada < 0; p++) {
			Item itemDaLista = colecaoItens.get(p);
			if (itemDaLista.getProduto().equals(item.getProduto())) {
				posicaoEncontrada = p;
			}
		}
		colecaoItens.remove(posicaoEncontrada);

		// if (item.getProduto().getQuantidade() >= 0) {//
		item.getProduto().setQuantidade(item.getProduto().getQuantidade() + item.getQuantidade());
		produtoController.alterar(item.getProduto());
		venda.setValorTotal((venda.getValorTotal().subtract(item.getValorParcial())));
		if (venda.getQuantidadeItensTotal() != null) {
			venda.setQuantidadeItensTotal(venda.getQuantidadeItensTotal() - item.getQuantidade()); // removo o objeto item todo
		} else if (venda.getQuantidadeItensTotal() != null && venda.getQuantidadeItensTotal() == 0) {
			// venda.setQuantidadeItensTotal(null);
			MensagemAlerta(MSG_VOCE_PRECISA_POR_ALGO_NO_CARRINHO);
		}

	}

	public Boolean validarCampos() {
		validar = new Validacao();

		boolean freteOK = validar.notNullNotEmptyFrete(venda.getValorFrete(), 
				MSG_CAMPO_OBRIGATORIO, LABEL_FRETE);
		
		boolean formaPagamentoOK = validar.notNullNotEmpty(formaPagamento, 
				MSG_CAMPO_OBRIGATORIO, LABEL_TIPO_PAGAMENTO);
		
		boolean nomeOK = validar.notNullNotEmpty(venda.getCliente().getNome(), 
				MSG_CAMPO_OBRIGATORIO, LABEL_NOME);

		boolean telefoneOK = validar.notNullNotEmpty(contato.getTelefone(), 
				MSG_CAMPO_OBRIGATORIO, LABEL_TELEFONE);

		boolean logradouroOK = validar.notNullNotEmpty(endereco.getLogradouro(),
				MSG_CAMPO_OBRIGATORIO, LABEL_LOGRADOURO);

		boolean numeroOK = validar.notNullNotEmpty(endereco.getNumero(),
				MSG_CAMPO_OBRIGATORIO, LABEL_NUMERO);
		
		boolean bairroOK = validar.notNullNotEmpty(endereco.getBairro(),
				MSG_CAMPO_OBRIGATORIO, LABEL_BAIRRO);
		
		boolean cidadeOK = validar.notNullNotEmpty(endereco.getCidade().getEstado().getNome(),
				MSG_CAMPO_OBRIGATORIO, LABEL_ESTADO);
		
		boolean estadoOK = validar.notNullNotEmpty(endereco.getCidade().getEstado().getNome(),
				MSG_CAMPO_OBRIGATORIO, LABEL_ESTADO);

		boolean cepOK = validar.notNullNotEmpty(endereco.getCep(),
				MSG_CAMPO_OBRIGATORIO, LABEL_CEP);

		return nomeOK && telefoneOK && freteOK && formaPagamentoOK && 
				logradouroOK && numeroOK && bairroOK && cidadeOK && estadoOK && cepOK;
	}

	// Não sei onde e nem como que funciona ainda
	public void limparCamposDatas() {
		dataInicial = new Date();
		dataFinal = new Date();
	}
	
	public void filtrarPorData() {
		vendaController.listarVendasPorData(dataInicial, dataFinal);
		vendasPorData = 1;
	}
	
	public void listarProdutos() {
		try {
			vendasPorData = 0;
			limparCamposDatas();
			colecaoProdutos = produtoController.listarProdutos();
		} catch (RuntimeException e) {
			e.printStackTrace();
			MensagemErro(MSG_DADOS_NAO_ENCONTRADOS);
		}
	}
	
	
	// Dialog
	public void abrirDialog() {
		if (venda.getQuantidadeItensTotal() == null || venda.getQuantidadeItensTotal() == 0) {
			MensagemAlerta(MSG_VOCE_PRECISA_POR_ALGO_NO_CARRINHO);
		} else {
			Map<String, Object> options = new HashMap<String, Object>();
			options.put("modal", true);
			options.put("closable", true);
			options.put("draggable", true);
			options.put("resizable", true);
			options.put("contentHeight", 700);
			options.put("contentWidth", 760);
			options.put("showEffect", "explode");
			current().dialog().openDynamic("dialog-finalizar", options, null);
		}
	}
	
	public void fecharDialog() {
		// venda.setQuantidadeItensTotal(null);
		current().dialog().closeDynamic(null);
	}

	public void fecharDialogFinalizar() {
		carrinho();
		venda = new Venda();
		current().dialog().closeDynamic("dialog-finalizar");
	}

}
