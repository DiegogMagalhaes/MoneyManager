package br.com.diegogabriel.moneymanager.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import br.com.diegogabriel.moneymanager.despesas.DespesaMensal;



/**
 * Uma classe que interage com o banco de dados, operando na table DespesaMensal.
 * 
 * @author Diego Gabirel
 * @version 1.0
 */

public class DespesaMensalDAO {
private final Connection con;
	
	public DespesaMensalDAO(Connection con) {
		this.con = con;
	}
	
	
	/**
	 * Insere uma DespesaMensal na table DespesaMensal do banco de dados.
	 * 
	 * @param despesaMensal Se refere ao DespesaMensal no qual sera salva no banco de dados.
	 * @throws SQLException 
	 */
	
	public void inserir(DespesaMensal despesaMensal) throws SQLException {
		
		String sql = "insert into DespesaMensal(nome, descricao, valor, pago ,datavalidade) values (?,?,?,?,?)";
		
		try(PreparedStatement stmt = con.prepareStatement(sql)){
			stmt.setString(1, despesaMensal.getNome());
			stmt.setString(2, despesaMensal.getDescricao());
			stmt.setDouble(3, despesaMensal.getValor());
			stmt.setBoolean(4, despesaMensal.foiPago());
			stmt.setDate(5, Date.valueOf(despesaMensal.getData()));
			stmt.execute();
		}
		
	}
	
	
	/**
	 * Retorna um Set de despesas mensais presentes na tabela DespesaMensal do banco de dados.
	 * 
	 * @return Um Set de despesas mensais presentes na tabela DespesaMensal
	 * @throws SQLException Lança uma exceção na pilha quando ocorrer algum erro referente ao sql
	 */
	public Set<DespesaMensal> getDespesaMensal() throws SQLException{
		
		Set<DespesaMensal> despesasMensais = new HashSet<>();
		String sql = "select * from DespesaMensal";
		
		try(PreparedStatement stmt = con.prepareStatement(sql)){
			stmt.execute();
			resultSetToDespesaMensal(stmt, despesasMensais);
		}
		
		
		return despesasMensais;
	}
	
	
	/**
	 * Pega um Resultado de um PreparedStatement e os adiciona a um Set de DespesaMensal
	 * 
	 * @param stmt PreparedStatement no qual sera retirado o ResultSet
	 * @param despesasMensais Set de DespesaMensal no qual sera adicionado as despesas mensais provindas do resultado do PreparedStatement
	 * @throws SQLException Lança uma exceção na pilha quando ocorrer algum erro referente ao sql
	 */
	private void resultSetToDespesaMensal(PreparedStatement stmt, Set<DespesaMensal> despesasMensais) throws SQLException {
		
		try(ResultSet rs = stmt.getResultSet()){
			while(rs.next()) {
				String nome = rs.getString("nome");
				String descricao = rs.getString("descricao");
				Double valor = rs.getDouble("valor");
				Boolean pago = rs.getBoolean("pago");
				LocalDate dataValidade = rs.getDate("dataValidade").toLocalDate();
				DespesaMensal despesaMensal = new DespesaMensal(valor,nome,descricao, pago,dataValidade);
				despesasMensais.add(despesaMensal);
			}
		}
		
	}

}
