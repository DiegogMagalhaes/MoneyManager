package br.com.diegogabriel.moneymanager.modelo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.diegogabriel.moneymanager.exception.SaldoInsuficienteException;

/**
 * Classe responsavel por definir uma despesa.
 * 
 * @author Diego Gabirel
 * @version 2.0
 */

@Entity
@Table(name = "DESPESA")
public class Despesa implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	protected Usuario usuario;
	
	protected Double valor;
	protected String nome;
	protected String descricao;
	protected boolean pago;

	
	//----------Construtores---------- 
	
	/**
	 * Construtor padrão, sem parametros.
	 */
	public Despesa() {
		
	}
	
	
	/**
	 * @param valor		Double responsavel pelo valor referente a despesa.
	 * @param nome		String responsavel pelo nome da despesa.
	 * @param descricao	String responsavel pela descrição da despesa.
	 */
	public Despesa(Double valor, String nome, String descricao) {
		this.valor = valor;
		this.nome = nome;
		this.descricao = descricao;
		pago = false;
	}
	
	
	/**
	 * Construtor de Despesa usado para carregar uma despesa já existente de um banco de dados.
	 * 
	 * @param valor 	Double responsavel pelo valor referente a despesa.
	 * @param nome		String responsavel pelo nome da despesa.
	 * @param descricao	String responsavel pela descrição da despesa.
	 * @param pago		boolean responsavel pela informação se a despesa foi paga ou não.
	 * @param usuario 	Usuario responsavel pelo relacionamento entre usuario e despesa
	 */
	public Despesa(Double valor, String nome, String descricao, boolean pago, Usuario usuario) {
		this.valor = valor;
		this.nome = nome;
		this.descricao = descricao;
		this.pago = pago;
		this.usuario = usuario;
	}
	
	
	//----------Getters and Setters----------
	
	/**
	 * @return valor de id
	 */
	public Long getId() {
		return id;
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
	 * @return o valor booleano referente ao estado pago da despesa
	 */
	public boolean getPago() {
		return pago;
	}
	
	
	
	
	/**
	 * @param id Id que indentifica a despesa no banco de dados
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * @param valor	Valor que sera atribuido da despesa
	 */
	public void setValor(Double valor) {
		this.valor = valor;
	}
	
	/**
	 * @param nome Nome que sera atribuido da despesa
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	/**
	 * @param descicao Descição que sera atribuida da despesa
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	/**
	 * @param pago Estado booleando referente a se a despesa foi paga ou não
	 */
	public void setPago(boolean pago) {
		this.pago = pago;
	}


	//----------Metodos----------
	
	
	/**
	 * Ao ser chamada, se o saldo for suficiente a despesa é paga e retorna o valor do saldo restante.
	 * 
	 * @param  saldo Valor de saldo disponivel que o MoneyManager possui
	 * @return retorna o resultado do saldo subtraido pelo valor da despesa
	 * @throws SaldoInsuficienteException Joga na pilha uma exceção, caso o saldo não seja suficiente para pagar.
	 */
	public Double pagar(Double saldo) throws SaldoInsuficienteException{
		
		Double resultado = saldo - valor;
		if(resultado > saldo) throw new SaldoInsuficienteException();
		
		pago = true;
		return resultado;
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
