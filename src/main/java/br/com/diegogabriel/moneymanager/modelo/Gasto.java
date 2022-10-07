package br.com.diegogabriel.moneymanager.modelo;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * Essa classe se refere a gastos ocassionais do dia a dia. 
 * Um gasto pode possuir uma partição no qual categoriza o gasto.
 * Gastos extende Despesa. 
 * 
 * @author Diego Gabriel
 * @version 2.0
 */

@Entity
public class Gasto extends Despesa{

	@ManyToOne
	private Particao particao;

	
	//----------Construtores----------
	
	/**
	 * Construtor padrão, sem parametros.
	 */
	public Gasto() {
		
	}
	
	
	/**
	 * @param valor 	Double responsavel pelo valor referente a despesa.
	 * @param nome		String responsavel pelo nome da despesa. 
	 * @param descricao	String responsavel pela descrição da despesa.
	 * @param particao	Particao na qual o objeto de Gastos fara parte.
	 */
	public Gasto(Double valor, String nome, String descricao, Particao particao) {
		super(valor,nome,descricao);
		this.particao = particao;
	}
	
	
	/**
	 * Construtor de Gasto, usado para carregar uma despesa ja existente de um banco de dados
	 * 
	 * @param valor 	Double responsavel pelo valor referente a despesa.
	 * @param nome		String responsavel pelo nome da despesa.
	 * @param descricao	String responsavel pela descrição da despesa.
	 * @param pago		boolean responsavel pela informação se a despesa foi paga ou não.
	 * @param usuario 	Usuario responsavel pelo relacionamento entre usuario e despesa
	 * @param particao  Particao refrente a partição da despesa
	 */
	public Gasto(Double valor, String nome, String descricao, boolean pago,Usuario usuario ,Particao particao) {
		super(valor,nome,descricao,pago, usuario);
		this.particao = particao; 
	}
	
	
	//----------Getters and Setters----------
	
	/** 
	 * @return Retorna a particao de gastos
	 */
	public Particao getParticao() {
		return particao;
	}
	
	
	
	
	/**
	 * @param particao Partição que sera atribuida a despesa
	 */
	public void setParticao(Particao particao) {
		this.particao = particao;
	}
	
	
	//----------Metodos----------
	
	@Override
	public String toString() {
		
		if(particao == null) return super.toString();
		
		return super.toString() + " | Particao: " + particao;
	}
}
