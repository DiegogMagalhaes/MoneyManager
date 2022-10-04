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

import br.com.diegogabriel.moneymanager.despesas.Despesa;
import br.com.diegogabriel.moneymanager.modelo.Usuario;
import br.com.diegogabriel.moneymanager.util.Report;

public class DespesaDAO {
	
	
	public List<Despesa> getDespesa(EntityManager em, Usuario usuario) throws SQLException{
		String jpql = "Select d from Despesa d WHERE d.usuario.id = :uid";
		return em.createQuery(jpql,Despesa.class).setParameter("uid",usuario.getId()).getResultList();
	}
	
	public List<Despesa> getDespesaTeste(EntityManager em) throws SQLException{
		String jpql = "Select d from Despesa d ";
		return em.createQuery(jpql,Despesa.class).getResultList();
	}
	
	public void pagarDespesa(EntityManager em, Despesa despesa) {
		despesa.setPago(true);
		Despesa tempdespesa=  em.merge(despesa);
		tempdespesa= despesa;
	}
	
	
	public List<Despesa> searchDespesaByPago(EntityManager em, Usuario usuario ,Boolean pago) throws SQLException{
		String jpql = "Select d from Despesa d WHERE d.usuario.id = :uid AND d.pago = true";
		return em.createQuery(jpql,Despesa.class).setParameter("uid",usuario.getId()).getResultList();
	}
	
	
	public Despesa searchDespesaByName(EntityManager em, String nome, Usuario usuario) throws SQLException{
		String jpql = "Select d from Despesa d WHERE d.usuario.id = :uid AND d.nome = :dnome";
		
		return em.createQuery(jpql,Despesa.class)
				.setParameter("uid",usuario.getId())
				.setParameter("dnome", nome)
				.getSingleResult();
	
	}
	
	
}
