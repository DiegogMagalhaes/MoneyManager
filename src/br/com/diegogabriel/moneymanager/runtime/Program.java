package br.com.diegogabriel.moneymanager.runtime;

import java.util.Scanner;

public class Program {
	
public static void main(String args[]) {		
	
		RuntimeMoneyManager rmm = new RuntimeMoneyManager();
		rmm.usuario = rmm.criarNovo();
		
		boolean running = true;
		
		while(running) {
			
			running = rmm.menu();
			
		}
		
	}
}
