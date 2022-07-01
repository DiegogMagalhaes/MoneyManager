package br.com.diegogabriel.moneymanager.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import br.com.diegogabriel.moneymanager.despesas.DespesaMensal;
import br.com.diegogabriel.moneymanager.despesas.Gasto;
import br.com.diegogabriel.moneymanager.modelo.Particao;




/**
 * Uma classe que interage com o banco de dados, operando na table Gasto.
 * 
 * @author Diego Gabirel
 * @version 1.0
 */

public class GastoDAO{
	
	protected final Connection con;
	
	public GastoDAO(Connection con) {
		this.con = con;
	}

	/**
	 * Insere um Gasto na table Gasto do banco de dados.
	 * 
	 * @param gasto Se refere ao Gasto no qual sera salva no banco de dados.
	 * @throws SQLException 
	 */
	
	public void inserir(Gasto gasto, String usuario) throws SQLException {
		
		String sql = "insert into Gasto(nome, descricao, valor, pago, particaonome,usuario) values (?,?,?,?,?,?)";
		
		try(PreparedStatement stmt = con.prepareStatement(sql)){
			stmt.setString(1, gasto.getNome());
			stmt.setString(2, gasto.getDescricao());
			stmt.setDouble(3, gasto.getValor());
			stmt.setBoolean(4, gasto.foiPago());
			
			if(gasto.getParticao() != null)stmt.setString(5, gasto.getParticao().getNome());
			else stmt.setString(5, null);
			
			stmt.setString(6, usuario);
			
			stmt.execute();
		}
		
	}
	
	
	/**
	 * Retorna um Set de gastos presentes na tabela Gasto do banco de dados.
	 * 
	 * @return Um Set de gastos presentes na tabela Gasto
	 * @throws SQLException Lança uma exceção na pilha quando ocorrer algum erro referente ao sql
	 */
	public Set<Gasto> getGasto(String usuario) throws SQLException{
		
		Set<Gasto> gastos = new HashSet<>();
		String sql = "select * from Gasto Left Join PARTICAO\r\n"
					+ "ON Gasto.particaoNome = PARTICAO.nome"
					+ " WHERE usuario = " + "'" + usuario + "'";
		
		try(PreparedStatement stmt = con.prepareStatement(sql)){
			stmt.execute();
			resultSetToGasto(stmt, gastos);
		}
		
		
		return gastos;
	} 
	
	
	/**
	 * Recebe um Booleano e retorna um Set de Gasto onde todos seus elementos são pagos ou não, de acordo com o valor da entrada
	 * 
	 * @param pago Boolean refrente ao elemento pago de gasto
	 * @return Retorna um Set de Gasto com o valor de pago equivalente ao valor de pago recebido
	 * @throws SQLException
	 */
	
	public Set<Gasto> searchGastoByPago(Boolean pago, String usuario) throws SQLException{
		
		Set<Gasto> gastos = new HashSet<>();
		String sql = "select * from Gasto Left Join PARTICAO\r\n"
					+ "ON Gasto.particaoNome = PARTICAO.nome"
					+ " where pago = " + pago 
					+ " AND usuario = " + "'" + usuario + "'";
				
		
		try(PreparedStatement stmt = con.prepareStatement(sql)){
			stmt.execute();
			resultSetToGasto(stmt, gastos);
		}
		
		return gastos;
		
	}
	
	
	/**
	 * Recebe uma String e retorna um Set de Gasto com o mesmo nome que essa String
	 * presentes na tabela Gasto do banco de dados.
	 * 
	 * @param nome String refrente ao nome do gasto que queremos buscar
	 * @return Retorna um Gasto com o mesmo nome recebido
	 * @throws SQLException
	 */
	
	public Gasto searchGastoByName(String nome, String usuario) throws SQLException{
		
		Set<Gasto> gastos = new HashSet<>();
		String sql = "select * from Gasto Left Join PARTICAO\r\n"
					+ "ON Gasto.particaoNome = PARTICAO.nome"
					+ " where Gasto.nome = " + "'" + nome +"'"
					+ " AND usuario = " + "'" + usuario + "'";
				
		
		try(PreparedStatement stmt = con.prepareStatement(sql)){
			stmt.execute();
			resultSetToGasto(stmt, gastos);
		}
		
		for(Gasto g : gastos) return g;
		
		return null;
		
	}
	
	
	/**
	 * Pega um Resultado de um PreparedStatement e os adiciona a um Set de Gasto
	 * 
	 * @param stmt PreparedStatement no qual sera retirado o ResultSet
	 * @param gastos Set de Gasto no qual sera adicionado os gastos provindas do resultado do PreparedStatement
	 * @throws SQLException Lança uma exceção na pilha quando ocorrer algum erro referente ao sql
	 */
	private void resultSetToGasto(PreparedStatement stmt, Set<Gasto> gastos) throws SQLException {
		
		try(ResultSet rs = stmt.getResultSet()){
			while(rs.next()) {
				String nome = rs.getString("nome");
				String descricao = rs.getString("descricao");
				Double valor = rs.getDouble("valor");
				Boolean pago = rs.getBoolean("pago");
				String nome_particao = rs.getString("particaoNome");
				
				Particao p;
				
				if(nome_particao != null) {
					Double gastoMes = rs.getDouble("GastoMes");
					Double limite = rs.getDouble("limite");
					p = new Particao(nome_particao,limite,gastoMes);
				}
				else 
					p = null;
				
				Gasto gasto = new Gasto(valor,nome,descricao, pago,p);
				gastos.add(gasto);
			}
		}
		
	}

	
}
