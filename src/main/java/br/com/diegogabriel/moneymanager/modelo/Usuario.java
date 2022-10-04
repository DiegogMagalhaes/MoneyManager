package br.com.diegogabriel.moneymanager.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "USUARIO")
public class Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private Integer senha;
	private Double saldo;
	private Double saldoPrevisto;
	private Double salario;
	
	
	/**
	 * @return the saldoPrevisto
	 */
	public Double getSaldoPrevisto() {
		return saldoPrevisto;
	}

	/**
	 * @param saldoPrevisto the saldoPrevisto to set
	 */
	public void setSaldoPrevisto(Double saldoPrevisto) {
		this.saldoPrevisto = saldoPrevisto;
	}

	/**
	 * Retorna o salario do usuario.
	 * 
	 * @return Salario do usuario
	 */
	
	public Double getSalario() {
		return salario;
	}
	
	/**
	 * Retorna a senha do usuario.
	 * 
	 * @return Senha do usuario
	 */
	
	public Integer getSenha() {
		return senha;
	}
	
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Retorna o nome do usuario.
	 * 
	 * @return	nome do usuario
	 */
	public String getNome() {
		return nome;
	}
	
	public Double getSaldo() {
		return saldo;
	}
	
	/**
	 * Atribui um valor a usuario.
	 * 
	 * @param salario do usuario
	 */
	
	public void setSalario(Double salario) {
		this.salario = salario ;
	}
	
	
	public void setSaldo(Double saldo) {
		this.saldo = saldo;
	}
	
	/**
	 * Atribui um valor a senha.
	 * 
	 * @param senha do usuario
	 */
	
	public void setSenha(Integer senha) {
		this.senha= senha ;
	}
	
	
	/**
	 * Atribui um valor a nome
	 * 
	 * @param nome do usuario
	 */
	public void setNome(String nome) {
		this.nome= nome ;
	}
	
	
	/**
	 * Recebe um valor, no qual sera o novo salario do usuario.
	 * 
	 * @param valor	Double referente ao novo salario do usuario.
	 */
	
	public void alterarSalario(Double valor) {
		this.salario = valor;
	}
	
	
	public Usuario(String nome, Integer senha, Double salario) {
		this.nome = nome;
		this.senha = senha;
		this.salario = salario;
		this.saldo = salario;
		this.saldoPrevisto = salario;
	}
	
	
	public Usuario() {
		// TODO Auto-generated constructor stub
	}

	public Usuario(String nome, Integer senha, Double salario, Double saldo) {
		this.nome = nome;
		this.senha = senha;
		this.salario = salario;
		this.saldo = saldo;
		this.saldoPrevisto = saldo;
	}

	public Usuario(String nome, Integer senha, Double salario, Double saldo, Double saldoPrevisto) {
		this.nome = nome;
		this.senha = senha;
		this.salario = salario;
		this.saldo = saldo;
		this.saldoPrevisto = saldoPrevisto;
	}

	@Override
	public String toString(){
		return "Nome: " + nome;
	}

}
