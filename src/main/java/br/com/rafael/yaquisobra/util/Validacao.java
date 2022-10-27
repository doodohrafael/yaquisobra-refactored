package br.com.rafael.yaquisobra.util;

import static br.com.rafael.yaquisobra.util.Mensagens.MensagemAlerta;
import static br.com.rafael.yaquisobra.util.Constantes.*;

import java.math.BigDecimal;

public class Validacao {
	
	public boolean notNullNotEmpty(String valor, String msg, String campo) {

		if (valor == null || valor.trim().equals("")) {
			msg = msg.replaceAll("%", " " + campo + " ");
			MensagemAlerta(msg);
			return false;
		} 

		return true;
	}
	
	public boolean notNullNotEmpty(BigDecimal valor, String msg, String campo) {

		if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
			msg = msg.replaceAll("%", " " + campo + " ");
			MensagemAlerta(msg);
			return false;
		}

		return true;
	}
	
	public boolean notNullNotEmpty(Integer valor, String msg, String campo) {

		if (valor == null || valor <= 0) {
			msg = msg.replaceAll("%", " " + campo + " ");
			MensagemAlerta(msg);
			return false;
		} 

		return true;
	}
	
	public boolean notNullNotEmpty(Object valor, String msg, String campo) {

		if (valor == null) {
			msg = msg.replaceAll("%", " " + campo + " ");
			MensagemAlerta(msg);
			return false;
		} 

		return true;
	}
	
	public boolean camposSenhaNotNullNotEmpty(String valor, String msg, String campo) {

		if (valor == null || valor.trim().equals("")) {
			msg = msg.replaceAll("%", " " + campo + " ");
			MensagemAlerta(msg);
			return false;
		} 

		if(valor.length() < 6) {
			MensagemAlerta(MSG_SENHA_PRECISA_DE_6_DÍGITOS);
			return false;
		}
		
		return true;
	}
	
	public boolean camposSenhaIguais(String senha, String confirmaSenha) {

		if (!senha.equals(confirmaSenha)) {
			MensagemAlerta(MSG_CAMPOS_SENHA_E_CONFIRMAR_SENHA_PRECISAM_SER_IGUAIS);
			return false;
		} 

		return true;
	}
	
	public boolean notNullNotEmptyFrete(BigDecimal valor, String msg, String campo) {

		if (valor == null || valor.compareTo(BigDecimal.ZERO) < 0) {
			msg = msg.replaceAll("%", " " + campo + " ");
			MensagemAlerta(msg);
			return false;
		}

		return true;
	}
	
}
