package br.com.diegogabriel.moneymanager.runtime;

import java.util.Locale;
import java.util.Scanner;

import br.com.diegogabriel.moneymanager.modelo.MoneyManager;
import br.com.diegogabriel.moneymanager.modelo.Usuario;

import java.io.*;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;

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
	 * @return Retorna um booleano, cujo so retorna false quando selecionado a opção de sair.
	 * @throws NullPointerException	Joga na pilha uma exceção caso usuario seja nulo.	
	 * @throws IOException 
	 */
	
	public boolean menu() throws NullPointerException, IOException{
		
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
		System.out.println("8. Salvar e sair\n\n");
	
		int choice = Integer.parseInt(scan.nextLine());	
		
		switch(choice) {
		
		case 1: 
			usuario.moneyManager.adicionarDespesa();
			return true;
				
		case 2: 
			usuario.moneyManager.mostrarDespesas(false);
			
			System.out.println("\n\nInsira o nome da despesa que deseja pagar:");	
			String despesaNome = scan.nextLine();
			
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
			Double valor = scan.nextDouble();
			usuario.moneyManager.adicionarSaldo(valor);
			return true;
		
		case 8:
			System.out.println("Nomeie o arquivo a ser salvo: ");
			String path = scan.nextLine();

			salvarUsuario(path);
			
		}

		return false;
	}
	
	
	/** 
	 * Cria um novo Usuario e o retorna.
	 * 
	 * @return Retorna um novo Usuario.
	 * @throws IllegalArgumentException Joga uma exceção na pilha quando, o salario for menor que zero ou nome for invalido.
	 */
	
	public Usuario criarNovo() throws IllegalArgumentException{
		
		Scanner scan = new Scanner(System.in).useLocale(Locale.US);
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

	
	/**
	 * Salva o usuario em um arquivo com nome expecificado pelo usuario. 
	 * O arquivo sera salvo sempre em C:/MoneyManager.
	 * Caso um arquivo com o mesmo nome de um antigo seja salvo, esse arquivo é sobre escrito.
	 * 
	 * @param name String referente ao nome do arquivo que sera salvo
	 * @throws IOException Joga uma exceção na pilha caso ocorre algum erro no procedimento de entrada/saida
	 */
	
	public void salvarUsuario(String name) throws IOException {
		
		String path = "C:/MoneyManager/"+name;

		try {	
			File diretorio = new File("C:/MoneyManager/");
			if(!diretorio.exists())diretorio.mkdir();
		}
		catch(Exception ex) {
			System.out.println(ex.getMessage());
		}

		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path));
		oos.writeObject(this.usuario);
		
        oos.close();	
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
	
	public Usuario carregarUsuario(String name) throws FileNotFoundException, IOException, ClassNotFoundException {
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream("C:/MoneyManager/"+name));
        Usuario usuarioCarregado = (Usuario) ois.readObject();
        ois.close();
        
        return usuarioCarregado;
	}
}
