package br.com.diegogabriel.moneymanager.bean;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 * 
 * Bean onde é feito o processo de comunicação entre o website e a aplicação. 
 * Referente aos metodos de Navegação, onde permite a transitividade entre paginas.
 * 
 * @author Diego Gabriel
 * @version 1.0
 */


@Named
@ViewScoped
public class NavegacaoBean implements Serializable{

	
	public String formAdicionarDespesa() {
		return "AdicionarDespesa?faces-redirect=true";
	}
	
	public String formAdicionarParticao() {
		return "AdicionarParticao?faces-redirect=true";
	}
	
	public String formMenu() {
		return "Menu?faces-redirect=true";
	}
	
	public String formRegistrar() {
		return "Registrar?faces-redirect=true";
	}
	
	public String formMostarDespesa() {
		return "MostrarDespesa?faces-redirect=true";
	}
	
	public String formPagarDespesa() {
		return "PagarDespesa?faces-redirect=true";
	}
	
	public String formAdicionarSaldo() {
		return "AdicionarSaldo?faces-redirect=true";
	}
	
}
