package br.com.diegogabriel.moneymanager.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.diegogabriel.moneymanager.dao.JDBCComunicador;
import br.com.diegogabriel.moneymanager.modelo.Usuario;

/**
 * 
 * Bean onde é feito o processo de comunicação entre o website e a aplicação. 
 * Referente a manipulação e entre outros metodos referentes ao Usuario.
 * 
 * @author Diego Gabriel
 * @version 1.0
 */


@Named
@ViewScoped
public class UsuarioBean implements Serializable{

	
	private static final long serialVersionUID = 1L;
	private Usuario usuario;
	private Double valor;
	
	@Inject
	JDBCComunicador comunicador;
	
	//----------Iniciador----------
	
	@PostConstruct
	public void init() {
		usuario = usuarioAtual();
	}
	
	//----------Getters and Setters----------
	
	public Double getValor() {
		return valor;
	}

	public Usuario getUsuario(){
		return usuario;
	}

	
	
	
	public void setValor(Double valor) {
		this.valor = valor;
	}

	//----------Metodos----------
	
	/**
	 * Adiciona Saldo ao usuario atual
	 * @return String
	 */
	public String adicionarSaldo() {
		
		Double resultado = usuario.getSaldo() + this.valor;
		Double resultadoSaldoPrevisto = usuario.getSaldoPrevisto() + this.valor;
		comunicador.atualizarSaldo(resultado, usuario);
		comunicador.atualizarSaldoPrevisto(resultadoSaldoPrevisto, usuario);
		return "Menu?faces-redirect=true";
	}
	
	
	/**
	 * Persiste o novo Usuario no banco de dados
	 * @return String
	 */
	public String Registrar() {
		
		if(comunicador.buscarUsuario(usuario.getNome()) != null) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Ja existe um usuario com o nome " + usuario.getNome() ));
			return "erro";
		}
			
		
		usuario.setSaldo(usuario.getSalario());
		usuario.setSaldoPrevisto(usuario.getSalario());
			
		comunicador.inserirUsuario(usuario);		
		
		return "Login?faces-redirect=true";
		
	}
	
	
	/**
	 * Identifica e retorna o usuario atual logado.
	 * @return Usuario
	 */
	public Usuario usuarioAtual() {
		FacesContext context = FacesContext.getCurrentInstance();
		Usuario usuarioLogado = (Usuario) context.getExternalContext().getSessionMap().get("usuarioLogado");
		
		if(usuarioLogado != null) {
			return comunicador.buscarUsuario(usuarioLogado.getNome());	
		}
		else return new Usuario();
	}
	
	
	/**
	 * Loga um usuario
	 * @return String
	 */
	public String Logar() {
		
		Usuario usuarioRequisitado = comunicador.buscarUsuario(usuario.getNome());
		
		if(usuarioRequisitado == null) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Senha ou Usuario incorretos"));
			return "error";
		}
		
		if(usuario.getSenha().equals(usuarioRequisitado.getSenha())) {
			
			Usuario usuarioLogado = usuarioRequisitado;
			
			FacesContext context = FacesContext.getCurrentInstance();
			context.getExternalContext().getSessionMap().put("usuarioLogado", usuarioLogado);

			return "Menu?faces-redirect=true";
		}
		
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Senha ou Usuario incorretos"));
		return "error";
	}
	
	
	/**
	 * Desloga o usuario atualmente logado.
	 * @return String
	 */
	public String Logout() {
		FacesContext c = FacesContext.getCurrentInstance();
		c.getExternalContext().getSessionMap().put("usuarioLogado", null);
		
		return "Login?faces-redirect=true";
	}
	
}
