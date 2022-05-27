package br.com.diegogabriel.moneymanager.modelo;

import br.com.diegogabriel.moneymanager.exception.LimiteParticaoException;

/**
 * Particao é uma classe que representa uma partição de gastos. 
 * Ou seja é uma partição para uma categoria especifica de gastos, nos quais tem um valor limite que pode ser gasto a cada mês.
 * 
 * Por exemplo: se você for um colecionador de cartas, porem quer estipular um valor limite por mês para acabar não extrapolando nos gastos com sua coleção.
 * Você pode criar uma partição para gastos que entram nessa categoria com um limite de 100 reais.
 * Nesse mês sempre que você criar um novo gasto que tenha como partição essa coleção de cartas, você sera impedido de adicionar esse gasto no sistema caso ultrapasse o limite.
 * 
 * @author Diego Gabriel
 * @version 1.0
 */

public class Particao {

	private String ID;
	private Double gastoMes;
	private Double limite;
	
	public Particao(String ID, Double limite) {
		this.ID = ID;
		this.limite = limite;
		gastoMes = 0d;
	}
	
	
	/**
	 * Recebe um valor para ser adicionado aos gastos mensais feitos nessa partição.
	 * 
	 * @param gastos Double representante de um valor gasto, no qual sera adicionado a gastoMes.
	 */
	
	private void addGastosMes(Double gastos) {
		gastoMes = gastos;
	}
	
	
	/**
	 * Recebe um valor, no qual é verificado se sua soma com os gastos anteriores desse mes extrapola o limite estabelecido. 
	 * Se não extrapolar é adicionado o valor aos gastos do mês.
	 * 
	 * @param valor	Double referente ao valor da conta que esta sendo adicionada a particao.
	 */
	
	public void verificarLimite(Double valor) {
		Double resultado = valor + gastoMes;
		if(resultado > limite) throw new LimiteParticaoException(String.valueOf(limite - gastoMes));
		addGastosMes(resultado);
	}
	
	
	@Override
	public String toString(){
		return "ID: " + ID + " | gastoMes: " + gastoMes + " | limite: " + limite;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		Particao outraParticao = (Particao) obj;
		return this.ID.equals(outraParticao.ID);
	}
	
	
	@Override
	public int hashCode() {
		return ID.hashCode();
	}
	
}
