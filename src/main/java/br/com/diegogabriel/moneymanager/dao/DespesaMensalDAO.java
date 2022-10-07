package br.com.diegogabriel.moneymanager.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;

import br.com.diegogabriel.moneymanager.modelo.Despesa;
import br.com.diegogabriel.moneymanager.modelo.DespesaMensal;
import br.com.diegogabriel.moneymanager.modelo.Usuario;



/**
 * DAO da classe DespesaMensal
 * 
 * @author Diego Gabirel
 * @version 2.0
 */

public class DespesaMensalDAO{
	

	/**
	 * @param  em				EntityMnanager que realizara a comunicação com o banco de dados.
	 * @param  despesaMensal 	Se refere ao DespesaMensal no qual sera salva no banco de dados.
	 * @throws SQLException 
	 */
	public void inserir( EntityManager em, DespesaMensal despesaMensal) throws SQLException {
		em.persist(despesaMensal);
	}
	
	
	/**
	 * @param  em		EntityMnanager que realizara a comunicação com o banco de dados.
	 * @param  usuario	Usuario no qual possui as despesas que queremos procurar.
	 * @return List<DespesaMensal>
	 * @throws SQLException
	 */
	public List<DespesaMensal> getDespesaMensal( EntityManager em, Usuario usuario) throws SQLException{
		String jpql = "Select d from Despesa d WHERE d.usuario.id = :uid AND d.dtype == 'DespesaMensal'";
		return em.createQuery(jpql,DespesaMensal.class).setParameter("uid",usuario.getId()).getResultList();
		
	}
	
	
	/**
	 * @param  em		EntityMnanager que realizara a comunicação com o banco de dados.
	 * @param  nome 	String refrente ao nome da despesa mensal que queremos buscar.
	 * @param  usuario	Usuario no qual possui as despesas que queremos procurar.
	 * @return DespesaMensal
	 * @throws SQLException
	 */
	public DespesaMensal searchDespesaMensalByName(EntityManager em, String nome, Usuario usuario) throws SQLException{
		String jpql = "Select d from Despesa d WHERE d.usuario.id = :uid AND d.nome = :dnome AND d.dtype == 'DespesaMensal'";
		
		return em.createQuery(jpql,DespesaMensal.class)
				.setParameter("uid",usuario.getId())
				.setParameter("dnome", nome)
				.getSingleResult();
	
	
	}
	
	
	/**
	 * @param  em		EntityMnanager que realizara a comunicação com o banco de dados.
	 * @param  pago 	Boolean na qual filtrara as despesas por pago ou não pago.
	 * @param  usuario	Usuario no qual possui as despesas que queremos procurar.
	 * @return List<DespesaMensal>
	 * @throws SQLException
	 */
	public List<DespesaMensal> searchDespesaMensalByPago(EntityManager em, Boolean pago, Usuario usuario) throws SQLException{
		String jpql = "Select d from Despesa d WHERE d.usuario.id = :uid AND d.pago = true AND d.dtype == 'DespesaMensal'";
		return em.createQuery(jpql,DespesaMensal.class).setParameter("uid",usuario.getId()).getResultList();
	}

}
