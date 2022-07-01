package br.com.diegogabriel.moneymanager.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import br.com.diegogabriel.moneymanager.modelo.Particao;




/**
 * Uma classe que interage com o banco de dados, operando na table Particao.
 * 
 * @author Diego Gabirel
 * @version 1.0
 */

public class ParticaoDAO {
	private final Connection con;
	
	public ParticaoDAO(Connection con) {
		this.con = con;
	}
	
	
	/**
	 * Insere uma Particao na table Particao do banco de dados.
	 * 
	 * @param particao Se refere ao Particao no qual sera salva no banco de dados.
	 * @throws SQLException 
	 */
	
	public void inserir(Particao particao, String usuario) throws SQLException {
		
		String sql = "insert into Particao(nome, limite, gastoMes, usuario) values (?,?,?,?)";
		
		try(PreparedStatement stmt = con.prepareStatement(sql)){
			stmt.setString(1, particao.getNome());
			stmt.setDouble(2, particao.getLimite());
			stmt.setDouble(3, particao.getGastoMes());
			stmt.setString(4, usuario);
			stmt.execute();
		}
		
	}
	
	
	public void atualizar(Particao particao, String usuario) throws SQLException {
		
		String sql = "UPDATE PARTICAO\r\n"
					+ "SET\r\n"
					+ "gastoMes = "  +  particao.getGastoMes() + " ,\r\n"
					+ "limite = " + particao.getLimite() + "\r\n"
					+ "WHERE\r\n"
					+ "USUARIO = "+ "'" + usuario + "' AND "
					+ "NOME = "+ "'" + particao.getNome()+ "'";
		
		
		try(PreparedStatement stmt = con.prepareStatement(sql)){
			stmt.execute();
		}
		
	}
	
	
	/**
	 * Retorna um Set de partiçoes presentes na tabela Particao do banco de dados.
	 * 
	 * @return Um Set de partiçoes presentes na tabela Particao
	 * @throws SQLException Lança uma exceção na pilha quando ocorrer algum erro referente ao sql
	 */
	public Set<Particao> getParticao(String usuario) throws SQLException{
		
		Set<Particao> particoes = new HashSet<>();
		String sql = "select * from Particao\r\n"
					+ "Where usuario = " + "'" + usuario + "'";
		
		try(PreparedStatement stmt = con.prepareStatement(sql)){
			stmt.execute();
			resultSetToParticao(stmt, particoes);
		}
		
		
		return particoes;
	}
	
	
	/**
	 * Recebe uma String e retorna um Set de partiçoes com o mesmo nome que essa String
	 * presentes na tabela Particao do banco de dados.
	 * 
	 * @param nome String refrente ao nome da Particao que queremos buscar
	 * @return
	 * @throws SQLException
	 */
	
	public Particao searchParticaoByName(String nome, String usuario) throws SQLException{
		
		Set<Particao> particoes = new HashSet<>();
		String sql = "select * from Particao where nome = " + "'" + nome +"'"
					+ " AND usuario = " + "'" + usuario + "'";
		
		
		try(PreparedStatement stmt = con.prepareStatement(sql)){
			stmt.execute();
			resultSetToParticao(stmt, particoes);
		}
		
		for(Particao p : particoes) return p;
		
		return null;
		
	}
	
	
	/**
	 * Pega um Resultado de um PreparedStatement e os adiciona a um Set de Particao
	 * 
	 * @param stmt PreparedStatement no qual sera retirado o ResultSet
	 * @param particoes Set de Particao no qual sera adicionado as partições provindas do resultado do PreparedStatement
	 * @throws SQLException Lança uma exceção na pilha quando ocorrer algum erro referente ao sql
	 */
	private void resultSetToParticao(PreparedStatement stmt, Set<Particao> particoes) throws SQLException {
		
		try(ResultSet rs = stmt.getResultSet()){
			while(rs.next()) {
				String nome = rs.getString("nome");
				Double gastoMes = rs.getDouble("gastoMes");
				Double limite = rs.getDouble("limite");
				Particao particao = new Particao(nome,limite,gastoMes);
				particoes.add(particao);
			}
		}
		
	}

}
