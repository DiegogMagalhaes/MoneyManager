package br.com.diegogabriel.moneymanager.modelo;

import br.com.diegogabriel.moneymanager.exception.LimiteParticaoException;

public class ParticaoGastos {

	private String ID;
	private Double gastoMes;
	private Double limite;
	
	private void addGastosMes(Double gastos) {
		gastoMes = gastos;
	}
	
	public void verificarLimite(Double valor) {
		Double resultado = valor + gastoMes;
		if(resultado > limite) throw new LimiteParticaoException(String.valueOf(limite - gastoMes));
		addGastosMes(resultado);
	}
	
	public String getID() {
		return ID;
	}
	
	@Override
	public String toString(){
		return "ID :" + ID + "; gastoMes: " + gastoMes + "limite :" + limite;
	}
	
	@Override
	public boolean equals(Object obj) {
		ParticaoGastos outraParticao = (ParticaoGastos) obj;
		return this.ID.equals(outraParticao.ID);
	}
	
	@Override
	public int hashCode() {
		return ID.hashCode();
	}
	
	public ParticaoGastos(String ID, Double limite) {
		this.ID = ID;
		this.limite = limite;
		gastoMes = 0d;
	}
	
}
