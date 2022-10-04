package br.com.diegogabriel.moneymanager.despesas;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;

import br.com.diegogabriel.moneymanager.exception.SaldoInsuficienteException;
import br.com.diegogabriel.moneymanager.modelo.Usuario;

/**
 * Essa classe se refere a despesas que renovam a cada mês, como por exemplo conta de internet. 
 * DespesaMensal extende Despesa. 
 * Esse tipo de despesa, possui data de validade.
 * 
 * @author Diego Gabriel
 * @version 1.0
 */

@Entity
public class DespesaMensal extends Despesa  {
	
	@Column(name = "datavalidade")
	private LocalDate dataValidade;
	
	/**
	 * Construtor padão de DespesaMensal
	 * 
	 * @param valor 	Double responsavel pelo valor referente a despesa.
	 * @param nome		String responsavel pelo nome da despesa. 
	 * @param descricao 	String resposavel pela descrição da despesa.
	 * @param dataValidade	LocalDate responvavel por salvar a data limite em que a despesa pode ser paga, sem atraso.
	 */
	public DespesaMensal(Double valor, String nome, String descricao, LocalDate dataValidade) {
		super(valor,nome,descricao);
		this.dataValidade = dataValidade;
	}
	
	
	/**
	 * Construtor de DespesaMensal, usado para carregar uma despesa ja existente de um arquivo ou banco de dados
	 * 
	 * @param valor 	Double responsavel pelo valor referente a despesa.
	 * @param nome		String responsavel pelo nome da despesa. Esse nome não pode ser repetido.
	 * @param descricao	String responsavel pela descrição da despesa.
	 * @param pago		boolean responsavel pela informação se a despesa foi paga ou não.
	 * @param dataValidade LocalDate refrente a validade da despesa
	 */
	
	public DespesaMensal(Double valor, String nome, String descricao, boolean pago, LocalDate dataValidade) {
		super(valor,nome,descricao,pago);
		this.dataValidade = dataValidade; 
	}
	
	/**
	 * Construtor de DespesaMensal, usado para carregar uma despesa ja existente de um arquivo ou banco de dados
	 * 
	 * @param valor 	Double responsavel pelo valor referente a despesa.
	 * @param nome		String responsavel pelo nome da despesa. Esse nome não pode ser repetido.
	 * @param descricao	String responsavel pela descrição da despesa.
	 * @param pago		boolean responsavel pela informação se a despesa foi paga ou não.
	 * @param usuario String responsavel pelo relacionamento entre usuario e despesa
	 * @param dataValidade LocalDate refrente a validade da despesa.	 
	 */
	
	public DespesaMensal(Double valor, String nome, String descricao, boolean pago, Usuario usuario ,LocalDate dataValidade) {
		super(valor,nome,descricao,pago, usuario);
		this.dataValidade = dataValidade; 
	}
	
	public DespesaMensal() {
		
	}
	
	
	/** 
	 * @return data de validade da despesa
	 */
	
	public LocalDate getData() {
		return dataValidade;
	}
	
	/**
	 * @param dataValidade Data de validade que sera atribuida a despesa
	 */
	
	public void setData(LocalDate dataValidade) {
		this.dataValidade = dataValidade;
	}
	
	
	/**
	 * Verefica se a despesa esta atrasada.
	 * 
	 * @return Um booleano indicando se a despesa esta ou não atrasada.
	 */
	
	private boolean verificarAtraso() {
		
		if(dataValidade.isBefore(LocalDate.now())) return true;
		else return false;
		
	}
	
	
	@Override
	public Double pagar(Double saldo) throws SaldoInsuficienteException{
		
		if(verificarAtraso()) System.out.println("Essa conta esta atrassada");
		return super.pagar(saldo);
	}
	
	@Override
	public String toString() {
		String resultadoAtraso;
		
		if(verificarAtraso()) resultadoAtraso = "A conta esta atrasada";
		else resultadoAtraso = "A conta esta em dia";
		
		return super.toString() + "| validade: " + dataValidade.toString() + ", " + resultadoAtraso;
	}
	
}
