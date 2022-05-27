package br.com.diegogabriel.moneymanager.runtime;

import java.io.IOException;
import java.util.Scanner;

import br.com.diegogabriel.moneymanager.modelo.MoneyManager;
import br.com.diegogabriel.moneymanager.modelo.Usuario;

/**
 * Classe com o objetivo de exibir interfaces e executar os metodos de MoneyManager.
 * 
 * @author Diego Gabriel
 * @version 1.0
 */
public final class RuntimeMoneyManager {
	
	public Usuario usuario;
	
	/**
	 * Interface de entrada e saida, onde executa determinado metodo de MoneyManager dependendo do input do usuario.
	 * 
	 * @return							Retorna um booleano, cujo so retorna false quando selecionado a opção de sair.
	 * @throws 	NullPointerException	Joga na pilha uma exceção caso usuario seja nulo.	
	 */
	
	public boolean menu() throws NullPointerException{
		
		if(usuario == null) throw new NullPointerException("Usuario invalido");
		
		Scanner scan = new Scanner(System.in);
		
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
		System.out.println("8. Salvar e sair\n\n");
	
		int choice = Integer.parseInt(scan.nextLine());	
		
		switch(choice) {
		
		case 1: 
			usuario.moneyManager.adicionarDespesa();
			return true;
				
		case 2: 
			usuario.moneyManager.mostrarDespesas();
			System.out.println("\n\nInsira o nome da despesa que deseja pagar:");
			Scanner scanDespesa = new Scanner(System.in);
			String despesaNome = scanDespesa.next();
			usuario.moneyManager.pagarDespesas(despesaNome);
			return true;
		
		case 3: 
			usuario.moneyManager.mostrarDespesas();
			return true;
		
		case 4: 
			usuario.moneyManager.adicionarParticao();
			return true;
			
		case 7:
			System.out.println("Insira o valor a ser adicionado:");
			Scanner scanValor = new Scanner(System.in);
			Double valor = scanValor.nextDouble();
			usuario.moneyManager.adicionarSaldo(valor);
			return true;

		}

		return false;
	}
	
	
	/** 
	 * Cria um novo Usuario e o retorna.
	 * 
	 * @return	Retorna um novo Usuario.
	 * @throws IllegalArgumentException	Joga uma exceção na pilha quando, o salario for menor que zero ou nome for invalido.
	 */
	public Usuario criarNovo() throws IllegalArgumentException{
		
		Scanner scan = new Scanner(System.in);
		String nome;
		Double salario = 0d;
		
		System.out.println("Qual seu nome?");
		nome = scan.nextLine();
		
		if(nome.equals("")) throw new IllegalArgumentException("Nome não pode ser vazio");
		
		System.out.println("Quanto você recebe de salario?");
		salario = scan.nextDouble();
		
		if(salario < 0) throw new IllegalArgumentException("Salario não pode ser menor que zero");
		
		Usuario novoUsuario = new Usuario(nome, salario);
		
		MoneyManager moneyManager = new MoneyManager(salario);
		novoUsuario.moneyManager = moneyManager;
		
		System.out.print("\n\n");

		return novoUsuario;
	}

}
