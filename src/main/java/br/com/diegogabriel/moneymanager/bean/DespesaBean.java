package br.com.diegogabriel.moneymanager.bean;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.com.diegogabriel.moneymanager.dao.JDBCComunicador;
import br.com.diegogabriel.moneymanager.exception.LimiteParticaoException;
import br.com.diegogabriel.moneymanager.modelo.Despesa;
import br.com.diegogabriel.moneymanager.modelo.DespesaMensal;
import br.com.diegogabriel.moneymanager.modelo.Gasto;
import br.com.diegogabriel.moneymanager.modelo.Particao;
import br.com.diegogabriel.moneymanager.modelo.Usuario;
import br.com.diegogabriel.moneymanager.util.Report;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 * 
 * Bean onde é feito o processo de comunicação entre o website e a aplicação. 
 * Referente a manipulação e entre outros metodos referentes a Despesa
 * 
 * @author Diego Gabriel
 * @version 1.0
 */


@Named
@ViewScoped
public class DespesaBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Usuario usuario;
	private Despesa despesa;
	
	@Inject
	JDBCComunicador comunicador;
	
	private Gasto gastoSelecionado;
	private DespesaMensal despesamensalSelecionado;
	private String tipoDespesa;
	private String filtrarPor;
	
	private List<Despesa> listaDespesa;
	private List<Despesa> listaDespesaPagar;
 	
	private String radioValue;

	private String data;
	private String nomeParticao;
	
	private Boolean temParticao;
	private List<Particao> particoes;
	
	//----------Iniciador----------
	
	@PostConstruct
	public void init() {
		
		usuario = indentificarUsuario();
		despesa = new Despesa(0.0,"","");
		
		filtrarPor = "";
		radioValue = "";
		nomeParticao = "";
		temParticao = false;
		
		
		listaDespesa = new ArrayList<Despesa>();
		listaDespesaPagar = new ArrayList<Despesa>();
		
		
		gerarPDF();
	}
	
	//----------Getters and Setters----------
	
	public Gasto getGastoSelecionado() {
		return gastoSelecionado;
	}

	public DespesaMensal getDespesamensalSelecionado() {
		return despesamensalSelecionado;
	}

	public String getTipoDespesa() {
		return tipoDespesa;
	}

	public List<Despesa> getListaDespesaPagar() {
		return listaDespesaPagar;
	}
	
	public Double getSaldoPrevisto() {
		return (comunicador.buscarUsuario(usuario.getNome()).getSaldoPrevisto() - despesa.getValor());
	}
	
	public Despesa getDespesa() {
		return despesa;
	}
	
	public String getData() {
		return data;
	}
	
	public Boolean getTemParticao() {
		return temParticao;
	}
	
	public String getNomeParticao() {
		return nomeParticao;
	}
	
	public String getRadioValue() {
		return radioValue;
	}
	
	public 	Double getLimiteParticao() {
		Particao p = buscarParticao();
		return p.getLimite();
	}
	
	public Double getGastoMes() {
		Particao p = buscarParticao();
		return p.getGastoMes();
	}
	
	public Double getRestoLimite() {
		return (getLimiteParticao() - ( getGastoMes() + despesa.getValor() ) );
	}
	
	public Double getLimiteRestante() {
		return (getLimiteParticao() - getGastoMes());
	}
	
	public Double getValorPospago() {
		
		AtualizarUsuario();
		Double valorResultante =  usuario.getSaldo();
		
		if(listaDespesaPagar.size() != 0) {
			for (Despesa despesa : listaDespesaPagar) {
				valorResultante -= despesa.getValor();
			}
		}
		
		return valorResultante;
	}
	
	public List<Particao> getParticoes(){
		return particoes;
	}
	
	public List<Despesa> getListaDespesas(){
		
		
		List<Despesa> list = comunicador.listarDespesas(usuario);
		
			for (Despesa despesa : list) {
				if(!listaDespesa.contains(despesa))listaDespesa.add(despesa);
			}
		
		return listaDespesa;
		
	}
	
	public List<Despesa> getListaDespesasNaoPagas(){
		
		
		List<Despesa> list = comunicador.listarDespesas(usuario);
		List<Despesa> despesasNaoPagas = new ArrayList<Despesa>();
		
		
			for (Despesa despesa : list) {
				if(!despesasNaoPagas.contains(despesa) && despesa.getPago() == false)despesasNaoPagas.add(despesa);
			}
			
			if(listaDespesaPagar.size() != 0) {
				for (Despesa despesa : listaDespesaPagar) {
					despesasNaoPagas.remove(despesa);
				}
			}
			
		return despesasNaoPagas;
		
	}
	
	public String getFiltrarPor() {
		return filtrarPor;
	}

	
	
	
	public void setGastoSelecionado(Gasto gastoSelecionado) {
		this.gastoSelecionado = gastoSelecionado;
	}

	public void setDespesamensalSelecionado(DespesaMensal despesamensalSelecionado) {
		this.despesamensalSelecionado = despesamensalSelecionado;
	}

	public void setTipoDespesa(String tipoDespesa) {
		this.tipoDespesa = tipoDespesa;
	}
	
	public void setListaDespesaPagar() {
		
		FacesContext context = FacesContext.getCurrentInstance();
		Map<String, String> params = context.getExternalContext().getRequestParameterMap();

		String despesaNome = (String)params.get("action");
		Despesa despesa = buscarDespesa(despesaNome);
		
		this.listaDespesaPagar.add(despesa);
	}	
	
	public void setData(String data) {
		this.data = data;
	}
	
	public void setTemParticao(Boolean temParticao) {
		this.temParticao = temParticao;
	}
	
	public void setNomeParticao(String nomeParticao) {
		this.nomeParticao = nomeParticao;
	}
	
	public void setRadioValue(String radioValue){
		this.radioValue = radioValue;
	}
	
	public void setFiltrarPor(String filtrarPor) {
		this.filtrarPor = filtrarPor;
	}
	
	
	//----------Conversores e Validadores----------
	
	public LocalDate converterData() {
		System.out.println("Passou aqui: " + data);
		DateTimeFormatter formato = (DateTimeFormatter.ofPattern("dd/MM/yy")); 
		LocalDate d = LocalDate.parse(data, formato);
		
		return d;
	}
	
	
	public void validatorData(FacesContext fc, UIComponent component, Object value) throws ValidatorException{
		String s = value.toString();

		DateTimeFormatter formato = (DateTimeFormatter.ofPattern("dd/MM/yy")); 
		try {
			LocalDate data = LocalDate.parse(s,formato);
		}
		catch(Exception ex) {
			throw new ValidatorException(new FacesMessage("A data deve ser no formato dd/mm/aa"));
		}
	}
	
	//----------Metodos----------
	
	private Usuario indentificarUsuario(){
		FacesContext context = FacesContext.getCurrentInstance();
		Usuario usuarioLogado = (Usuario) context.getExternalContext().getSessionMap().get("usuarioLogado");

		return usuarioLogado;	
	}
	
	
	private void AtualizarUsuario() {
		usuario = comunicador.buscarUsuario(usuario.getNome());
	}
	
	
	public void verMais() {
		
		System.out.println(tipoDespesa);
		
		FacesContext context = FacesContext.getCurrentInstance();
		Map<String, String> params = context.getExternalContext().getRequestParameterMap();

		String despesaNome = (String)params.get("verMais");
		
		Despesa d = comunicador.buscarDespesa(despesaNome, usuario);
		
		
		Gasto g;
		try{
			g = (Gasto)d;
		}
		catch(Exception ex) {
			g = null;
		}
		
		DespesaMensal dm;
		try{
			dm = (DespesaMensal)d;
		}
		catch(Exception ex) {
			dm = null;
		}
		
		if(dm != null) {
			despesamensalSelecionado = dm;
			tipoDespesa = "despesaMensal";
		}
		else {
			gastoSelecionado = g;
			tipoDespesa = "gasto";
			nomeParticao = gastoSelecionado.getNome();
		}

	}
	
	public String pagarDespesa() {
		
		Double resultado = getValorPospago();
		
		if(resultado >= 0) {
			comunicador.atualizarSaldo(resultado,usuario);
			
			for (Despesa despesa : listaDespesaPagar) {
				comunicador.pagarDespesa(despesa);
			}
			
		}
		
		return "Menu?faces-redirect=true";
		
	}
	
	
	public void iniciarParticoes() {
		particoes = comunicador.listarParticao(usuario);
		
		for (Particao particao : particoes) {
			nomeParticao = particao.getNome();
			break;
		}
	}
	
	
	public Particao buscarParticao() {
		return comunicador.buscarParticao(nomeParticao,usuario);
	}

	
	private Despesa buscarDespesa(String nome) {
		return comunicador.buscarDespesa(nome,usuario);
	}

	
	/**
	 * Inicia os parametros necessarios para o jasperReport produzir o PDF e logo em seguida ja gera o PDF em memoria.
	 */
	public void gerarPDF(){
		
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        ServletContext context = (ServletContext) externalContext.getContext();
      	HttpSession session = (HttpSession) externalContext.getSession(false);        
      	String arquivo = null;    
        
      	switch (filtrarPor) {
		case "Gasto":
			arquivo = context.getRealPath("WEB-INF/report/Gasto.jasper");
			break;
		case "Mensal":
			arquivo = context.getRealPath("WEB-INF/report/DespesaMensal.jasper");
			break;
		default:
			arquivo = context.getRealPath("WEB-INF/report/Despesa.jasper");
			break;
		}
         
        
        Map params = new HashMap<String,Object>();
        
		params.put("USER_ID", usuario.getId());
		
		try {
			session.setAttribute("arquivo", arquivo);
			session.setAttribute("params", params);
		}
		catch(Exception e){
			e.getStackTrace();
		}
		
	}
	
	
	/**
	 * Persiste a Despesa criada no banco de dados
	 * @return String
	 */
	public String gravar(){
		
		if(usuario.getSaldoPrevisto() - despesa.getValor() < 0){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("O valor inserido em despesa ultrapassa o saldo, considerando as despesas pagas e ainda não pagas"));
			return "error";
		}
		
		if(radioValue.equals("mensal")) {
			DespesaMensal dm = new DespesaMensal(despesa.getValor(),despesa.getNome(),despesa.getDescricao(),false,usuario,converterData());
			comunicador.inserirDespesaMensal(dm);
			
		}
		
		else if(radioValue.equals("gasto")) {
			
			Particao p = null;
			
			if(temParticao == true) {
				p = buscarParticao();
				try {
					p.verificarLimite(despesa.getValor());
				}
				catch(LimiteParticaoException ex){
					System.out.println("ex");
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("O falor de gastos da partição não pode ser maior que seu limite"));
					return "error";
				}
				comunicador.atualizarParticao(p, usuario);
			}
			
			Gasto g = new Gasto(despesa.getValor(),despesa.getNome(),despesa.getDescricao(),false,usuario,p);
			comunicador.inserirGasto(g);
			
			
		}
		
		comunicador.atualizarSaldoPrevisto(usuario.getSaldoPrevisto() - despesa.getValor(), usuario);
		
		return "Menu?faces-redirect=true";
		
	}
	

	
}
