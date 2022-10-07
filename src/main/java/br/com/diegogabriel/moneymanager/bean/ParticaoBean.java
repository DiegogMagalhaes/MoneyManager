package br.com.diegogabriel.moneymanager.bean;

import java.io.Serializable;
import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.diegogabriel.moneymanager.dao.JDBCComunicador;
import br.com.diegogabriel.moneymanager.modelo.Despesa;
import br.com.diegogabriel.moneymanager.modelo.Particao;
import br.com.diegogabriel.moneymanager.modelo.Usuario;

/**
 * 
 * Bean onde é feito o processo de comunicação entre o website e a aplicação. 
 * Referente a manipulação e entre outros metodos referentes a Particao.
 * 
 * @author Diego Gabriel
 * @version 1.0
 */


@ViewScoped
@Named
public class ParticaoBean implements Serializable{

	private static final long serialVersionUID = 1L;

	Particao particao;
	Usuario usuario;
	
	@Inject
	JDBCComunicador comunicador;
	
	//----------Iniciador----------
	
	@PostConstruct
	public void init() {
		usuario = indentificarUsuario();
		particao = new Particao("",0.0);
	}
	
	//----------Getters and Setters----------
	
	public Particao getParticao() {
		return particao;
	}

	
	
	
	public void setParticao(Particao particao) {
		this.particao = particao;
	}

	//----------Metodos----------
	
	private Usuario indentificarUsuario(){
		FacesContext context = FacesContext.getCurrentInstance();
		Usuario usuarioLogado = (Usuario) context.getExternalContext().getSessionMap().get("usuarioLogado");

		return usuarioLogado;	
	}
	
	/**
	 * Persiste a Particao criada no banco de dados
	 * @return String
	 */
	public String gravar() {
		particao.setGastoMes(0d);
		particao.setUsuario(usuario);
		comunicador.inserirParticao(particao);
		
		return "Menu?faces-redirect=true";
	}
	

	
}
