package br.com.rafael.yaquisobra.beans;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.rafael.yaquisobra.controller.EstoqueController;
import br.com.rafael.yaquisobra.domain.model.Estoque;
import br.com.rafael.yaquisobra.domain.model.Ingrediente;
import br.com.rafael.yaquisobra.util.Constantes;
import br.com.rafael.yaquisobra.util.Mensagens;
import br.com.rafael.yaquisobra.util.Validacao;
import lombok.Data;

@SuppressWarnings("deprecation")
@ManagedBean
@SessionScoped
@Data
public class EstoqueMBean {

	private Estoque estoque;
	private Ingrediente ingrediente;
	private List<Estoque> colecaoEstoque;
	private EstoqueController estoqueController;
	private Validacao validar;

	public EstoqueMBean() {
		estoqueController = new EstoqueController();
		if (estoque == null) {
			setEstoque(new Estoque());
//			estoque.setIngrediente(new Ingrediente());
//			estoque.setDataEntrada(new Date());
		}
		// setColecaoEstoque(estoqueController.listarEstoques());
	}

	public void salvar() {
		try {
			if (validarCampos()) {
				//estoque.setDataEntrada(new Date());
//				estoqueController.incluir(estoque.getIngrediente());
				estoqueController.incluir(estoque);
				setEstoque(new Estoque());
//				estoque.setIngrediente(new Ingrediente());
				Mensagens.MensagemInfo(Constantes.MSG_SALVO_SUCESSO);
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
			setEstoque(new Estoque());
//			estoque.setIngrediente(new Ingrediente());
			Mensagens.MensagemInfo(Constantes.MSG_NAO_SALVO_SUCESSO);
		}
	}

	public void atualizarSessao() {
		setEstoque(new Estoque());
//		estoque.setIngrediente(new Ingrediente());
	}

	public Boolean validarCampos() {
		validar = new Validacao();

//		boolean fornecedorOK = validar.notNullNotEmpty(estoque.getFornecedor(), Constantes.MSG_CAMPO_OBRIGATORIO,
//				Constantes.LABEL_NOME);

//		boolean dataEntradaOK = validar.notNullNotEmpty(estoque.getDataEntrada(), Constantes.MSG_CAMPO_OBRIGATORIO,
//				Constantes.LABEL_DESCRICAO);

//		boolean descricaoOK = validar.notNullNotEmpty(estoque.getIngrediente().getDescricao(),
//				Constantes.MSG_CAMPO_OBRIGATORIO, Constantes.LABEL_DATA_ENTRADA);
//
//		boolean marcaOK = validar.notNullNotEmpty(estoque.getIngrediente().getMarca(), Constantes.MSG_CAMPO_OBRIGATORIO,
//				Constantes.LABEL_MARCA);
//
//		boolean nomeOK = validar.notNullNotEmpty(estoque.getIngrediente().getNome(), Constantes.MSG_CAMPO_OBRIGATORIO,
//				Constantes.LABEL_NOME);
//
//		boolean precoOK = validar.notNullNotEmpty(estoque.getIngrediente().getPreco(), Constantes.MSG_CAMPO_OBRIGATORIO,
//				Constantes.LABEL_PRECO);
//
//		boolean volumeOK = validar.notNullNotEmpty(estoque.getIngrediente().getVolume(),
//				Constantes.MSG_CAMPO_OBRIGATORIO, Constantes.LABEL_VOLUME);
		
//		return nomeOK && descricaoOK && precoOK && fornecedorOK && dataEntradaOK && marcaOK && volumeOK;
		return null;
	}

	public void consultarEstoque() {
		try {
			setEstoque(new Estoque());
			setColecaoEstoque(estoqueController.listarEstoques());
		} catch (RuntimeException e) {
			e.printStackTrace();
			Mensagens.MensagemErro(Constantes.MSG_DADOS_NAO_ENCONTRADOS);
		}
	}

}
