package br.com.diegogabriel.moneymanager.testes;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Set;

import br.com.diegogabriel.moneymanager.conexao.ConnectionPool;
import br.com.diegogabriel.moneymanager.dao.UsuarioDAO;
import br.com.diegogabriel.moneymanager.modelo.Usuario;

public class UsuarioDAOTest {

	public static void main (String[] args) throws SQLException {
		
		Usuario usuario = new Usuario("mio", 3061d);
		
		ConnectionPool pool = new ConnectionPool();
		
		try(Connection con = pool.getConnection()){
			UsuarioDAO dao = new UsuarioDAO(con);
			
			dao.inserir(usuario);
			
			Set<Usuario> usuarios = dao.getUsuarios();
			
			usuarios.forEach(u -> System.out.println(u));
		}
	}
	
	
}
