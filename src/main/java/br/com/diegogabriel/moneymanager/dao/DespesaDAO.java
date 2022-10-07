package br.com.diegogabriel.moneymanager.dao;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;

import br.com.diegogabriel.moneymanager.modelo.Despesa;
import br.com.diegogabriel.moneymanager.modelo.Usuario;
import br.com.diegogabriel.moneymanager.util.Report;

/**
 * DAO da classe despesa
 * 
 * @author Diego Gabriel
 * @version 2.0
 */
public class DespesaDAO {
	
	
	/**
	 * @param  em		EntityMnanager que realizara a comunicação com o banco de dados.
	 * @param  usuario	Usuario no qual possui as despesas que queremos procurar.
	 * @return List<Despesa>
	 * @throws SQLException
	 */
	public List<Despesa> getDespesa(EntityManager em, Usuario usuario) throws SQLException{
		String jpql = "Select d from Despesa d WHERE d.usuario.id = :uid";
		return em.createQuery(jpql,Despesa.class).setParameter("uid",usuario.getId()).getResultList();
	}
	
	
	/**
	 * @param  em		EntityMnanager que realizara a comunicação com o banco de dados.
	 * @param  despesa	Despesa no qual sera paga.
	 */
	public void pagarDespesa(EntityManager em, Despesa despesa) {
		despesa.setPago(true);
		Despesa tempdespesa=  em.merge(despesa);
		tempdespesa= despesa;
	}
	
	
	/**
	 * @param  em		EntityMnanager que realizara a comunicação com o banco de dados.
	 * @param  usuario	Usuario no qual possui as despesas que queremos procurar.
	 * @param  pago		Boolean na qual filtrara as despesas por pago ou não pago.
	 * @return List<Despesa>
	 * @throws SQLException
	 */
	public List<Despesa> searchDespesaByPago(EntityManager em, Usuario usuario ,Boolean pago) throws SQLException{
		String jpql = "Select d from Despesa d WHERE d.usuario.id = :uid AND d.pago = true";
		return em.createQuery(jpql,Despesa.class).setParameter("uid",usuario.getId()).getResultList();
	}
	
	
	/**
	 * @param  em		EntityMnanager que realizara a comunicação com o banco de dados.
	 * @param  nome		Nome da despesa que sera buscada.
	 * @param  usuario	Usuario no qual possui as despesas que queremos procurar.
	 * @return Despesa
	 * @throws SQLException
	 */
	public Despesa searchDespesaByName(EntityManager em, String nome, Usuario usuario) throws SQLException{
		String jpql = "Select d from Despesa d WHERE d.usuario.id = :uid AND d.nome = :dnome";
		
		return em.createQuery(jpql,Despesa.class)
				.setParameter("uid",usuario.getId())
				.setParameter("dnome", nome)
				.getSingleResult();
	
	}
	
	
}
