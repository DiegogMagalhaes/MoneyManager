package br.com.diegogabriel.moneymanager.modelo;

public class UsuarioDB {
	
	private String nome;
	private Double salario;
	
	public MoneyManagerDB moneyManager;
	
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
	
	
	public UsuarioDB(String nome, Double salario) {
		this.nome = nome;
		this.salario = salario;
	}
	
	
	@Override
	public String toString(){
		return "Nome: " + nome;
	}

}
