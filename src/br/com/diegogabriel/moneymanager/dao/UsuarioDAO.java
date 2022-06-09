package br.com.diegogabriel.moneymanager.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import br.com.diegogabriel.moneymanager.modelo.Usuario;

/**
 * Uma classe que interage com o banco de dados, operando na table Usuario.
 * 
 * @author Diego Gabirel
 * @version 1.0
 */

public class UsuarioDAO {
	
	private final Connection con;
	
	public UsuarioDAO(Connection con) {
		this.con = con;
	}
	
	
	/**
	 * Insere um Usuario na table Usuario do banco de dados.
	 * 
	 * @param usuario Se refere ao usuario no qual sera salva no banco de dados.
	 * @throws SQLException 
	 */
	
	public void inserir(Usuario usuario) throws SQLException {
		
		String sql = "insert into Usuario (nome, salario) values (?,?)";
		
		try(PreparedStatement stmt = con.prepareStatement(sql)){
			stmt.setString(1, usuario.getNome());
			stmt.setDouble(2, usuario.getSalario());
			stmt.execute();
		}
		
	}
	
	
	/**
	 * Retorna um Set de usuarios presentes na tabela Usuario do banco de dados.
	 * 
	 * @return Um Set de usuarios presentes na tabela Usuario
	 * @throws SQLException Lança uma exceção na pilha quando ocorrer algum erro referente ao sql
	 */
	public Set<Usuario> getUsuarios() throws SQLException{
		
		Set<Usuario> usuarios = new HashSet<>();
		String sql = "select * from Usuario";
		
		try(PreparedStatement stmt = con.prepareStatement(sql)){
			stmt.execute();
			resultSetToUsuario(stmt, usuarios);
		}
		
		
		return usuarios;
	}
	
	
	/**
	 * Pega um Resultado de um PreparedStatement e os adiciona a um Set de Usuario
	 * 
	 * @param stmt PreparedStatement no qual sera retirado o ResultSet
	 * @param usuarios Set de Usuario no qual sera adicionado os usuarios provindos do resultado do PreparedStatement
	 * @throws SQLException Lança uma exceção na pilha quando ocorrer algum erro referente ao sql
	 */
	private void resultSetToUsuario(PreparedStatement stmt, Set<Usuario> usuarios) throws SQLException {
		
		try(ResultSet rs = stmt.getResultSet()){
			while(rs.next()) {
				String nome = rs.getString("nome");
				Double salario = rs.getDouble("salario");
				Usuario usuario = new Usuario(nome,salario);
				usuarios.add(usuario);
			}
		}
		
	}
	
}
