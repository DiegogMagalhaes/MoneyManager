package br.com.diegogabriel.moneymanager.despesas;

import java.time.LocalDate;

public class DespesaMensal extends Despesa  {
	
	private LocalDate validade;
	
	@Override
	public Double pagar(Double saldo) {
		return super.pagar(saldo);
	}
	
	private String verificarValidade() {
		
		if(validade.isBefore(LocalDate.now())) return "Conta esta atrasada";
		else return "Conta esta em dia";
		
	}
	
	public DespesaMensal(Double valor, String nome, String descricao, LocalDate validade) {
		super(valor,nome,descricao);
		this.validade = validade;
	}
	
	@Override
	public String toString() {
		return super.toString() + "validade: " + validade.toString() + ". " + verificarValidade();
	}
	
}
