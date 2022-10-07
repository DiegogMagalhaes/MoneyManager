package br.com.diegogabriel.moneymanager.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.diegogabriel.moneymanager.modelo.Particao;
import br.com.diegogabriel.moneymanager.modelo.Usuario;


/**
 * DAO da classe Particao
 * 
 * @author Diego Gabirel
 * @version 1.0
 */

public class ParticaoDAO {
	
	/**
	 * @param  em			EntityMnanager que realizara a comunicação com o banco de dados.
	 * @param  particao 	Particao no qual sera salva no banco de dados.
	 * @throws SQLException 
	 */
	public void inserir(Particao particao, EntityManager em) throws SQLException {		
		em.persist(particao);	
	}
	
	
	/**
	 * @param  em			EntityMananager que realizara a comunicação com o banco de dados.
	 * @param  particao		Particao que sera atualizada.
	 * @param  usuario		Usuario no qual possui a partição que queremos atualizar.
	 * @throws SQLException
	 */
	public void atualizar(EntityManager em, Particao particao, Usuario usuario) throws SQLException {
		
		Particao tempparticao =  em.merge(particao);
		tempparticao = particao;
		
	}
	
	
	/**
	 * @param  em			EntityMananager que realizara a comunicação com o banco de dados.
	 * @param  usuario		Usuario no qual possui as partições que queremos procurar.
	 * @return List<Particao>
	 * @throws SQLException
	 */
	public List<Particao> getParticao(EntityManager em, Usuario usuario) throws SQLException{
		
			String jpql = "Select p from Particao p WHERE p.usuario.id = :uid";
			return em.createQuery(jpql,Particao.class).setParameter("uid",usuario.getId()).getResultList();
	
	}
	
	
	/**
	 * @param  em		EntityMananager que realizara a comunicação com o banco de dados.
	 * @param  nome 	String refrente ao nome da Partição que queremos buscar.
	 * @param  usuario	Usuario no qual possui a partição que queremos procurar.
	 * @return Particao
	 * @throws SQLException
	 */
	public Particao searchParticaoByName(EntityManager em, String nome, Usuario usuario) throws SQLException{
			
		String jpql = "Select p from Particao p WHERE p.usuario.id = :uid AND p.nome = :pnome";
		return em.createQuery(jpql,Particao.class)
				.setParameter("uid",usuario.getId())
				.setParameter("pnome", nome)
				.getSingleResult();
	}
}
