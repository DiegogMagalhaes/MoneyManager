package br.com.diegogabriel.moneymanager.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import br.com.diegogabriel.moneymanager.modelo.MoneyManagerDB;

public class MoneyManagerDAO {

	private final Connection con;
	
	public MoneyManagerDAO(Connection con) {
		this.con = con;
	}
	
	
	/**
	 * Insere um MoneyManagerDB na table MoneyManager do banco de dados.
	 * 
	 * @param MoneyManagerDB Se refere ao MoneyManagerDB no qual sera salva no banco de dados.
	 * @throws SQLException 
	 */
	
	public void inserir(MoneyManagerDB moneyManager, String usuario) throws SQLException {
		
		String sql = "insert into MoneyManager(Usuario,SALDO,SALDOPREVISTO) values (?,?,?)";
		
		try(PreparedStatement stmt = con.prepareStatement(sql)){
			stmt.setString(1, moneyManager.getusuarioNome());
			stmt.setDouble(2, moneyManager.getSaldo());
			stmt.setDouble(3, moneyManager.getSaldoPrevisto());

			stmt.execute();
		}
		
	}
	
	
	public void atualizar(MoneyManagerDB moneyManager, String usuario) throws SQLException {
		
		String sql = "UPDATE MONEYMANAGER\r\n"
					+ "SET\r\n"
					+ "saldo = " + moneyManager.getSaldo() + " ,\r\n"
					+ "saldoprevisto = " + moneyManager.getSaldoPrevisto() + "\r\n"
					+ "WHERE\r\n"
					+ "USUARIO = "+ "'" + usuario + "'";
			
		try(PreparedStatement stmt = con.prepareStatement(sql)){
			
			stmt.execute();
		}
	}
	
	
	/**
	 * Recebe uma String usuario e retorna uma MoneyManagerDB referente a esse usuario
	 * presentes na tabela MoneyManager do banco de dados.
	 * 
	 * @param usuario String refrente ao nome do Usuario no qual possui o MoneyManagerDB que queremos buscar
	 * @return um MoneyManagerDB com o usuario recebido
	 * @throws SQLException
	 */
	
	public MoneyManagerDB getMoneyManager(String usuario) throws SQLException{
		
		Set<MoneyManagerDB> moneyManagerDBs = new HashSet<>();
		String sql = "select * from MoneyManager\r\n"
					+ "WHERE usuario = " + "'" + usuario + "'";
		
		try(PreparedStatement stmt = con.prepareStatement(sql)){
			stmt.execute();
			resultSetToMoneyManagerDB(stmt, moneyManagerDBs);
		}
		
		for(MoneyManagerDB mn : moneyManagerDBs) return mn;
		
		return null;
		
	}
	
	
	/**
	 * Pega um Resultado de um PreparedStatement e os adiciona a um Set de MoneyManagerDB
	 * 
	 * @param stmt PreparedStatement no qual sera retirado o ResultSet
	 * @param MoneyManagerDBs Set de MoneyManagerDB no qual sera adicionado as despesas mensais provindas do resultado do PreparedStatement
	 * @throws SQLException Lança uma exceção na pilha quando ocorrer algum erro referente ao sql
	 */
	private void resultSetToMoneyManagerDB(PreparedStatement stmt, Set<MoneyManagerDB> MoneyManagerDBs) throws SQLException {
		
		try(ResultSet rs = stmt.getResultSet()){
			while(rs.next()) {
				String usuario = rs.getString("USUARIO");
				Double saldo = rs.getDouble("SALDO");
				Double saldoPrevisto = rs.getDouble("SALDOPREVISTO");
				
				MoneyManagerDB moneyManagerDB = new MoneyManagerDB(usuario,saldo,saldoPrevisto);
				MoneyManagerDBs.add(moneyManagerDB);
			}
		}
		
	}

	
	
	
	
}
