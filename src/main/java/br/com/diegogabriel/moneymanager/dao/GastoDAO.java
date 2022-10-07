package br.com.diegogabriel.moneymanager.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;

import br.com.diegogabriel.moneymanager.modelo.DespesaMensal;
import br.com.diegogabriel.moneymanager.modelo.Gasto;
import br.com.diegogabriel.moneymanager.modelo.Usuario;




/**
 * DAO da classe Gasto
 * 
 * @author Diego Gabirel
 * @version 2.0
 */

public class GastoDAO{


	/**
	 * @param  em		EntityMnanager que realizara a comunicação com o banco de dados.
	 * @param  gasto 	Gasto no qual sera salvo no banco de dados.
	 * @throws SQLException 
	 */
	public void inserir(EntityManager em, Gasto gasto) throws SQLException {		
		em.persist(gasto);
		
	}
	
	
	/**	 
	 * @param  em		EntityMnanager que realizara a comunicação com o banco de dados.
	 * @param  usuario	Usuario no qual possui as despesas que queremos procurar.
	 * @return List<Gasto>
	 * @throws SQLException 
	 */
	public List<Gasto> getGasto(EntityManager em,Usuario usuario) throws SQLException{
		String jpql = "Select d from Despesa d WHERE d.usuario.id = :uid AND d.dtype == 'Gasto'";
		return em.createQuery(jpql,Gasto.class).setParameter("uid",usuario.getId()).getResultList();
	
	} 
	
	
	/**
	 * @param  em		EntityMnanager que realizara a comunicação com o banco de dados.
	 * @param  pago 	Boolean na qual filtrara as despesas por pago ou não pago.
	 * @param  usuario	Usuario no qual possui as despesas que queremos procurar.
	 * @return List<Gasto>
	 * @throws SQLException
	 */
	public List<Gasto> searchGastoByPago(EntityManager em,Boolean pago, Usuario usuario) throws SQLException{

		String jpql = "Select d from Despesa d WHERE d.usuario.id = :uid AND d.pago = true AND d.dtype == 'Gasto'";
		return em.createQuery(jpql,Gasto.class).setParameter("uid",usuario.getId()).getResultList();
	
		
	}
	
	
	/**
	 * @param  em		EntityMnanager que realizara a comunicação com o banco de dados.
	 * @param  nome 	String refrente ao nome do gasto que queremos buscar.
	 * @param  usuario	Usuario no qual possui as despesas que queremos procurar. 
	 * @return Gasto
	 * @throws SQLException
	 */
	public Gasto searchGastoByName(EntityManager em,String nome, Usuario usuario) throws SQLException{
		
		String jpql = "Select d from Despesa d WHERE d.usuario.id = :uid AND d.nome = :dnome AND d.dtype == 'Gasto'";
		
		return em.createQuery(jpql,Gasto.class)
				.setParameter("uid",usuario.getId())
				.setParameter("dnome", nome)
				.getSingleResult();
		
	}
	
}
