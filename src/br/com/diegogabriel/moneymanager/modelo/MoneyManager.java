package br.com.diegogabriel.moneymanager.modelo;

import br.com.diegogabriel.moneymanager.despesas.Despesa;
import br.com.diegogabriel.moneymanager.despesas.DespesaMensal;
import br.com.diegogabriel.moneymanager.despesas.Gasto;
import br.com.diegogabriel.moneymanager.exception.SaldoInsuficienteException;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Classe responsavel por fazer todo controle,criação e alteração de saldo e despesa.
 * 
 * @author Sintese Suporte05
 * @version 1.0
 */

public class MoneyManager{

	/**
	 * saldo se refere ao valor atual de saldo disponivel.
	 * saldoPrevisto se refere ao valor de saldo ja prevendo o pagamento de todas as despesas adicionadas.
	 */
	
	private Set<Despesa> despesas = new HashSet<>();
	private Set<Particao> particoes = new HashSet<>();
	private Double saldo;
	private Double saldoPrevisto;
	
	/**
	 * ENUM responsavel pela separação de tipo.
	 * DESPESAMENSAL = 1.
	 * GASTOS = 2.
	 * 
	 * utiliza do metodo getValor() para pegar o valor da constrante id do ENUM.
	 */
	
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
	
	
	public MoneyManager(Double saldo) {
		this.saldo = saldo;
	}
	
	
	/**
	 * Recebe um valor para adicionar ao saldo.
	 * 
	 * @param valor	Double que sera adicionado ao saldo vigente.
	 */
	public void adicionarSaldo(Double valor) {
		saldo += valor;
	}
	
	
	/**
	 * Adciona uma nova despesa ao HashSet despesas. 
	 * Possui uma interface via console para auxiliar o usuario do programa a dar a entrada necessaria.
	 * 
	 */
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
		
		avaliarNovaDespesa(valor);
		
		if(tipo == tipodeDespesa.DESPESAMENSAL.getValor()) 
		{
			LocalDate data = criarLocalDateDespesaMensal();
			Despesa despesa = new DespesaMensal(valor,nome,descricao,data);
			
			despesas.add(despesa);
			
		}
		
		else if(tipo == tipodeDespesa.GASTOS.getValor()) 
		{
			Particao particao = criarParticaoGastos(valor);
			Despesa despesa = new Gasto(valor,nome,descricao,particao);
			
			despesas.add(despesa);
		}						
		
	}
	
	
	/**
	 * Disponibiliza ao usuario as partições existente para categorizar seu gasto. Então o valorGasto recebido como parametro é adiconado nos gastos mensais na partição escolhida.
	 * 
	 * @param valorGasto 	Double referente ao valor da despesa, que sera descontado em sua partição caso tenha uma.
	 * @return	 			Retorna a Particao escolhida.
	 */
	private Particao criarParticaoGastos(Double valorGasto) {
		
		String particao;
		Scanner scan = new Scanner(System.in);
		
		System.out.println("Desseja inserir esse gasto a alguma partição existente?\n");
		
		for(Particao x : particoes) 
			System.out.println(x);
		
		System.out.print("\n Se sim insira o nome da particao: ");
		
		particao = scan.nextLine();
		
		for(Particao x : particoes) {
			if(particao.equals(x)) 
			{
				x.verificarLimite(valorGasto);
				return x;
			}
		}
		return null;
		
	}
	
	
	/**
	 * Gera uma LocalDate atraves de uma entrada de data so usuario.
	 * 
	 * @return	Retorna uma LocalDate referente a uma entrada do usuario.
	 */
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
	
	
	/**
	 * Imprime todas as despesas disponiveis no HashSet despesas.
	 * 
	 */
	public void mostrarDespesas() {
		despesas.forEach(despesa -> System.out.println(despesa));
	}
	
	
	/**
	 * Recebe como parametro o nome de uma despesa, na qual sera paga. 
	 * 
	 * @param nome	String representante do nome da despesa a ser pega.
	 */
	
	public void pagarDespesas(String nome) {
		despesas.forEach(despesa ->
		{
			if(despesa.equals(nome)) saldo = despesa.pagar(saldo);
		});
		
	}

	
	/**
	 * Cria uma nova Particao e adiciona ao HashSet particoes.
	 * 
	 */
	
	public void adicionarParticao() {
		Double limite = 0d; 
		String ID = "";
		
		Scanner scan = new Scanner(System.in);
		
		System.out.println("Insira o nome da partição: ");
		ID = scan.nextLine();
		
		System.out.println("Insira o valor limite alocado a essa particao: ");
		limite = scan.nextDouble();
		
		Particao novaParticao = new Particao(ID,limite);
		particoes.add(novaParticao);
	}
	
	
	/**
	 * Avalia se uma despesa a ser adicionada pode ser paga com o valor previsto do saldo apos ter pago todas despesas.
	 * Esse metodo também mostra o resultado do saldo ao usuario e questiona se ele realmente deseja continuar com a adição dessa despesa.
	 * Por fim caso o usuario queira continuar com a nova despesa o valor de saldoPrevisto é atualizado, ja prevendo como sera o valor do saldo apos pagar essa nova despesa.
	 * 
	 * @param valor Double				refrente ao valor da despesa que sera adicionada.
	 * @throw SaldoInsuficienteException	Joga uma exceção, quando a despesa que sera adicionada não podera ser paga com o saldo atual.
	 * @throw IllegalArgumentException	Joga uma exceção, quando resposta do usuario for algo não esperado pelo sistema.
	 */
	
	private void avaliarNovaDespesa(Double valor) throws SaldoInsuficienteException, IllegalArgumentException{
		
		if(saldo - valor < 0) 
			throw new SaldoInsuficienteException(); 
		else if(saldoPrevisto - valor < 0) 
			throw new SaldoInsuficienteException("Não é possivel adicionar uma nova despesa, pois ela não podera ser paga, considerando o valor a ser pago das outras depesas.");
		
		Scanner scan = new Scanner(System.in);
		
		System.out.printf("\nO novo valor do seu saldo sera: %.2f. Deseja Adicionar essa Despesa?\n\n Digite: sim ou não.\n",valor);
		String resposta = scan.nextLine();
		
		if(resposta.equals("sim")) saldoPrevisto -= valor;
		else if(resposta.equals("não")) System.out.println("\n\nDespesa não adicionada\n\n");
		else throw new IllegalArgumentException("A resposta deve ser sim ou não");
	}

	
	@Override
	public String toString() {
		return "Saldo: " + saldo.toString();
	}
	
}
