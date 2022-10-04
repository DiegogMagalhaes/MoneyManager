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


@Named
@ViewScoped
public class UsuarioBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Usuario usuario;
	private Double valor;
	@Inject
	JDBCComunicador comunicador;
	
	@PostConstruct
	public void init() {
		usuario = usuarioAtual();
	}
	
	/**
	 * @return the valor
	 */
	public Double getValor() {
		return valor;
	}

	/**
	 * @param valor the valor to set
	 */
	public void setValor(Double valor) {
		this.valor = valor;
	}

	public Usuario getUsuario(){
		return usuario;
	}
	
	public String adicionarSaldo() {
		
		Double resultado = usuario.getSaldo() + this.valor;
		Double resultadoSaldoPrevisto = usuario.getSaldoPrevisto() + this.valor;
		comunicador.atualizarSaldo(resultado, usuario);
		comunicador.atualizarSaldoPrevisto(resultadoSaldoPrevisto, usuario);
		return "Menu?faces-redirect=true";
	}
	
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
	
	public Usuario usuarioAtual() {
		FacesContext context = FacesContext.getCurrentInstance();
		Usuario usuarioLogado = (Usuario) context.getExternalContext().getSessionMap().get("usuarioLogado");
		
		if(usuarioLogado != null) {
			return comunicador.buscarUsuario(usuarioLogado.getNome());	
		}
		else return new Usuario();
	}
	
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
	
	public String Logout() {
		FacesContext c = FacesContext.getCurrentInstance();
		c.getExternalContext().getSessionMap().put("usuarioLogado", null);
		
		return "Login?faces-redirect=true";
	}
	
}
