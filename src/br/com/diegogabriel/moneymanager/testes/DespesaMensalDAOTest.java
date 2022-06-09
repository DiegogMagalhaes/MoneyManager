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

	public static void main(String[] args) throws SQLException {
		
		DateTimeFormatter formato = (DateTimeFormatter.ofPattern("dd/MM/yy")); 
		LocalDate data = LocalDate.parse("17/01/07", formato);
		
		DespesaMensal despesaMensal  = new DespesaMensal (145d,"asboneca mugi","mugi fofa",true,data);
		
		ConnectionPool pool = new ConnectionPool();
			
		try(Connection con = pool.getConnection()){
			DespesaMensalDAO dao = new DespesaMensalDAO(con);
			
			dao.inserir(despesaMensal);
				
			Set<DespesaMensal> despesasMensais = dao.getDespesaMensal();
				
			despesasMensais.forEach(u -> System.out.println(u));
		}

	}

}
