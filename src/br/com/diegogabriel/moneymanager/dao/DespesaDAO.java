package br.com.diegogabriel.moneymanager.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import br.com.diegogabriel.moneymanager.despesas.Despesa;
import br.com.diegogabriel.moneymanager.despesas.DespesaMensal;
import br.com.diegogabriel.moneymanager.despesas.Gasto;

public class DespesaDAO {
	private final Connection con;
	
	public DespesaDAO(Connection con) {
		this.con = con;
	}
	
	
	public Set<Despesa> getDespesa(String usuario) throws SQLException{
		
		Set<Despesa> despesas = new HashSet<>();
		Set<DespesaMensal> despesasMensais = new HashSet<>();
		Set<Gasto> gastos = new HashSet<>();
		
		try{
			DespesaMensalDAO dao = new DespesaMensalDAO(con);
			despesasMensais = dao.getDespesaMensal(usuario);
		}
		catch(Exception ex) {
			System.out.println(ex.getMessage());
		}

		try{
			GastoDAO dao = new GastoDAO(con);
			gastos = dao.getGasto(usuario);
		}
		catch(Exception ex) {
			System.out.println(ex.getMessage());
		}

		for(DespesaMensal d : despesasMensais)
		despesas.add(d);
		
		for(Gasto d : gastos)
		despesas.add(d);
		
		return despesas;
	}
	
	
	public void pagarDespesa(String nome, String usuario) {
		
		String sql = "UPDATE despesamensal SET pago = true WHERE nome =" + "'" + nome + "'"
					+ " AND usuario = " + "'" + usuario + "'";
				
		try(PreparedStatement stmt = con.prepareStatement(sql)){
			stmt.execute();
		}
		catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
		
		sql = "UPDATE gasto SET pago = true WHERE nome =" + "'" + nome + "'";
		
		try(PreparedStatement stmt = con.prepareStatement(sql)){
			stmt.execute();
		}
		catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
		
	}
	
	
	public Set<Despesa> searchDespesaByPago(Boolean pago, String usuario) throws SQLException{
		
		Set<Despesa> despesas = new HashSet<>();
		Set<DespesaMensal> despesasMensais = new HashSet<>();
		Set<Gasto> gastos = new HashSet<>();
		
		try{
			DespesaMensalDAO dao = new DespesaMensalDAO(con);
			despesasMensais = dao.searchDespesaMensalByPago(pago,usuario);
			
		}
		catch(Exception ex) {
			System.out.println(ex.getMessage());
		}

		try{
			GastoDAO dao = new GastoDAO(con);
			gastos = dao.searchGastoByPago(pago,usuario);
			
		}
		catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
		
		for(DespesaMensal m : despesasMensais)
			despesas.add(m);
		
		for(Gasto g : gastos)
			despesas.add(g);
		
		return despesas;
		
	}
	
	
	public Despesa searchDespesaByName(String nome, String usuario) throws SQLException{
		
		
		try{
			DespesaMensalDAO dao = new DespesaMensalDAO(con);
			DespesaMensal despesaMensal = dao.searchDespesaMensalByName(nome,usuario);
			if(despesaMensal != null) return despesaMensal;
		}
		catch(Exception ex) {
			System.out.println(ex.getMessage());
		}

		try{
			GastoDAO dao = new GastoDAO(con);
			Gasto gasto = dao.searchGastoByName(nome,usuario);
			if(gasto != null) return gasto;
		}
		catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
		
		return null;
		
	}
	
	
}
