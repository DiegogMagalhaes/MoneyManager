package br.com.diegogabriel.moneymanager.runtime;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/**
 * Classe com o objetivo de implementar o metodo main e executar o RuntimeMoneyManager em loop.
 * 
 * @author Diego Gabriel
 * @version 1.0
 */

public final class Program {
	
	public static void main(String args[]) throws FileNotFoundException, ClassNotFoundException, IOException {		
		RuntimeMoneyManager rmm = new RuntimeMoneyManager();
		inicializar(rmm);
		boolean running = true;
		
		while(running) {
			
			try{
				running = rmm.menu();
			}
			catch(RuntimeException ex){
				System.out.println(ex.getMessage()); 
			}
		}
		
	}

	/**
	 * Inicializa uma interface para o usuario dar entrada nas informações necessarias para inicialização do programa
	 * 
	 * @param rmm	Referencia ao RuntimeMoneyManager que vai ser executado em loop
	 * @throws FileNotFoundException
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	
	private static void inicializar(RuntimeMoneyManager rmm) throws FileNotFoundException, ClassNotFoundException, IOException {
		
		
		Scanner scan = new Scanner(System.in);
		
		System.out.println("------------------------------MONEY MANAGER------------------------------");
		System.out.println("Seja bem vindo! aqui você pode ter mais controle sobre o seu dinheiro.");
		System.out.println("Sempre que for fazer uma nova despesa utilize o money manager para tanto ter mais controle quanto analizar se vale a pena\n\n");

		System.out.println("Selecione uma opção: ");
		System.out.println("1: Criar um novo usuario");
		System.out.println("2: Carregar Usuario");
		
		int choice = Integer.parseInt(scan.nextLine());
		
		switch(choice) {
			case 1: 
				rmm.usuario = rmm.criarNovo();
			break;
			
			case 2: 
				System.out.println("Digite o caminho do arquivo: ");
				String path = scan.nextLine();

				rmm.usuario = rmm.carregarUsuario(path);
			break;
				
		}
		
		
	}

}
