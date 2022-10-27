package br.com.rafael.yaquisobra.beans;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import static br.com.rafael.yaquisobra.util.Mensagens.MensagemErro;
import static br.com.rafael.yaquisobra.util.Mensagens.MensagemInfo;
import static br.com.rafael.yaquisobra.beans.LoginMBean.usuarioLogado;
import static br.com.rafael.yaquisobra.util.Constantes.*;

import br.com.rafael.yaquisobra.controller.ProdutoController;
import br.com.rafael.yaquisobra.domain.model.Produto;
import br.com.rafael.yaquisobra.domain.model.Usuario;
import br.com.rafael.yaquisobra.util.Global;
import br.com.rafael.yaquisobra.util.Validacao;
import lombok.Data;

@SuppressWarnings("deprecation")
@ManagedBean
@SessionScoped
@Data
public class ProdutoMBean {

	private Produto produto;
	
	private ProdutoController produtoController;
	private List<Produto> colecaoProdutos;
	private Usuario usuarioLogado;
	private Validacao validar;

	public ProdutoMBean() {
		produtoController = new ProdutoController();
		colecaoProdutos = produtoController.listarProdutos();
		usuarioLogado = usuarioLogado();
		if (produto == null) {
			produto = new Produto();
		}
	}

	public String salvar() {
		try {
			if (validarCampos()) {
				produtoController = new ProdutoController();
				produtoController.incluir(produto);
				produto = new Produto();
				MensagemInfo(MSG_SALVO_SUCESSO);
				return telaConsultarProduto;
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
			produto = new Produto();
			MensagemInfo(MSG_NAO_SALVO_SUCESSO);
		}
		return null;
	}

	public void consultarProdutos() {
		try {
			produto = new Produto();
			colecaoProdutos = produtoController.listarProdutos();
		} catch (RuntimeException e) {
			e.printStackTrace();
			MensagemErro(MSG_DADOS_NAO_ENCONTRADOS);
		}
	}

	public void excluir(Produto produto) {
		try {
			produtoController.excluir(produto);
			MensagemInfo(MSG_EXCLUIDO_SUCESSO);
		} catch (RuntimeException e) {
			e.printStackTrace();
			MensagemErro(MSG_NAO_EXCLUIDO_SUCESSO);
		}
	}

	public String telaAlterar() {
		return telaAlterarProduto;
	}

	public String alterar() {
		try {
			if (validarCampos()) {
				produtoController.alterar(produto);
				produto = new Produto();
				return telaConsultarProduto;
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
			MensagemErro(MSG_NAO_ALTERADO_SUCESSO);
		}
		return null;
	}

	public Boolean validarCampos() {
		validar = new Validacao();

		boolean nomeOK = validar.notNullNotEmpty(produto.getNome(), 
				MSG_CAMPO_OBRIGATORIO, LABEL_NOME);

		boolean descricaoOK = validar.notNullNotEmpty(produto.getDescricao(), 
				MSG_CAMPO_OBRIGATORIO, LABEL_DESCRICAO);

		boolean precoOK = validar.notNullNotEmpty(produto.getPreco(), 
				MSG_CAMPO_OBRIGATORIO, LABEL_PRECO);

		boolean quantidadeOK = validar.notNullNotEmpty(produto.getQuantidade(), 
				MSG_CAMPO_OBRIGATORIO, LABEL_QUANTIDADE);

		return nomeOK && descricaoOK && precoOK && quantidadeOK;
	}

	public void atualizarSessao() {
		produto = new Produto();
		Global.limparItens = 1;
	}

}
