package br.com.diegogabriel.moneymanager.runtime;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Scanner;

import br.com.diegogabriel.moneymanager.conexao.ConnectionPool;
import br.com.diegogabriel.moneymanager.dao.MoneyManagerDAO;
import br.com.diegogabriel.moneymanager.dao.UsuarioDAO;
import br.com.diegogabriel.moneymanager.modelo.MoneyManagerDB;
import br.com.diegogabriel.moneymanager.modelo.UsuarioDB;

public class RuntimeMoneyManagerDB {
public UsuarioDB usuario;
	
	/**
	 * Interface de entrada e saida, onde executa determinado metodo de MoneyManager dependendo do input do usuario.
	 * 
	 * @return Retorna um booleano, cujo so retorna false quando selecionado a opção de sair.
	 * @throws SQLException 
	 * @throws NullPointerException	Joga na pilha uma exceção caso usuario seja nulo.	
	 * @throws IOException 
	 */
	
	public boolean menu() throws SQLException{
		
		if(usuario == null) throw new NullPointerException("Usuario invalido");
		
		Scanner scan = new Scanner(System.in).useLocale(Locale.US);
		
		System.out.println("______________________________________________");
		System.out.println(usuario);
		System.out.println(usuario.moneyManager);
		System.out.println("Digite o numero referente a uma opção");
		System.out.println("______________________________________________\n\n");
		
		System.out.println("1. Adicionar despesa");
		System.out.println("2. Pagar despesa");
		System.out.println("3. Mostrar despesas");
		System.out.println("4. Adicionar partição");
		System.out.println("5. Remover despesa");
		System.out.println("6. Remover partição");
		System.out.println("7. Adicionar Saldo");
		System.out.println("8. sair\n\n");
	
		int choice = Integer.parseInt(scan.nextLine());	
		
		switch(choice) {
		
		case 1: 
			
			usuario.moneyManager.adicionarDespesa();
			atualizarMoneyManagerDB();
			return true;
				
		case 2: 
			usuario.moneyManager.mostrarDespesas(false);
			
			System.out.println("\n\nInsira o nome da despesa que deseja pagar:");	
			String despesaNome = scan.nextLine();
			
			usuario.moneyManager.pagarDespesas(despesaNome);
			atualizarMoneyManagerDB();
			return true;
		
		case 3: 
			usuario.moneyManager.mostrarDespesas();
			return true;
		
		case 4: 
			usuario.moneyManager.adicionarParticao();
			return true;
			
		case 7:
			System.out.println("Insira o valor a ser adicionado:");
			Double valor = scan.nextDouble();
			usuario.moneyManager.adicionarSaldo(valor);
			atualizarMoneyManagerDB();
			return true;
		
		case 8:
			return false;
		}

		return false;
	}
	
	
	/** 
	 * Cria um novo Usuario e o retorna.
	 * 
	 * @return Retorna um novo Usuario.
	 * @throws IllegalArgumentException Joga uma exceção na pilha quando, o salario for menor que zero ou nome for invalido.
	 * @throws SQLException 
	 */
	
	public UsuarioDB criarNovo() throws IllegalArgumentException, SQLException{
		
		Scanner scan = new Scanner(System.in).useLocale(Locale.US);
		String nome;
		Double salario = 0d;
		
		System.out.println("Qual seu nome?");
		nome = scan.nextLine();
		
		if(nome.equals("")) throw new IllegalArgumentException("Nome não pode ser vazio");
		
		System.out.println("Quanto você recebe de salario?");
		salario = scan.nextDouble();
		
		if(salario < 0) throw new IllegalArgumentException("Salario não pode ser menor que zero");
		
		UsuarioDB novoUsuario = new UsuarioDB(nome, salario);
	
		MoneyManagerDB moneyManager = new MoneyManagerDB(salario, nome);	
		novoUsuario.moneyManager = moneyManager;
		
		ConnectionPool pool = new ConnectionPool();
		
		try(Connection con = pool.getConnection()){
			
			UsuarioDAO usuarioDAO = new UsuarioDAO(con); 
			usuarioDAO.inserir(novoUsuario);
		
			MoneyManagerDAO moneyManagerDAO = new MoneyManagerDAO(con);
			moneyManagerDAO.inserir(novoUsuario.moneyManager, novoUsuario.getNome());
		
		}
		
		
		System.out.print("\n\n");

		return novoUsuario;
	}
	
	/**
	 * Carrega um arquivo de usuario e o retorna
	 * 
	 * @param name String referente ao nome do arquivo que sera salvo
	 * @return Retorna a instancia de usuario salva no arquivo
	 * @throws FileNotFoundException  Joga uma exceção na pilha caso o arquivo não seja encontrado
	 * @throws IOException		  Joga uma exceção na pilha caso ocorre algum erro no procedimento de entrada/saida
	 * @throws ClassNotFoundException Joga uma exceção na pilha caso a classe não seja encontrada
	 */
	
	public UsuarioDB carregarUsuario(String name) throws FileNotFoundException, IOException, ClassNotFoundException {
		ConnectionPool pool = new ConnectionPool();
		
		try(Connection con = pool.getConnection()){
			
			UsuarioDAO usuarioDAO = new UsuarioDAO(con);
			UsuarioDB user = usuarioDAO.searchUsuarioByName(name);
			
			MoneyManagerDAO moneyManagerDAO = new MoneyManagerDAO(con);
			MoneyManagerDB moneyManager = moneyManagerDAO.getMoneyManager(name);	
			user.moneyManager = moneyManager;
			
			return user;
		}
		catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
		
		throw new NullPointerException("Usuario não encontrado");
		
	}
	
	
	
	public void atualizarMoneyManagerDB() throws SQLException {
		ConnectionPool pool = new ConnectionPool();
		
		try(Connection con = pool.getConnection()){
			MoneyManagerDAO dao = new MoneyManagerDAO(con);
			dao.atualizar(usuario.moneyManager, usuario.getNome());
		}
	}

}
