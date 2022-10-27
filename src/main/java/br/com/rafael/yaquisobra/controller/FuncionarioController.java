package br.com.rafael.yaquisobra.controller;

import java.util.List;

import br.com.rafael.yaquisobra.dao.FuncionarioDao;
import br.com.rafael.yaquisobra.domain.model.Funcionario;

public class FuncionarioController {
	
	FuncionarioDao dao = new FuncionarioDao();

	public void incluir(Funcionario funcionario) {
		dao.incluir(funcionario);
	}
	
	public List<Funcionario> listarFuncionarios() {
		return dao.listarFuncionarios();
	}
	
	public void excluir(Funcionario funcionario) {
		dao.excluir(funcionario);
	}
	
	public void alterar(Funcionario funcionario) {
		dao.alterar(funcionario);
	}
	
	public boolean procurarFuncionario(String cpf) {
		return dao.procurarPorCpf(cpf);
	}
	
	public Funcionario funcionarioPorCodigo(Long codigo) {
		return dao.funcionarioPorCodigo(codigo);
	}
	
	public Funcionario autenticarFuncionario(String cpf, String senha) {
		return dao.autenticarFuncionario(cpf, senha);
	}
}
