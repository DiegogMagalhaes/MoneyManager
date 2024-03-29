package br.com.diegogabriel.moneymanager.modelo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.diegogabriel.moneymanager.exception.LimiteParticaoException;

/**
 * Particao é uma classe que representa uma partição de gastos. 
 * Ou seja é uma partição para uma categoria especifica de gastos, nos quais tem um valor limite que pode ser gasto a cada mês.
 * 
 * Por exemplo: se você for um colecionador de cartas, porem quer estipular um valor limite por mês para acabar não extrapolando nos gastos com sua coleção.
 * Você pode criar uma partição para gastos que entram nessa categoria com um limite de 100 reais.
 * Nesse mês sempre que você criar um novo gasto que tenha como partição essa coleção de cartas, você sera impedido de adicionar esse gasto no sistema caso ultrapasse o limite.
 * 
 * @author Diego Gabriel
 * @version 2.0
 */
@Entity
@Table(name = "PARTICAO")
public class Particao implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	private Usuario usuario;
	
	private String nome;
	private Double gastoMes;
	private Double limite;

	
	//----------Construtores----------
	
	/**
	 * Construtor padrão, sem parametros.
	 */
	public Particao() {
		
	}
	
	
	/**
	 * @param nome		String referente ao nome da partição
	 * @param limite	Double referente ao limite da partição
	 */
	public Particao(String nome, Double limite) {
		this.nome = nome;
		this.limite = limite;
		gastoMes = 0d;
	}
	
	
	/**
	 * Construtor de Particao, usado para carregar uma particao ja existente de um banco de dados
	 * 
	 * @param nome		String referente ao nome da partição
	 * @param limite	Double referente ao limite da partição
	 * @param gastoMes	Double referente à quantidade de gastos no mes de uma partição
	 * @param usuario   Usuario responsavel pelo relacionamento entre usuario e particao
	 */
	public Particao(String nome, Double limite, Double gastoMes, Usuario usuario) {
		this.nome = nome;
		this.limite = limite;
		this.gastoMes = gastoMes;
		this.usuario = usuario;
	}
	
	
	//----------Getters and Setters----------
	
	/**
	 * @return valor de id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @return nome da partição
	 */
	public String getNome() {
		return nome;
	}
	
	/**
	 * @return gastoMes da partição
	 */
	public Double getGastoMes() {
		return gastoMes;
	}
	
	/**
	 * @return usuario relacionado a partição
	 */
	public Usuario getUsuario() {
		return usuario;
	}
	
	/**
	 * @return limite da partição
	 */	
	public Double getLimite() {
		return limite;
	}
	
	
	
	/**
	 * @param id Id que indentifica a despesa no banco de dados
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * @param nome Nome que sera atribuido a particao
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	/**
	 * @param gastoMes Quantidade de gastos ao mês relacionado a uma partição
	 */
	public void setGastoMes(Double gastoMes) {
		this.gastoMes = gastoMes;
	}

	/**
	 * @param usuario Usuario que relaciona com essa partição 
	 */
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	/**
	 * @param limite Limite da partição
	 */
	public void setLimite(Double limite) {
		this.limite = limite;
	}


	//----------Metodos----------
	
	/**
	 * Recebe um valor, no qual é verificado se sua soma com os gastos anteriores desse mes extrapola o limite estabelecido. 
	 * Se não extrapolar é adicionado o valor aos gastos do mês.
	 * 
	 * @param valor	Double referente ao valor da conta que esta sendo adicionada a particao.
	 */
	public void verificarLimite(Double valor) {
		Double resultado = valor + gastoMes;
		if(resultado > limite) throw new LimiteParticaoException(String.valueOf(limite - gastoMes));
		setGastoMes(resultado);
	}
	
	
	@Override
	public String toString(){
		return "[nome: " + nome + " | gastoMes: " + gastoMes + " | limite: " + limite + "]";
	}
	
	
	@Override
	public boolean equals(Object obj) {
		Particao outraParticao = (Particao) obj;
		return this.nome.equals(outraParticao.getNome());
	}
	
	
	public boolean equals(String s) {
		return this.nome.equals(s);
	}
	
	
	@Override
	public int hashCode() {
		return nome.hashCode();
	}
	
}
