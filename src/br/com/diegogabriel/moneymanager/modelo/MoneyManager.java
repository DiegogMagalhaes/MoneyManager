package br.com.diegogabriel.moneymanager.modelo;

import br.com.diegogabriel.moneymanager.despesas.Despesa;
import br.com.diegogabriel.moneymanager.despesas.DespesaMensal;
import br.com.diegogabriel.moneymanager.despesas.Gasto;
import br.com.diegogabriel.moneymanager.exception.SaldoInsuficienteException;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class MoneyManager{

	private Set<Despesa> despesas = new HashSet<>();
	private Set<ParticaoGastos> particoes = new HashSet<>();
	private Double saldo;
	
	private enum tipodeDespesa{
		DESPESAMENSAL(1),
		GASTOS(2);
		
		private final int id;
		
		tipodeDespesa(int id) {
			this.id = id;
		}
		
		private int getValor() {
			return id;
		}
	}

	public void adicionarSaldo(Double valor) {
		saldo += valor;
	}
	
	public void adicionarDespesa() {
			
		Double valor = 0d; 
		String nome = "";
		String descricao = "";
		
		Scanner scan = new Scanner(System.in);
		
		
		System.out.println("Qual tipo de despesa deseja? \n "
							+ "1. Despesa mensal - Despesas que se renovam de mes em mes\n "
							+ "2. Gastos - Despesas ocassionais do dia a dia e demais compras");
		int tipo = 0; 
		tipo = Integer.parseInt(scan.nextLine());
		
		if(tipo > 2 && tipo < 1) throw new IllegalArgumentException("O valor deve ser 1(Despesa Mensal) ou 2(Gastos)");
		System.out.println("Insira um nome para a despesa: ");
		nome = scan.nextLine();
		
		System.out.println("De uma pequna descrição da despesa: ");
		descricao = scan.nextLine();
		
		System.out.println("Insira o valor da despesa: ");
		valor = scan.nextDouble();
		
		if(tipo == tipodeDespesa.DESPESAMENSAL.getValor()) 
		{
			LocalDate data = criarLocalDateDespesaMensal();
			Despesa despesa = new DespesaMensal(valor,nome,descricao,data);
			despesas.add(despesa);
			
		}
		
		else if(tipo == tipodeDespesa.GASTOS.getValor()) 
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
		
		for(ParticaoGastos x : particoes) 
			System.out.println(x);
		
		System.out.print("\n Se sim insira o nome da particao: ");
		
		particao = scan.nextLine();
		
		for(ParticaoGastos x : particoes)
			if(particao.equals(x.getID())) return x;
		
		return null;
		
	}
	
	private LocalDate criarLocalDateDespesaMensal() {
		
		String data;
		LocalDate dataretorno;
		
		Scanner scan = new Scanner(System.in);
		
		System.out.println("Insira uma data no formato dd/mm/aa:");
		
		data = scan.nextLine();
		
		DateTimeFormatter formato = (DateTimeFormatter.ofPattern("dd/MM/yy")); 
		dataretorno = LocalDate.parse(data, formato);
		
		return dataretorno;
	}
	
	public void mostrarDespesas() {
		despesas.forEach(despesa -> System.out.println(despesa));
	}
	
	public void pagarDespesas(String nome) {
		despesas.forEach(despesa ->
		{
			if(despesa.equals(nome)) saldo = despesa.pagar(saldo);
		});
		
	}

	public void adicionarParticao() {
		Double limite; 
		String ID;
		
		Scanner scan = new Scanner(System.in);
		
		System.out.println("Insira o nome da particao: ");
		ID = scan.next();
		
		System.out.println("Insira o valor limite alocado a essa particao: ");
		limite = scan.nextDouble();
		
		ParticaoGastos novaParticao = new ParticaoGastos(ID,limite);
		particoes.add(novaParticao);
	}

	public MoneyManager(Double saldo) {
		this.saldo = saldo;
	}
	
	@Override
	public String toString() {
		return "Saldo: " + saldo.toString();
	}
	
}
