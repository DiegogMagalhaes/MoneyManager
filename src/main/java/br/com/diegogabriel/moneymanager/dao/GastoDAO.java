package br.com.diegogabriel.moneymanager.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;

import br.com.diegogabriel.moneymanager.despesas.DespesaMensal;
import br.com.diegogabriel.moneymanager.despesas.Gasto;
import br.com.diegogabriel.moneymanager.modelo.Usuario;




/**
 * Uma classe que interage com o banco de dados, operando na table Gasto.
 * 
 * @author Diego Gabirel
 * @version 1.0
 */

public class GastoDAO{


	/**
	 * Insere um Gasto na table Gasto do banco de dados.
	 * 
	 * @param gasto Se refere ao Gasto no qual sera salva no banco de dados.
	 * @throws SQLException 
	 */
	
	public void inserir(EntityManager em, Gasto gasto) throws SQLException {		
		em.persist(gasto);
		
	}
	
	
	/**
	 * Retorna um Set de gastos presentes na tabela Gasto do banco de dados.
	 * 
	 * @return Um Set de gastos presentes na tabela Gasto
	 * @throws SQLException Lança uma exceção na pilha quando ocorrer algum erro referente ao sql
	 */
	public List<Gasto> getGasto(Usuario usuario, EntityManager em) throws SQLException{
		String jpql = "Select d from Despesa d WHERE d.usuario.id = :uid AND d.dataValidade == null";
		return em.createQuery(jpql,Gasto.class).setParameter("uid",usuario.getId()).getResultList();
	
	} 
	
	
	/**
	 * Recebe um Booleano e retorna um Set de Gasto onde todos seus elementos são pagos ou não, de acordo com o valor da entrada
	 * 
	 * @param pago Boolean refrente ao elemento pago de gasto
	 * @return Retorna um Set de Gasto com o valor de pago equivalente ao valor de pago recebido
	 * @throws SQLException
	 */
	
	public List<Gasto> searchGastoByPago(EntityManager em,Boolean pago, Usuario usuario) throws SQLException{

		String jpql = "Select d from Despesa d WHERE d.usuario.id = :uid AND d.pago = true AND d.dataValidade == null";
		return em.createQuery(jpql,Gasto.class).setParameter("uid",usuario.getId()).getResultList();
	
		
	}
	
	
	/**
	 * Recebe uma String e retorna um Set de Gasto com o mesmo nome que essa String
	 * presentes na tabela Gasto do banco de dados.
	 * 
	 * @param nome String refrente ao nome do gasto que queremos buscar
	 * @return Retorna um Gasto com o mesmo nome recebido
	 * @throws SQLException
	 */
	
	public Gasto searchGastoByName(EntityManager em,String nome, Usuario usuario) throws SQLException{
		
		String jpql = "Select d from Despesa d WHERE d.usuario.id = :uid AND d.nome = :dnome AND d.dataValidade == null";
		
		return em.createQuery(jpql,Gasto.class)
				.setParameter("uid",usuario.getId())
				.setParameter("dnome", nome)
				.getSingleResult();
		
	}
	
}
