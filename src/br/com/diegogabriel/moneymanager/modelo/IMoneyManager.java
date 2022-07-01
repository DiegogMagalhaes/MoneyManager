package br.com.diegogabriel.moneymanager.modelo;

import java.time.LocalDate;

import br.com.diegogabriel.moneymanager.despesas.Despesa;
import br.com.diegogabriel.moneymanager.exception.DespesaExistenteException;
import br.com.diegogabriel.moneymanager.exception.ParticaoExistenteException;
import br.com.diegogabriel.moneymanager.exception.SaldoInsuficienteException;

public interface IMoneyManager {
	
	/**
	 * ENUM responsavel pela separação de tipo.
	 * DESPESAMENSAL = 1.
	 * GASTOS = 2.
	 * 
	 * utiliza do metodo getValor() para pegar o valor da constrante id do ENUM.
	 */
	
	public enum tipodeDespesa{
		DESPESAMENSAL(1),
		GASTOS(2);
		
		final int id;
		
		tipodeDespesa(int id) {
			this.id = id;
		}
		
		int getValor() {
			return id;
		}
	}
	

	/**
	 * Recebe um valor para adicionar ao saldo.
	 * 
	 * @param valor	Double que sera adicionado ao saldo vigente.
	 * @throws IllegalArgumentException lança uma exceção na pilha quando receber um valor menor ou igual a 0.
	 */
	public void adicionarSaldo(Double valor) throws IllegalArgumentException;
	
	
	/**
	 * Adiciona uma nova despesa ao HashSet despesas. 
	 * Possui uma interface via console para auxiliar o usuario do programa a dar a entrada necessaria.
	 * 
	 * @throws DespesaExistenteException Joga na pilha uma exceção quando o usuario tenta adicionar uma despesa com o mesmo nome.
	 */
	
	public void adicionarDespesa() throws DespesaExistenteException; 
	
	
	/**
	 * Imprime todas as despesas disponiveis no HashSet despesas.
	 * 
	 */
	
	public void mostrarDespesas();
	
	
	/**
	 * Imprime todas as despesas pagas ou não pagas, de acordo com a booleana pagas.
	 * @param pagas	boolean no qual define se sera imprimido as despesas pagas ou não pagas
	 */
	
	public void mostrarDespesas(boolean pagas);
		
	
	/**
	 * Recebe como parametro o nome de uma despesa, na qual sera paga. 
	 * 
	 * @param nome	String representante do nome da despesa a ser pega.
	 */
	
	public void pagarDespesas(String nome);
	
	
	/**
	 * Cria uma nova Particao e adiciona ao HashSet particoes.
	 * @throws ParticaoExistenteException	Joga uma exceção na pilha, quando inserido um nome repetido de partição.
	 */
	
	public void adicionarParticao() throws ParticaoExistenteException; 
	
	


	
	
}
