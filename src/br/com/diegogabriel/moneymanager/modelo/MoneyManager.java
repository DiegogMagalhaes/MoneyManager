package br.com.diegogabriel.moneymanager.modelo;

import br.com.diegogabriel.moneymanager.despesas.Despesa;
import br.com.diegogabriel.moneymanager.despesas.DespesaMensal;
import br.com.diegogabriel.moneymanager.despesas.Gasto;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class MoneyManager implements Serializable{

	Set<Despesa> despesas = new HashSet<>();
	Usuario usuario;
	Set<ParticaoGastos> particaoGastos = new HashSet<>();
	
	private enum TipodeDespesa{
		DESPESAMENSAL(1),
		GASTOS(2);
		
		private final int id;
		
		TipodeDespesa(int id) {
			this.id = id;
		}
		
		private int getValor() {
			return id;
		}
	}
	
	public void addDespesa() {
			
		Double valor; 
		String nome;
		String descricao;
		
		Scanner scan = new Scanner(System.in);
		
		System.out.println("Qual tipo de despesa deseja? \n "
							+ "1. Despesa mensal - Despesas que se renovam de mes em mes\n "
							+ "2. Gastos - Despesas ocassionais do dia a dia e demais compras");
		int tipo = scan.nextInt();
		
		if(tipo > 2 && tipo < 1) {
			scan.close();
			throw new IllegalArgumentException("O valor deve ser 1(Despesa Mensal) ou 2(Gastos)");
		} 
		
		System.out.println("Insira um nome para a despesa: ");
		nome = scan.nextLine();
		
		System.out.println("De uma pequna descrição da despesa: ");
		descricao = scan.nextLine();
		
		System.out.println("Insira o valor da despesa: ");
		valor = scan.nextDouble();
		
		scan.close();
		
		if(tipo == TipodeDespesa.DESPESAMENSAL.getValor()) 
		{
			LocalDate data = criarLocalDateDespesaMensal();
			Despesa despesa = new DespesaMensal(valor,nome,descricao,data);
			despesas.add(despesa);
			
		}
		
		else if(tipo == TipodeDespesa.GASTOS.getValor()) 
		{
			ParticaoGastos particao = criarParticaoGastos();
			Despesa despesa = new Gasto(valor,nome,descricao,particao);
			despesas.add(despesa);
		}						
		
	}
	
	private ParticaoGastos criarParticaoGastos() {
		
		String particao;

		Scanner scan = new Scanner(System.in);
		
		System.out.println("Desseja inserir esse gasto a alguma partição existente?\n");
		
		for(ParticaoGastos x : particaoGastos) 
			System.out.println(x);
		
		System.out.print("\n Se sim insira o nome da particao: ");
		
		particao = scan.nextLine();
		scan.close();
		
		for(ParticaoGastos x : particaoGastos)
			if(particao.equals(x.getID())) return x;
		
		return null;
		
	}
	
	private LocalDate criarLocalDateDespesaMensal() {
		String data;

		Scanner scan = new Scanner(System.in);
		
		System.out.println("Insira uma data no formato dd/mm/aa:");
		
		data = scan.nextLine();
		scan.close();
		
		DateTimeFormatter formato = (DateTimeFormatter.ofPattern("dd/mm/yy")); 
		
		return LocalDate.parse(data, formato);
	}
}
