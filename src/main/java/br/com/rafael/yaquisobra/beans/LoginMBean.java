package br.com.rafael.yaquisobra.beans;

import static br.com.rafael.yaquisobra.util.Constantes.*;
import static br.com.rafael.yaquisobra.util.Mensagens.MensagemErro;
import static org.apache.commons.codec.digest.DigestUtils.md5Hex;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.rafael.yaquisobra.controller.UsuarioController;
import br.com.rafael.yaquisobra.domain.model.Usuario;
import br.com.rafael.yaquisobra.util.Validacao;

@SuppressWarnings("deprecation")
@ManagedBean
@SessionScoped
public class LoginMBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	static Usuario usuarioLogado;
	private Validacao validar;
	UsuarioController usuarioController;

	public LoginMBean() {
		usuarioController = new UsuarioController();
		if (usuarioLogado == null) {
			usuarioLogado = new Usuario();
		}
	}
	
	public String entrar() throws Exception {
		try {
			if (validarCampos()) {
				usuarioController = new UsuarioController();
				usuarioLogado = usuarioController.autenticarUsuario(
						usuarioLogado.getCpf(),
						md5Hex(usuarioLogado.getSenha()));

				if (usuarioLogado.getCpf() != null && 
						usuarioLogado.getSenha() != null) {
					return telaPrincipal;
				} else {
					MensagemErro(MSG_CPF_E_OU_SENHA_INCORRETOS);
				}
			}
		} catch (Exception e) {
			MensagemErro(MSG_ERRO_LOGIN_SISTEMA);
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		return null;
	}

	public boolean validarCampos() {
		validar = new Validacao();
		boolean cpfOK = validar.notNullNotEmpty(usuarioLogado.getCpf(), 
				MSG_CAMPO_OBRIGATORIO, LABEL_CPF);

		boolean senhaOK = validar.camposSenhaNotNullNotEmpty(
				usuarioLogado.getSenha(),
				MSG_CAMPO_OBRIGATORIO, LABEL_SENHA);

		return cpfOK && senhaOK;
	}

	public String sair() {
		usuarioLogado = new Usuario();
		return telaLogin;
	}
	
	public static Usuario usuarioLogado() {
		return usuarioLogado;
	}

	// Getters and Setters for Views
	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		LoginMBean.usuarioLogado = usuarioLogado;
	}
}
