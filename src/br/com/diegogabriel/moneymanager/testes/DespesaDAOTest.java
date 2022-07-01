package br.com.diegogabriel.moneymanager.testes;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Set;

import br.com.diegogabriel.moneymanager.conexao.ConnectionPool;
import br.com.diegogabriel.moneymanager.dao.DespesaDAO;
import br.com.diegogabriel.moneymanager.despesas.Despesa;

public class DespesaDAOTest {

	
	public static void main(String[] args) throws SQLException {
		
		
		
		ConnectionPool pool = new ConnectionPool();
			
		try(Connection con = pool.getConnection()){
			DespesaDAO dao = new DespesaDAO(con);
			
			dao.pagarDespesa("yugioh","mugi");
			Set<Despesa> despesas = dao.getDespesa("mugi");
				
			despesas.forEach(u -> System.out.println(u));
		}

	}
	
	
}
