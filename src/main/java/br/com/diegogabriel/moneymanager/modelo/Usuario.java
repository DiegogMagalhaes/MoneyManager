package br.com.diegogabriel.moneymanager.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Classe responsavel por definir um Usuario. 
 * 
 * @author Diego Gabriel
 * @version 2.0
 */
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
	
	
	//----------Construtores----------
	
	/**
	 * Construtor padrão, sem parametros.
	 */
	public Usuario() {
	
	}
	
	//----------Getters and Setters----------
	
	/**
	 * @return o id do usuario
	 */
	public Long getId() {
		return id;
	}
	
	/**
	 * @return nome do usuario
	 */
	public String getNome() {
		return nome;
	}
	
	/**
	 * @return Senha do usuario
	 */
	public Integer getSenha() {
		return senha;
	}
	
	/**
	 * @return saldo do usuario
	 */
	public Double getSaldo() {
		return saldo;
	}
	
	/**
	 * @return o saldoPrevisto do usuario
	 */
	public Double getSaldoPrevisto() {
		return saldoPrevisto;
	}
	
	/**
	 * @return Salario do usuario
	 */
	public Double getSalario() {
		return salario;
	}
	
	
	

	/**
	 * @param id Id que indentifica a despesa no banco de dados
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * @param nome nome referente ao usuario
	 */
	public void setNome(String nome) {
		this.nome= nome ;
	}
	
	/**
	 * @param senha senha referente ao usuario
	 */
	public void setSenha(Integer senha) {
		this.senha= senha ;
	}
	
	/**
	 * @param saldo saldo referente ao usuario
	 */
	public void setSaldo(Double saldo) {
		this.saldo = saldo;
	}
	
	/**
	 * @param saldoPrevisto saldoPrevisto referente ao usuario
	 */
	public void setSaldoPrevisto(Double saldoPrevisto) {
		this.saldoPrevisto = saldoPrevisto;
	}
	
	/**
	 * @param salario salario referente ao usuario
	 */
	
	public void setSalario(Double salario) {
		this.salario = salario ;
	}
	

	//----------Metodos----------

	@Override
	public String toString(){
		return "Nome: " + nome;
	}

}
