package br.com.diegogabriel.moneymanager.modelo;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;
import java.util.Set;

import br.com.diegogabriel.moneymanager.conexao.ConnectionPool;
import br.com.diegogabriel.moneymanager.dao.DespesaDAO;
import br.com.diegogabriel.moneymanager.dao.DespesaMensalDAO;
import br.com.diegogabriel.moneymanager.dao.GastoDAO;
import br.com.diegogabriel.moneymanager.dao.ParticaoDAO;
import br.com.diegogabriel.moneymanager.despesas.Despesa;
import br.com.diegogabriel.moneymanager.despesas.DespesaMensal;
import br.com.diegogabriel.moneymanager.despesas.Gasto;
import br.com.diegogabriel.moneymanager.exception.DespesaExistenteException;
import br.com.diegogabriel.moneymanager.exception.ParticaoExistenteException;
import br.com.diegogabriel.moneymanager.exception.SaldoInsuficienteException;

public class MoneyManagerDB implements IMoneyManager {

	/**
	 * saldo se refere ao valor atual de saldo disponivel.
	 * saldoPrevisto se refere ao valor de saldo ja prevendo o pagamento de todas as despesas adicionadas.
	 */
	
	private Double saldo;
	private Double saldoPrevisto;
	private String usuarioNome;
	private DespesaDAO despesaDAO;
	private DespesaMensalDAO despesaMensalDAO;
	private	GastoDAO gastoDAO;
	private ParticaoDAO particaoDAO;
	Connection con;
	
	public MoneyManagerDB(Double saldo, String usuarioNome) throws SQLException {
		
		this.saldo = saldo;
		saldoPrevisto = saldo;
		this.usuarioNome = usuarioNome;
		
		ConnectionPool pool = new ConnectionPool();
		con = pool.getConnection();
		try{
			
			despesaDAO = new DespesaDAO(con); 
			despesaMensalDAO = new DespesaMensalDAO(con);
			gastoDAO = new GastoDAO(con);
			particaoDAO = new ParticaoDAO(con);
				
		}
		catch(Exception ex) {
			System.out.println("Falha ao conectar ao banco de dados.\n\n" + ex.getMessage());
		}
		
	}

	
	public MoneyManagerDB(String usuarioNome, Double saldo, Double saldoPrevisto) throws SQLException {
		
		this.saldo = saldo;
		this.saldoPrevisto = saldoPrevisto;
		this.usuarioNome = usuarioNome;
		
		ConnectionPool pool = new ConnectionPool();
		con = pool.getConnection();
		try{
			
			despesaDAO = new DespesaDAO(con); 
			despesaMensalDAO = new DespesaMensalDAO(con);
			gastoDAO = new GastoDAO(con);
			particaoDAO = new ParticaoDAO(con);
				
		}
		catch(Exception ex) {
			System.out.println("Falha ao conectar ao banco de dados.\n\n" + ex.getMessage());
		}
		
	}

	public String getusuarioNome() {
		return usuarioNome;
	}
	
	public Double getSaldo() {
		return saldo;
	}
	
	public Double getSaldoPrevisto() {
		return saldoPrevisto;
	}
	
	
	@Override
	public void adicionarSaldo(Double valor) throws IllegalArgumentException {
		if(valor > 0) {
			saldo += valor;
			saldoPrevisto += valor;
		}
		else throw new IllegalArgumentException("valor invalido, porfavor insira um valor maior que 0");
	}
	
	
	@Override
	public void adicionarDespesa() throws DespesaExistenteException {
		
		Double valor = 0d; 
		String nome = "";
		String descricao = "";
		
		Scanner scan = new Scanner(System.in).useLocale(Locale.US);
		
		
		System.out.println("Qual tipo de despesa deseja? \n "
							+ "1. Despesa mensal - Despesas que se renovam de mes em mes\n "
							+ "2. Gastos - Despesas ocassionais do dia a dia e demais compras");
		int tipo = 0; 
		tipo = Integer.parseInt(scan.nextLine());
		
		
		if(tipo != 2 && tipo != 1) throw new IllegalArgumentException("O valor deve ser 1(Despesa Mensal) ou 2(Gastos)");
		System.out.println("Insira um nome para a despesa: ");
		nome = scan.nextLine();
		
		if(existeDespesa(nome)) throw new DespesaExistenteException();
		
		
		System.out.println("De uma pequna descrição da despesa: ");
		descricao = scan.nextLine();
		
		
		System.out.println("Insira o valor da despesa: ");
		valor = scan.nextDouble();
		
		if(tipo == tipodeDespesa.DESPESAMENSAL.getValor()) 
		{
			LocalDate data = criarLocalDateDespesaMensal();
			DespesaMensal despesa = new DespesaMensal(valor,nome,descricao,data);
			
			try{
			avaliarNovaDespesa(valor);
			despesaMensalDAO.inserir(despesa,usuarioNome);
			}
			catch(SQLException ex) {
				System.out.println(ex.getMessage());
			}
		}
		
		else if(tipo == tipodeDespesa.GASTOS.getValor()) 
		{
			Particao particao = escolherParticaoGastos(valor);
			Gasto despesa = new Gasto(valor,nome,descricao,particao);
			
			try {
				avaliarNovaDespesa(valor);
				gastoDAO.inserir(despesa,usuarioNome);
			}
			catch(SQLException ex) {
				System.out.println(ex.getMessage());
			}
		}						
	
		
	}
	
	
	/**
	 * Disponibiliza ao usuario as partições existente para categorizar seu gasto. Então o valorGasto recebido como parametro é adiconado nos gastos mensais na partição escolhida.
	 * 
	 * @param valorGasto 	Double referente ao valor da despesa, que sera descontado em sua partição caso tenha uma.
	 * @return	 	Retorna a Particao escolhida.
	 */
	
	private Particao escolherParticaoGastos(Double valorGasto) {
		String particao;
		Scanner scan = new Scanner(System.in);
		
		System.out.println("Desseja inserir esse gasto a alguma partição existente?\n");
		
		mostrarParticoes();
		
		System.out.print("\nCaso não queria apenas aperte Enter e prosiga. Se sim insira o nome da particao: ");
		
		particao = scan.nextLine();
		
		if(existeParticao(particao)) 
		{
			Particao particaoRetorno = buscarParticao(particao);
			
			try {
				particaoRetorno.verificarLimite(valorGasto);
				particaoDAO.atualizar(particaoRetorno, usuarioNome);
				return particaoRetorno;
			}
			catch(SQLException ex) {
				System.out.println("Falha ao conectar ao banco de dados");
				return null;
			}
			
		}
		
		return null;
	
	}
	
	
	/**
	 * Gera uma LocalDate atraves de uma entrada de data so usuario.
	 * 
	 * @return Retorna uma LocalDate referente a uma entrada do usuario.
	 */
	
	
	public LocalDate criarLocalDateDespesaMensal() {
		String data;
		LocalDate dataretorno;
		
		Scanner scan = new Scanner(System.in);
		
		System.out.println("Insira uma data no formato dd/mm/aa:");
		
		data = scan.nextLine();
		
		DateTimeFormatter formato = (DateTimeFormatter.ofPattern("dd/MM/yy")); 
		dataretorno = LocalDate.parse(data, formato);
		
		return dataretorno;
	}
	
	
	@Override
	public void mostrarDespesas() {
		
		try
		{
			Set<Despesa> despesas = despesaDAO.getDespesa(usuarioNome);
			despesas.forEach(d -> System.out.println(d));
		}
		catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	
	@Override
	public void mostrarDespesas(boolean pagas) {
		try
		{
			Set<Despesa> despesas = despesaDAO.searchDespesaByPago(pagas,usuarioNome);
			despesas.forEach(d -> System.out.println(d));
		}
		catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	
	@Override
	public void pagarDespesas(String nome) {
		saldo = buscarDespesa(nome).pagar(saldo);
		despesaDAO.pagarDespesa(nome, usuarioNome);
	}

	
	/**
	 * Busca e retorna uma despesa no HashSet despesas
	 * 
	 * @param nome			Nome da despesa a ser buscada
	 * @return			Retorna a despesa buscada
	 * @throws NullPointerException	Joga uma exceção na pilha quando a despesa não for encontrada
	 */
	private Despesa buscarDespesa(String nome) throws NullPointerException{
		
		try {
			Despesa temp = despesaDAO.searchDespesaByName(nome,usuarioNome);
			if(temp == null) throw new NullPointerException();
			return temp; 
		}
		catch(SQLException ex) {
			System.out.println(ex.getMessage());
		}
		
		throw new NullPointerException();
	}
	
	/**
	 * Verifica se existe uma Despesa com o mesmo nome no HashSet despesas.
	 * 
	 * @param nome 	String que representa o nome que sera procurado em despesas.
	 * @return	retorna true se existir despesa com o msm nome e falso para caso não.
	 */
	
	private boolean existeDespesa(String nome) {
		
		try{
			buscarDespesa(nome);
		}
		catch(Exception ex) {
			return false;
		}
		
		return true;
	}
	
	
	@Override
	public void adicionarParticao() throws ParticaoExistenteException {
		Double limite = 0d; 
		String ID = "";
		
		Scanner scan = new Scanner(System.in).useLocale(Locale.US);
		
		System.out.println("Insira o nome da partição: ");
		ID = scan.nextLine();
		
		if(existeParticao(ID)) throw new ParticaoExistenteException();
		
		System.out.println("Insira o valor limite alocado a essa particao: ");
		limite = scan.nextDouble();
		
		Particao novaParticao = new Particao(ID,limite);
		
		try {
		particaoDAO.inserir(novaParticao,usuarioNome);
		}
		catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
		
	}
	
	
	private void mostrarParticoes() {
		
		try {
			Set<Particao> particoes = particaoDAO.getParticao(usuarioNome);
			particoes.forEach(p -> System.out.println(p));
		}
		catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
		
	}
	
	
	/**
	 * Busca e retorna uma partição no HashSet particoes
	 * 
	 * @param nome			Nome da partição a ser buscada
	 * @return			Retorna a Particao buscada
	 * @throws NullPointerException	Joga uma exceção na pilha quando a particao não for encontrada
	 */
	private Particao buscarParticao(String nome) throws NullPointerException{	
		try {
			Particao temp = particaoDAO.searchParticaoByName(nome,usuarioNome);
			if(temp == null) throw new NullPointerException();
			return temp; 
		}
		catch(SQLException ex) {
			System.out.println(ex.getMessage());
		}
		
		throw new NullPointerException("Particao não encontrada");
	}
	
	
	/**
	 * Verifica se existe uma Particao com o mesmo nome no HashSet particoes.
	 * 
	 * @param nome 	String que representa o nome que sera procurado em particoes.
	 * @return	retorna true se existir a partição com o msm nome e falso para caso não.
	 */
	
	private boolean existeParticao(String nome) {
		
		try{
			buscarParticao(nome);
		}
		catch(RuntimeException ex) {
			return false;
		}
		
		return true;
	}
	
	
	/**
	 * Avalia se uma despesa a ser adicionada pode ser paga com o valor previsto do saldo apos ter pago todas despesas.
	 * Esse metodo também mostra o resultado do saldo ao usuario e questiona se ele realmente deseja continuar com a adição dessa despesa.
	 * Por fim caso o usuario queira continuar com a nova despesa o valor de saldoPrevisto é atualizado, ja prevendo como sera o valor do saldo apos pagar essa nova despesa.
	 * 
	 * @param valor Double			refrente ao valor da despesa que sera adicionada.
	 * @throw SaldoInsuficienteException	Joga uma exceção, quando a despesa que sera adicionada não podera ser paga com o saldo atual.
	 * @throw IllegalArgumentException	Joga uma exceção, quando resposta do usuario for algo não esperado pelo sistema.
	 */
	
	private void avaliarNovaDespesa(Double valor) throws SaldoInsuficienteException, IllegalArgumentException{
		
		if(saldo - valor < 0) 
			throw new SaldoInsuficienteException(); 
		else if(saldoPrevisto - valor < 0) 
			throw new SaldoInsuficienteException("Essa despesa não pode ser paga com o saldo vigente, considerando o valor a ser pago das outras depesas.");
		
		Scanner scan = new Scanner(System.in);
		
		System.out.printf("\n\nO seu saldo, tendo em conta todas as contas acumuladas pagas ou não pagas sera: %.2f.\n\nDeseja Adicionar essa Despesa?\n\nDigite: sim ou não.\n\n",saldoPrevisto - valor);
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
