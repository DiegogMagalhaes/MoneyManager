package br.com.diegogabriel.moneymanager.testes;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Set;

import br.com.diegogabriel.moneymanager.conexao.ConnectionPool;
import br.com.diegogabriel.moneymanager.dao.ParticaoDAO;
import br.com.diegogabriel.moneymanager.modelo.Particao;

public class ParticoesDAOTest {

	public static void main (String[] args) throws SQLException {
			
		Particao particao = new Particao("cardgame", 150d);
			
		ConnectionPool pool = new ConnectionPool();
			
		try(Connection con = pool.getConnection()){
			ParticaoDAO dao = new ParticaoDAO(con);
			
			dao.inserir(particao);
				
			Set<Particao> particoes = dao.getParticao();
				
			particoes.forEach(u -> System.out.println(u));
		}
	}
	
	
}
