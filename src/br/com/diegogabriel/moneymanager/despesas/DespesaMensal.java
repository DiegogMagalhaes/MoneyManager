package br.com.diegogabriel.moneymanager.despesas;

import java.time.LocalDate;

import br.com.diegogabriel.moneymanager.exception.SaldoInsuficienteException;

/**
 * Essa classe se refere a despesas que renovam a cada mês, como por exemplo conta de internet. 
 * DespesaMensal extende Despesa. 
 * Esse tipo de despesa, possui data de validade.
 * 
 * @author Diego Gabriel
 * @version 1.0
 */

public class DespesaMensal extends Despesa  {
	
	private LocalDate dataValidade;
	
	/**
	 * Construtor padão de DespesaMensal
	 * 
	 * @param valor 		Double responsavel pelo valor referente a despesa.
	 * @param nome			String responsavel pelo nome da despesa. 
	 * @param descricao 	String resposavel pela descrição da despesa.
	 * @param dataValidade	LocalDate responvavel por salvar a data limite em que a despesa pode ser paga, sem atraso.
	 */
	public DespesaMensal(Double valor, String nome, String descricao, LocalDate dataValidade) {
		super(valor,nome,descricao);
		this.dataValidade = dataValidade;
	}
	
	
	/**
	 * Verefica se a despesa esta atrasada.
	 * 
	 * @return	Um booleano indicando se a despesa esta ou não atrasada.
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
