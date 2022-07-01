package br.com.diegogabriel.moneymanager.testes;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Set;

import br.com.diegogabriel.moneymanager.conexao.ConnectionPool;
import br.com.diegogabriel.moneymanager.dao.DespesaMensalDAO;
import br.com.diegogabriel.moneymanager.dao.GastoDAO;
import br.com.diegogabriel.moneymanager.despesas.DespesaMensal;
import br.com.diegogabriel.moneymanager.despesas.Gasto;
import br.com.diegogabriel.moneymanager.modelo.Particao;

public class GastoDAOTest {
	
public static void main(String[] args) throws SQLException {
		
		Particao p = null;
		Gasto gasto  = new Gasto (146d,"cartã mugi","mugi fofa",true,p);
		
		ConnectionPool pool = new ConnectionPool();
			
		try(Connection con = pool.getConnection()){
			GastoDAO dao = new GastoDAO(con);
			
			ConnectionPool pool2 = new ConnectionPool();
			
				
			Set<Gasto> gastos = dao.getGasto("mugi");
				
			gastos.forEach(u -> System.out.println(u));
		}

	}
	
	
}
