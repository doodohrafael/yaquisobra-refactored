package br.com.rafael.yaquisobra.controller;

import java.io.Serializable;
import java.util.List;

import br.com.rafael.yaquisobra.dao.UsuarioDao;
import br.com.rafael.yaquisobra.domain.model.Usuario;

public class UsuarioController implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	UsuarioDao dao = new UsuarioDao();

	public Usuario autenticarUsuario(String cpf, String senha) {
		return dao.autenticarUsuario(cpf, senha);
	}
	
	public List<Usuario> listarUsuarios() {
		return dao.listarUsuarios();
	}
	
	public void incluir(Usuario usuario) {
		dao.incluir(usuario);
	}
	
	public void alterar(Usuario usuario) {
		dao.alterar(usuario);
	}
	
	public boolean procurarPorCpf(String cpf) {
		return dao.procurarPorCpf(cpf);
	}
	
	public void excluir(Usuario usuario) {
		dao.excluir(usuario);
	}

}
