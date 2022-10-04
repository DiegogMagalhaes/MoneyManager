package br.com.diegogabriel.moneymanager.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.diegogabriel.moneymanager.modelo.Usuario;

/**
 * Uma classe que interage com o banco de dados, operando na table Usuario.
 * 
 * @author Diego Gabirel
 * @version 1.0
 */

public class UsuarioDAO {
	
	public UsuarioDAO() {
		
	}
	
	/**
	 * Insere um Usuario na table Usuario do banco de dados.
	 * 
	 * @param usuario Se refere ao usuario no qual sera salva no banco de dados.
	 * @throws SQLException 
	 */
	
	public void inserir(EntityManager em, Usuario usuario) throws SQLException {
		em.persist(usuario);
	}
	
	public void atualizarSaldo(EntityManager em,Double saldo,Usuario usuario) throws SQLException {		
		usuario.setSaldo(saldo);
		Usuario tempusuario =  em.merge(usuario);
		tempusuario = usuario;
	}
	
	
	public void atualizarSaldoPrevisto(EntityManager em, Double resultado, Usuario usuario) throws SQLException {
		usuario.setSaldoPrevisto(resultado);
		Usuario tempusuario =  em.merge(usuario);
		tempusuario = usuario;
	}
	
	/**
	 * Retorna um Set de usuarios presentes na tabela Usuario do banco de dados.
	 * 
	 * @return Um Set de usuarios presentes na tabela Usuario
	 * @throws SQLException Lança uma exceção na pilha quando ocorrer algum erro referente ao sql
	 */
	public List<Usuario> getUsuarios(EntityManager em) throws SQLException{
		
		String jpql = "Select u from Usuario u";
		return em.createQuery(jpql,Usuario.class).getResultList();

	}
	
	
	public Usuario searchUsuarioByName(EntityManager em, String nome) throws SQLException {
		
		String jpql = "Select u from Usuario u WHERE u.nome = :unome";
		
		Query q = em.createQuery(jpql,Usuario.class).setParameter("unome", nome);
		
		if(q != null) return (Usuario) q.getSingleResult();
		else throw new NullPointerException("Usuario não encontrado");
	}
	
		
		
}
