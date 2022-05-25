package br.com.diegogabriel.moneymanager.despesas;

import br.com.diegogabriel.moneymanager.exception.SaldoInsuficienteException;
import br.com.diegogabriel.moneymanager.modelo.ParticaoGastos;

public class Gasto extends Despesa{

	private ParticaoGastos particao;
	
	@Override
	public Double pagar(Double saldo) {
		return super.pagar(saldo);
	}

	private boolean verificarParticao(ParticaoGastos particao) {
		 return this.particao.equals(particao);
	}
	
	public Gasto(Double valor, String nome, String descricao) {
		super(valor,nome,descricao);
	}
	
	public Gasto(Double valor, String nome, String descricao, ParticaoGastos particao) {
		super(valor,nome,descricao);
		this.particao = particao;
	}
	
	@Override
	public String toString() {
		return super.toString() + "Particao: " + particao;
	}
}
