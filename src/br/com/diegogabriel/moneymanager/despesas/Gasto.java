package br.com.diegogabriel.moneymanager.despesas;

import java.time.LocalDate;

import br.com.diegogabriel.moneymanager.exception.SaldoInsuficienteException;
import br.com.diegogabriel.moneymanager.modelo.Particao;

/**
 * Essa classe se refere a gastos ocassionais do dia a dia. 
 * Um gasto pode possuir uma partição no qual categoriza o gasto.
 * Gastos extende Despesa. 
 * 
 * @author Sintese Suporte05
 * @version 1.0
 */

public class Gasto extends Despesa{

	private Particao particao;
	

	public Gasto(Double valor, String nome, String descricao) {
		super(valor,nome,descricao);
	}
	
	
	/** 
	 * @return Retorna a particao de gastos
	 */
	
	public Particao getParticao() {
		return particao;
	}
	
	
	/**
	 * Construtor padrão de Gasto
	 * 
	 * @param valor 	Double responsavel pelo valor referente a despesa.
	 * @param nome		String responsavel pelo nome da despesa. Esse nome não pode ser repetido.
	 * @param descricao	String responsavel pela descrição da despesa.
	 * @param particao	Particao na qual o objeto de Gastos fara parte.
	 */
	public Gasto(Double valor, String nome, String descricao, Particao particao) {
		super(valor,nome,descricao);
		this.particao = particao;
	}
	
	
	/**
	 * Construtor de Gasto, usado para carregar uma despesa ja existente de um arquivo ou banco de dados
	 * 
	 * @param valor 	Double responsavel pelo valor referente a despesa.
	 * @param nome		String responsavel pelo nome da despesa. Esse nome não pode ser repetido.
	 * @param descricao	String responsavel pela descrição da despesa.
	 * @param pago		boolean responsavel pela informação se a despesa foi paga ou não.
	 * @param particao  Particao refrente a partição da despesa
	 */
	
	public Gasto(Double valor, String nome, String descricao, boolean pago, Particao particao) {
		super(valor,nome,descricao,pago);
		this.particao = particao; 
	}
	
	
	/**
	 * Recebe uma Particao. Essa particao sera comparada atravez do metodo da classe Object equals. 
	 * 
	 * @param particao Particao a ser comparada.
	 * @return Um booleano indicando se a Particao recebida é igual a Particao do objeto de Gastos.
	 */
	
	private boolean verificarParticao(Particao particao) {
		 return this.particao.equals(particao);
	}
	
	@Override
	public String toString() {
		
		if(particao == null) return super.toString();
		
		return super.toString() + " | Particao: " + particao;
	}
}
