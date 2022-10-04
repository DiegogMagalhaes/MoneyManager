package br.com.diegogabriel.moneymanager.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.diegogabriel.moneymanager.modelo.Particao;
import br.com.diegogabriel.moneymanager.modelo.Usuario;




/**
 * Uma classe que interage com o banco de dados, operando na table Particao.
 * 
 * @author Diego Gabirel
 * @version 1.0
 */

public class ParticaoDAO {
	
	/**
	 * Insere uma Particao na table Particao do banco de dados.
	 * 
	 * @param particao Se refere ao Particao no qual sera salva no banco de dados.
	 * @throws SQLException 
	 */
	
	public void inserir(Particao particao, EntityManager em) throws SQLException {		
		em.persist(particao);	
	}
	
	
	public void atualizar(EntityManager em, Particao particao, Usuario usuario) throws SQLException {
		
		Particao tempparticao =  em.merge(particao);
		tempparticao = particao;
		
	}
	
	
	/**
	 * Retorna um Set de partiçoes presentes na tabela Particao do banco de dados.
	 * 
	 * @return Um Set de partiçoes presentes na tabela Particao
	 * @throws SQLException Lança uma exceção na pilha quando ocorrer algum erro referente ao sql
	 */
	public List<Particao> getParticao(EntityManager em, Usuario usuario) throws SQLException{
		
			String jpql = "Select p from Particao p WHERE p.usuario.id = :uid";
			return em.createQuery(jpql,Particao.class).setParameter("uid",usuario.getId()).getResultList();
	
	}
	
	
	/**
	 * Recebe uma String e retorna um Set de partiçoes com o mesmo nome que essa String
	 * presentes na tabela Particao do banco de dados.
	 * 
	 * @param nome String refrente ao nome da Particao que queremos buscar
	 * @return
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
