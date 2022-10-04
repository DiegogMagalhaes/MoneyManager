package br.com.diegogabriel.moneymanager.util;

import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import br.com.diegogabriel.moneymanager.modelo.Usuario;

public class Autorizador implements PhaseListener{

	@Override
	public void afterPhase(PhaseEvent event) {
		FacesContext context = event.getFacesContext();
		String nomePagina = context.getViewRoot().getViewId();
		
		if("/Login.xhtml".equals(nomePagina) || "/TesteHibernate.xhtml".equals(nomePagina) || "/Registrar.xhtml".equals(nomePagina)) {
			FacesContext c = FacesContext.getCurrentInstance();
			c.getExternalContext().getSessionMap().put("usuarioLogado", null);
			return;
		}
		
		
		Usuario usuarioLogado = (Usuario) context.getExternalContext().getSessionMap().get("usuarioLogado");
		System.out.println("String usuario: " + usuarioLogado);
		if(usuarioLogado != null) return;
		
		NavigationHandler handler = context.getApplication().getNavigationHandler();
		handler.handleNavigation(context, null, "Login?faces-redirect=true");
	}

	@Override
	public void beforePhase(PhaseEvent event) {

	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}

}
