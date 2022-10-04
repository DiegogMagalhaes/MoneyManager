package br.com.diegogabriel.moneymanager.dao;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import br.com.diegogabriel.moneymanager.despesas.Despesa;
import br.com.diegogabriel.moneymanager.despesas.DespesaMensal;
import br.com.diegogabriel.moneymanager.despesas.Gasto;
import br.com.diegogabriel.moneymanager.modelo.Particao;
import br.com.diegogabriel.moneymanager.modelo.Usuario;


@Named
public class JDBCComunicador implements Serializable {

	private static final long serialVersionUID = 8723261314059025760L;
	@Inject
	private EntityManager em;
	
	public List<Despesa> listarDespesas(Usuario usuario){
		DespesaDAO dao = new DespesaDAO();
		
		try 
		{
			return dao.getDespesa(em, usuario);
		}
		catch(Exception ex) {
			System.out.println(ex);
		}
		
		
		return null;
	}
	
	public void inserirDespesaMensal(DespesaMensal dm) {
		DespesaMensalDAO dao = new DespesaMensalDAO();
		
		try 
		{
			em.getTransaction().begin();
			dao.inserir(em,dm);
			em.getTransaction().commit();
		}
		catch(Exception ex) {
			em.getTransaction().rollback();
			System.out.println(ex);
		}
	
	}

	public void inserirGasto(Gasto g) {
		GastoDAO dao = new GastoDAO();
		
		try {
			em.getTransaction().begin();
			dao.inserir(em, g);
			em.getTransaction().commit();
		}
		catch(Exception ex) {
			em.getTransaction().rollback();
			System.out.println(ex);
		}
		
	}

	public List<Particao> listarParticao(Usuario usuario) {
		ParticaoDAO dao = new ParticaoDAO();
		
		try {
			return dao.getParticao(em, usuario);
		}
		catch(Exception ex) {
			System.out.println(ex);
		}
		return null;
	}

	public Particao buscarParticao(String Nomeparticao, Usuario usuario) {
		ParticaoDAO dao = new ParticaoDAO();
		
		try {
		 return dao.searchParticaoByName(em,Nomeparticao,usuario);
		}
		catch(Exception ex) {
			System.out.println(ex);
		}
		
		return null;
	}

	public void inserirUsuario(Usuario usuario) {
		UsuarioDAO dao = new UsuarioDAO ();
		
		try {
			em.getTransaction().begin();
			dao.inserir(em,usuario);
			em.getTransaction().commit();
		}
		catch(Exception ex) {
			em.getTransaction().rollback();
			System.out.println(ex);
		}

		
	}
	
	public Usuario buscarUsuario(String Nomeusuario) {
		UsuarioDAO dao = new UsuarioDAO();
		
		try {
		 return dao.searchUsuarioByName(em,Nomeusuario);
		}
		catch(Exception ex) {
			System.out.println(ex);
		}
		
		return null;
	}

	public Despesa buscarDespesa(String nome, Usuario usuario) {
		DespesaDAO dao = new DespesaDAO();
		
		try {
		 return dao.searchDespesaByName(em,nome,usuario);
		}
		catch(Exception ex) {
			System.out.println(ex);
		}
		
		return null;
	}

	public void atualizarSaldo(Double resultado, Usuario usuario) {
		
		UsuarioDAO dao = new UsuarioDAO();
		
		try {
			em.getTransaction().begin();
			dao.atualizarSaldo(em,resultado,usuario);
			em.getTransaction().commit();
		}
		catch(Exception ex) {
			em.getTransaction().rollback();
			System.out.println(ex);
		}
		
	}

	public void pagarDespesa(Despesa despesa) {
		DespesaDAO dao = new DespesaDAO();
		
		try {
			em.getTransaction().begin();
			dao.pagarDespesa(em, despesa);
			em.getTransaction().commit();
		}
		catch(Exception ex) {
			em.getTransaction().rollback();
			System.out.println(ex);
		}
	}

	public void inserirParticao(Particao particao) {
		ParticaoDAO dao = new ParticaoDAO();
		
		try {
			em.getTransaction().begin();
			dao.inserir(particao, em);
			em.getTransaction().commit();
		}
		catch(Exception ex) {
			em.getTransaction().rollback();
			System.out.println(ex);
		}
	}

	public void atualizarParticao(Particao particao, Usuario usuario) {
		ParticaoDAO dao = new ParticaoDAO();
		
		try {
			em.getTransaction().begin();
			dao.atualizar(em, particao, usuario);
			em.getTransaction().commit();
		}
		catch(Exception ex) {
			em.getTransaction().rollback();
			System.out.println(ex);
		}
	}

	
	public void atualizarSaldoPrevisto(Double resultado, Usuario usuario) {
		UsuarioDAO dao = new UsuarioDAO();
		
		try {
			em.getTransaction().begin();
			dao.atualizarSaldoPrevisto(em,resultado,usuario);
			em.getTransaction().commit();
		}
		catch(Exception ex) {
			em.getTransaction().rollback();
			System.out.println(ex);
		}
	}
	
	
}
