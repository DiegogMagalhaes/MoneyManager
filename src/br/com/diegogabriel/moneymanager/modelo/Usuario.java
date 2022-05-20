package br.com.diegogabriel.moneymanager.modelo;

import br.com.diegogabriel.moneymanager.exception.SenhaIncorretaException;

public class Usuario {
	
	private String nome;
	private Integer senha;
	private Double salario;
	private Double saldo;
	
	public Double getSalario() {
		return salario;
	}
	
	public Double getSaldo() {
		return saldo;
	}
	
	public void alterarSalario(Double valor, Integer senha) {
		verificarSenha(senha);
		this.salario = valor;
	}
	
	public void addSaldo(Double valor, Integer senha) {
		verificarSenha(senha);
		this.saldo += valor;
	}
	
	private void verificarSenha(Integer senha) {
		if(this.senha.equals(senha)) throw new SenhaIncorretaException();
	}
	
	public Usuario(String nome, Integer senha, Double salario) {
		this.nome = nome;
		this.senha = senha;
		this.salario = salario;
		saldo = salario;
		
		System.out.println(this);
	}
	
	@Override
	public String toString(){
		return "Nome: " + nome + "; Saldo: " + saldo;
	}
}
