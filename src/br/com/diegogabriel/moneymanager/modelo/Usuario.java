package br.com.diegogabriel.moneymanager.modelo;

public class Usuario {
	
	private String nome;
	private Double salario;
	
	public MoneyManager moneyManager;
	
	public Double getSalario() {
		return salario;
	}
	
	public void alterarSalario(Double valor) {
		this.salario = valor;
	}
	
	
	public Usuario(String nome, Double salario) {
		this.nome = nome;
		this.salario = salario;
	}
	
	@Override
	public String toString(){
		return "Nome: " + nome;
	}
}
