package br.com.diegogabriel.moneymanager.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.diegogabriel.moneymanager.dao.JDBCComunicador;
import br.com.diegogabriel.moneymanager.modelo.Particao;
import br.com.diegogabriel.moneymanager.modelo.Usuario;


@ViewScoped
@Named

public class ParticaoBean implements Serializable{

	private static final long serialVersionUID = 1L;

	Particao particao = new Particao("",0.0);
	Usuario usuario = indentificarUsuario();
	@Inject
	JDBCComunicador comunicador;
	
	/**
	 * @return the particao
	 */
	public Particao getParticao() {
		return particao;
	}

	/**
	 * @param particao the particao to set
	 */
	public void setParticao(Particao particao) {
		this.particao = particao;
	}
	
	private Usuario indentificarUsuario(){
		FacesContext context = FacesContext.getCurrentInstance();
		Usuario usuarioLogado = (Usuario) context.getExternalContext().getSessionMap().get("usuarioLogado");

		return usuarioLogado;	
	}
	
	public String gravar() {
		particao.setGastoMes(0d);
		particao.setUsuario(usuario);
		comunicador.inserirParticao(particao);
		
		return "Menu?faces-redirect=true";
	}
	

	
}
