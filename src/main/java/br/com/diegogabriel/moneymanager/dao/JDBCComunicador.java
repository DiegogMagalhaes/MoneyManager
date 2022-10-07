package br.com.diegogabriel.moneymanager.dao;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import br.com.diegogabriel.moneymanager.modelo.Despesa;
import br.com.diegogabriel.moneymanager.modelo.DespesaMensal;
import br.com.diegogabriel.moneymanager.modelo.Gasto;
import br.com.diegogabriel.moneymanager.modelo.Particao;
import br.com.diegogabriel.moneymanager.modelo.Usuario;

/**
 * Classe que faz o intermedio entre as operações dos DAOS e a aplicação
 * 
 * @author Diego Gabirel
 * @version 1.0
 */

@Named
public class JDBCComunicador implements Serializable {

	private static final long serialVersionUID = 8723261314059025760L;
	
	@Inject
	private EntityManager em;
	
	//----------Despsa----------
	
	/**
	 * 
	 * @param  usuario	Usuario no qual possui as despesas que queremos procurar.
	 * @return List<Despesa>
	 */
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
	
	
	/**
	 * @param  nome		Nome da despesa que sera buscada.
	 * @param  usuario	Usuario no qual possui as despesas que queremos procurar.
	 * @return Despesa
	 */
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
	
	
	/**
	 * @param  despesa	Despesa no qual sera paga.
	 */
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
	
	
	//----------DespesaMensal----------
	
	/**
	 * @param dm	Se refere ao DespesaMensal no qual sera salva no banco de dados.
	 */
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

	//----------Gasto----------
	
	/**
	 * @param g 	Gasto no qual sera salvo no banco de dados.
	 */
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
	
	//----------Particao----------

	/**
	 * 
	 * @param  usuario	Usuario no qual possui as partições que queremos procurar.
	 * @return List<Particao>
	 */
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

	
	/**
	 * @param  Nomeparticao 	String refrente ao nome da Partição que queremos buscar
	 * @param  usuario			Usuario no qual possui a partição que queremos procurar.
	 * @return Particao
	 */
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

	
	/** 
	 * @param particao	Particao no qual sera salva no banco de dados.
	 */
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

	
	/**
	 * @param  particao		Particao que sera atualizada
	 * @param  usuario		Usuario no qual possui a partição que queremos atualizar.
	 */
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
	
	//----------Usuario----------
	
	/**
	 * @param usuario	Usuario no qual sera salva no banco de dados.
	 */
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
	
	
	/**
	 * @param  Nomeusuario	String referente ao nome do usuario que sera buscad
	 * @return Usuario
	 */
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


	/**
	 * @param  resultado	Double referente ao novo saldo do usuario 
	 * @param  usuario 		Usuario que sera atualizado
	 */
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

	
	/**
	 * @param  resultado	Double referente ao novo valor do saldoPrevisto
	 * @param  usuario 		Usuario que sera atualizado
	 */
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
