package br.com.diegogabriel.moneymanager.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.diegogabriel.moneymanager.modelo.Usuario;

/**
 * DAO da classe Usuario
 * 
 * @author Diego Gabirel
 * @version 1.0
 */

public class UsuarioDAO {
	
	
	/**
	 * @param  em			EntityMananager que realizara a comunicação com o banco de dados.
	 * @param  usuario 		Usuario no qual sera salva no banco de dados.
	 * @throws SQLException 
	 */
	public void inserir(EntityManager em, Usuario usuario) throws SQLException {
		em.persist(usuario);
	}
	
	
	/**
	 * @param  em			EntityMananager que realizara a comunicação com o banco de dados.
	 * @param  saldo 		Double referente ao novo saldo do usuario 
	 * @param  usuario 		Usuario que sera atualizado
	 * @throws SQLException
	 */
	public void atualizarSaldo(EntityManager em,Double saldo,Usuario usuario) throws SQLException {		
		usuario.setSaldo(saldo);
		Usuario tempusuario =  em.merge(usuario);
		tempusuario = usuario;
	}
	
	
	/**
	 * @param  em			EntityMananager que realizara a comunicação com o banco de dados.
	 * @param  resultado	Double referente ao novo valor do saldoPrevisto
	 * @param  usuario 		Usuario que sera atualizado
	 * @throws SQLException
	 */
	public void atualizarSaldoPrevisto(EntityManager em, Double resultado, Usuario usuario) throws SQLException {
		usuario.setSaldoPrevisto(resultado);
		Usuario tempusuario =  em.merge(usuario);
		tempusuario = usuario;
	}
	
	
	/**
	 * @param  em	EntityMananager que realizara a comunicação com o banco de dados.
	 * @return List<Usuario>
	 * @throws SQLException 
	 */
	public List<Usuario> getUsuarios(EntityManager em) throws SQLException{
		
		String jpql = "Select u from Usuario u";
		return em.createQuery(jpql,Usuario.class).getResultList();

	}
	
	
	/**
	 * @param  em			EntityMananager que realizara a comunicação com o banco de dados.
	 * @param  nome			String referente ao nome do usuario que sera buscado
	 * @return Usuario
	 * @throws SQLException
	 */
	public Usuario searchUsuarioByName(EntityManager em, String nome) throws SQLException {
		
		String jpql = "Select u from Usuario u WHERE u.nome = :unome";
		
		Query q = em.createQuery(jpql,Usuario.class).setParameter("unome", nome);
		
		if(q != null) return (Usuario) q.getSingleResult();
		else throw new NullPointerException("Usuario não encontrado");
	}
	
		
		
}
