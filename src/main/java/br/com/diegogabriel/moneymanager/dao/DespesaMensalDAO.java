package br.com.diegogabriel.moneymanager.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;

import br.com.diegogabriel.moneymanager.despesas.Despesa;
import br.com.diegogabriel.moneymanager.despesas.DespesaMensal;
import br.com.diegogabriel.moneymanager.modelo.Usuario;



/**
 * Uma classe que interage com o banco de dados, operando na table DespesaMensal.
 * 
 * @author Diego Gabirel
 * @version 1.0
 */

public class DespesaMensalDAO{
	

	/**
	 * Insere uma DespesaMensal na table DespesaMensal do banco de dados.
	 * 
	 * @param despesaMensal Se refere ao DespesaMensal no qual sera salva no banco de dados.
	 * @throws SQLException 
	 */
	
	public void inserir( EntityManager em, DespesaMensal despesaMensal) throws SQLException {
		em.persist(despesaMensal);
	}
	
	
	/**
	 * Retorna um Set de despesas mensais presentes na tabela DespesaMensal do banco de dados.
	 * 
	 * @return Um Set de despesas mensais presentes na tabela DespesaMensal
	 * @throws SQLException Lança uma exceção na pilha quando ocorrer algum erro referente ao sql
	 */
	public List<DespesaMensal> getDespesaMensal( EntityManager em, Usuario usuario) throws SQLException{
		String jpql = "Select d from Despesa d WHERE d.usuario.id = :uid AND d.dataValidade != null";
		return em.createQuery(jpql,DespesaMensal.class).setParameter("uid",usuario.getId()).getResultList();
		
	}
	
	
	/**
	 * Recebe uma String e retorna um Set de DespesaMensal com o mesmo nome que essa String
	 * presentes na tabela DespesaMensal do banco de dados.
	 * 
	 * @param nome String refrente ao nome da despesa mensal que queremos buscar
	 * @return Retorna uma DespesaMensal com o mesmo nome recebido
	 * @throws SQLException
	 */
	
	public DespesaMensal searchDespesaMensalByName(EntityManager em, String nome, Usuario usuario) throws SQLException{
		String jpql = "Select d from Despesa d WHERE d.usuario.id = :uid AND d.nome = :dnome AND d.dataValidade != null";
		
		return em.createQuery(jpql,DespesaMensal.class)
				.setParameter("uid",usuario.getId())
				.setParameter("dnome", nome)
				.getSingleResult();
	
	
	}
	
	
	/**
	 * Recebe um Booleano e retorna um Set de DespesaMensal onde todos seus elementos são pagos ou não, de acordo com o valor da entrada
	 * 
	 * @param pago Boolean refrente ao elemento pago de despesa mensal 
	 * @return Retorna um Set de DespesaMensal com o valor de pago equivalente ao valor de pago recebido
	 * @throws SQLException
	 */
	
	public List<DespesaMensal> searchDespesaMensalByPago(EntityManager em, Boolean pago, Usuario usuario) throws SQLException{
		String jpql = "Select d from Despesa d WHERE d.usuario.id = :uid AND d.pago = true AND d.dataValidade != null";
		return em.createQuery(jpql,DespesaMensal.class).setParameter("uid",usuario.getId()).getResultList();
	}

}
