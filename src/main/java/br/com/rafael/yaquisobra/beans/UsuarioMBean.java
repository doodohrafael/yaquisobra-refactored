package br.com.rafael.yaquisobra.beans;

import static br.com.rafael.yaquisobra.beans.LoginMBean.usuarioLogado;
import static br.com.rafael.yaquisobra.util.Constantes.MSG_CAMPO_CPF_DUPLICADO;
import static br.com.rafael.yaquisobra.util.Constantes.MSG_DADOS_NAO_ENCONTRADOS;
import static br.com.rafael.yaquisobra.util.Constantes.MSG_EXCLUIDO_SUCESSO;
import static br.com.rafael.yaquisobra.util.Constantes.MSG_NAO_ALTERADO_SUCESSO;
import static br.com.rafael.yaquisobra.util.Constantes.MSG_NAO_EXCLUIDO_SUCESSO;
import static br.com.rafael.yaquisobra.util.Constantes.MSG_NAO_SALVO_SUCESSO;
import static br.com.rafael.yaquisobra.util.Constantes.MSG_SALVO_SUCESSO;
import static br.com.rafael.yaquisobra.util.Constantes.MSG_USUARIO_LOGADO;
import static br.com.rafael.yaquisobra.util.Constantes.telaAlterarUsuario;
import static br.com.rafael.yaquisobra.util.Constantes.telaConsultarUsuario;
import static br.com.rafael.yaquisobra.util.Mensagens.MensagemAlerta;
import static br.com.rafael.yaquisobra.util.Mensagens.MensagemErro;
import static br.com.rafael.yaquisobra.util.Mensagens.MensagemInfo;
import static org.apache.commons.codec.digest.DigestUtils.md5Hex;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.rafael.yaquisobra.controller.UsuarioController;
import br.com.rafael.yaquisobra.domain.exception.NegocioException;
import br.com.rafael.yaquisobra.domain.model.Cidade;
import br.com.rafael.yaquisobra.domain.model.Contato;
import br.com.rafael.yaquisobra.domain.model.Endereco;
import br.com.rafael.yaquisobra.domain.model.Estado;
import br.com.rafael.yaquisobra.domain.model.Usuario;
import br.com.rafael.yaquisobra.util.Global;
import br.com.rafael.yaquisobra.util.Validacao;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("deprecation")
@ManagedBean
@SessionScoped
@Data
public class UsuarioMBean {

	private Usuario usuario;
	private Usuario usuarioLogado;
	private Contato contato;
	private Endereco endereco;
	
	private UsuarioController usuarioController;
	private List<Usuario> colecaoUsuarios;
	private Validacao validar;
	private String confirmarSenha;

	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	private Boolean cpfAlterar;
	
	public UsuarioMBean() {
		if (usuario == null) {
			usuario = new Usuario();
		}
		usuarioController = new UsuarioController();
		colecaoUsuarios = usuarioController.listarUsuarios();
		usuarioLogado = usuarioLogado();
		limparCampos();
		
	}
	
	public String salvar() {
		try {
			cpfAlterar = false;
			if (validarCampos()) {
				if(contato != null) adicionarContatos();
				if(endereco != null) adicionarEnderecos();
				usuario.setSenha(md5Hex(usuario.getSenha()));
				usuarioController.incluir(usuario);
				limparCampos();
				MensagemInfo(MSG_SALVO_SUCESSO);
				return telaConsultarUsuario;
			}
		} catch (Exception e) {
			e.printStackTrace();
			usuario = new Usuario();
			MensagemErro(MSG_NAO_SALVO_SUCESSO);
			throw new NegocioException(e.getMessage());
		}
		return null;
	}

	public void consultarUsuarios() {
		try {
			usuario = new Usuario();
			this.colecaoUsuarios = usuarioController.listarUsuarios();
		} catch (Exception e) {
			e.printStackTrace();
			MensagemErro(MSG_DADOS_NAO_ENCONTRADOS);
			throw new NegocioException(e.getMessage());
		}
	}

	public void excluir(Usuario usuario) {
		try {
			if (usuarioLogado.getCpf().equals(usuario.getCpf())) {
				MensagemAlerta(MSG_USUARIO_LOGADO);
			} else {
				usuarioController.excluir(usuario);
				MensagemInfo(MSG_EXCLUIDO_SUCESSO);
			}
		} catch (Exception e) {
			e.printStackTrace();
			MensagemErro(MSG_NAO_EXCLUIDO_SUCESSO);
			throw new NegocioException(e.getMessage());
		}
	}

	public String telaAlterar() {
		return telaAlterarUsuario;
	}

	public String alterar() {
		try {
			cpfAlterar = true;
			if (validarCampos()) {
				usuario.setSenha(md5Hex(usuario.getSenha()));
				usuarioController.alterar(usuario);
				usuario = new Usuario();
				return telaConsultarUsuario;
			}
		} catch (Exception e) {
			e.printStackTrace();
			MensagemErro(MSG_NAO_ALTERADO_SUCESSO);
			throw new NegocioException(e.getMessage());
		}
		return null;
	}

	public Boolean validarCampos() {
		validar = new Validacao();

		boolean cpfDuplicado = true;

		if (!cpfAlterar) {
			cpfDuplicado = usuarioController.procurarPorCpf(usuario.getCpf());
			if (cpfDuplicado) {
				MensagemAlerta(MSG_CAMPO_CPF_DUPLICADO);
				cpfDuplicado = false;
			} else {
				cpfDuplicado = true;
			}
		}

		boolean senhasIguaisOK = validar.camposSenhaIguais(usuario.getSenha(), confirmarSenha);

		return cpfDuplicado && senhasIguaisOK;
	}
	
	public void limparCampos() {
		usuario = new Usuario();
		contato = new Contato();
		endereco = new Endereco();
		endereco.setCidade(new Cidade());
		endereco.getCidade().setEstado(new Estado());
	}
	
	public void adicionarContatos() {
		List<Contato> colecaoContatos = new ArrayList<>();
		colecaoContatos.add(contato);
		usuario.setContatos(colecaoContatos);
	}
	
	public void adicionarEnderecos() {
		List<Endereco> colecaoEnderecos = new ArrayList<>();
		colecaoEnderecos.add(endereco);
		usuario.setEnderecos(colecaoEnderecos);
	}

	// Ouvinte de eventos do sistema de componentes
	public void atualizarSessao() {
		usuario = new Usuario();
		Global.limparItens = 1;
	}
	
	
}
