package br.com.diegogabriel.moneymanager.despesas;

import br.com.diegogabriel.moneymanager.exception.SaldoInsuficienteException;

public abstract class Despesa {
	
	protected Double valor;
	protected String nome;
	protected String descricao;
	protected boolean pago;
	
	public Double pagar(Double saldo) {
		
		Double resultado = saldo - valor;
		if(resultado < valor) throw new SaldoInsuficienteException(String.valueOf(Math.abs(resultado)));
		
		pago = true;
		return resultado;
	}
	
	public Despesa(Double valor, String nome, String descricao) {
		this.valor = valor;
		this.nome = nome;
		this.descricao = descricao;
		pago = false;
	}
	
	@Override
	public String toString() {
		return "Nome: " + nome + "; Valor: " + valor + "; Descrição :" + descricao + ". Foi pago? " + pago;
	}
	
}
