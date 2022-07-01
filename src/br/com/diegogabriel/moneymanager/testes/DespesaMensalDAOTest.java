package br.com.diegogabriel.moneymanager.testes;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;

import br.com.diegogabriel.moneymanager.conexao.ConnectionPool;
import br.com.diegogabriel.moneymanager.dao.DespesaMensalDAO;
import br.com.diegogabriel.moneymanager.despesas.DespesaMensal;

public class DespesaMensalDAOTest {

	static DespesaMensalDAO dao;
	
	public static void main(String[] args) throws SQLException {
		
		DateTimeFormatter formato = (DateTimeFormatter.ofPattern("dd/MM/yy")); 
		LocalDate data = LocalDate.parse("17/01/07", formato);
		
		DespesaMensal despesaMensal  = new DespesaMensal (145d,"asboneca mugi","mugi fofa",true,data);
		
		ConnectionPool pool = new ConnectionPool();
			
		try(Connection con = pool.getConnection()){
			dao = new DespesaMensalDAO(con);
			
		}

		executarBusca();
		
	}
	
	static private void executarBusca() throws SQLException {
		Set<DespesaMensal> despesasMensais = dao.getDespesaMensal("mugi");
		
		despesasMensais.forEach(u -> System.out.println(u));
	}

}
