package br.com.diegogabriel.moneymanager.despesas;

import br.com.diegogabriel.moneymanager.exception.SaldoInsuficienteException;
import br.com.diegogabriel.moneymanager.modelo.Particao;
import java.io.Serializable;

/**
 * Classe abstrata responsavel por definir o que é uma despesa.
 * 
 * @author Diego Gabirel
 * @version 1.0
 */

public abstract class Despesa implements Serializable{
	
	protected Double valor;
	protected String nome;
	protected String descricao;
	protected boolean pago;
	
	/**
	 * Construtor padrão de Despesa
	 * 
	 * @param valor 	Double responsavel pelo valor referente a despesa.
	 * @param nome		String responsavel pelo nome da despesa. Esse nome não pode ser repetido.
	 * @param descricao	String responsavel pela descrição da despesa.
	 */
	public Despesa(Double valor, String nome, String descricao) {
		this.valor = valor;
		this.nome = nome;
		this.descricao = descricao;
		pago = false;
	}
	
	
	/**
	 * @return valor da despesa
	 */
	
	public Double getValor() {
		return valor;
	}

	/**
	 * @return o nome da despesa
	 */
	
	public String getNome() {
		return nome;
	}

	/**
	 * @return a descrição da despesa
	 */
	
	public String getDescricao() {
		return descricao;
	}
	
		
	/**
	 * Construtor de Despesa, usado para carregar uma despesa ja existente de um arquivo ou banco de dados
	 * 
	 * @param valor 	Double responsavel pelo valor referente a despesa.
	 * @param nome		String responsavel pelo nome da despesa. Esse nome não pode ser repetido.
	 * @param descricao	String responsavel pela descrição da despesa.
	 * @param pago		boolean responsavel pela informação se a despesa foi paga ou não.
	 */
	public Despesa(Double valor, String nome, String descricao, boolean pago) {
		this.valor = valor;
		this.nome = nome;
		this.descricao = descricao;
		this.pago = pago;
	}


	/**
	 * Ao ser chamada, se o saldo for suficiente a despesa é paga e retorna o valor do saldo restante.
	 * 
	 * @param 	saldo 						Valor de saldo disponivel que o MoneyManager possui
	 * @return								retorna o resultado do saldo subtraido pelo valor da despesa
	 * @throws	SaldoInsuficienteException 	Joga na pilha uma exceção, caso o saldo não seja suficiente para pagar.
	 */
	
	public Double pagar(Double saldo) throws SaldoInsuficienteException{
		
		Double resultado = saldo - valor;
		if(resultado > saldo) throw new SaldoInsuficienteException();
		
		pago = true;
		return resultado;
	}

	
	/**
	 * Retorna se a conta foi paga ou não.
	 * 
	 * @return Retorna uma booleana indicando se a conta foi paga.
	 */
	public boolean foiPago() {
		return pago;
	}
	
	
	@Override
	public String toString() {
		
		String foiPago;
		if(pago) foiPago = "Sim";
		else foiPago = "Não";
		
		return "Nome: " + nome + " | Valor: " + valor + " | Descrição: " + descricao + ". | Foi pago? " + foiPago;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		Despesa nome = (Despesa) obj;
		return this.nome.equals(nome.getNome());
	}
	
	
	@Override
	public int hashCode() {
		return nome.hashCode();
	}
}
