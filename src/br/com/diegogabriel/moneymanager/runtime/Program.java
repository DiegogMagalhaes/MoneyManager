package br.com.diegogabriel.moneymanager.runtime;

import java.util.Scanner;

/**
 * Classe com o objetivo de implementar o metodo main e executar o RuntimeMoneyManager em loop.
 * 
 * @author Diego Gabriel
 * @version 1.0
 */

public final class Program {
	
public static void main(String args[]) {		
	
		RuntimeMoneyManager rmm = new RuntimeMoneyManager();
		rmm.usuario = rmm.criarNovo();
		
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
}
