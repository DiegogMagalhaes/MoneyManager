package br.com.diegogabriel.moneymanager.despesas;

import java.time.LocalDate;

public class DespesaMensal extends Despesa  {
	
	private LocalDate validade;
	
	@Override
	public Double pagar(Double saldo) {
		return super.pagar(saldo);
	}
	
	private void verificarValidade() {
		
	}
	
	public DespesaMensal(Double valor, String nome, String descricao, LocalDate validade) {
		super(valor,nome,descricao);
		this.validade = validade;
	}
	
}
