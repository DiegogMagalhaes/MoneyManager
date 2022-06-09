package br.com.diegogabriel.moneymanager.modelo;

import java.io.Serializable;

/**
 * Representa o usuario do programa.
 * 
 * @author Diego Gabriel
 * @version 1.0
 */

public class Usuario implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String nome;
	private Double salario;
	
	public MoneyManager moneyManager;
	
	/**
	 * Retorna o salario do usuario.
	 * 
	 * @return Salario do usuario
	 */
	
	public Double getSalario() {
		return salario;
	}
	
	
	/**
	 * Retorna o nome do usuario.
	 * 
	 * @return	nome do usuario
	 */
	public String getNome() {
		return nome;
	}
	
	
	/**
	 * Recebe um valor, no qual sera o novo salario do usuario.
	 * 
	 * @param valor	Double referente ao novo salario do usuario.
	 */
	
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
