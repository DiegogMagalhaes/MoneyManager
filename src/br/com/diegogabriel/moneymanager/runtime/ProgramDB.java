package br.com.diegogabriel.moneymanager.runtime;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.util.Scanner;

import br.com.diegogabriel.moneymanager.conexao.ConnectionPool;
import br.com.diegogabriel.moneymanager.dao.UsuarioDAO;

public class ProgramDB {

	public static void main(String args[]){		
	
	RuntimeMoneyManagerDB rmm = new RuntimeMoneyManagerDB();
	inicializar(rmm);
	boolean running = true;
	
	while(running) {
		
		try{
			running = rmm.menu();
		}
		catch(Exception ex){
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

private static void inicializar(RuntimeMoneyManagerDB rmm) {
	
	
	Scanner scan = new Scanner(System.in);
	boolean inicializou = false;
	
	System.out.println("------------------------------MONEY MANAGER------------------------------");
	System.out.println("Seja bem vindo! aqui você pode ter mais controle sobre o seu dinheiro.");
	System.out.println("Sempre que for fazer uma nova despesa utilize o money manager para tanto ter mais controle quanto analizar se vale a pena\n\n");
	
	while(!inicializou) 
	{
	
		System.out.println("Selecione uma opção: ");
		System.out.println("1: Criar um novo usuario");
		System.out.println("2: Carregar Usuario");
		
		try {
		
			int choice = Integer.parseInt(scan.nextLine());
			if(choice != 2 && choice != 1) throw new IllegalArgumentException("O valor deve ser 1(Criar um novo usuario) ou 2(Carregar Usuario2)");
			
			switch(choice) {
				case 1: 
					rmm.usuario = rmm.criarNovo();
					inicializou = true;
				break;
				
				case 2: 
					System.out.println("Digite o nome do usuario: ");
					String nome = scan.nextLine();
	
					rmm.usuario = rmm.carregarUsuario(nome);
					inicializou = true;
				break;
					
			}
		
		}
		
		catch(Exception ex) {
			System.out.println(ex.getMessage() + "\n\n\n");
		}
	}
	
}

}
