package br.com.diegogabriel.moneymanager.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.jdbc.Work;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperRunManager;

/**
 * 
 * WebServlet responsavel pela geração de um pdf atraves dos arquivos .jasper presentes em WEB-INF/report
 * 
 * @author Diego Gabriel
 * @version 1.0
 */

@WebServlet("/relatorio.pdf")
public class Report extends HttpServlet implements Serializable {
	
	private static final long serialVersionUID = -448827697779537746L;
	
	//----------Construtores----------
	
	/**
	 * Construtor padrão, sem parametros
	 */
	public Report() {
		
	}
	
	//----------Metodos----------
	
	/**
	 * Metodo GET do protocolo HTTP. retorna uma arquivo .pdf
	 */
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		Map params = (Map) request.getSession().getAttribute("params");
		String arquivo = (String) request.getSession().getAttribute("arquivo");
		
		byte[] byteArrayPdf = gerarPDF(params, arquivo);

        response.setContentType("application/pdf");
        response.setContentLength(byteArrayPdf.length);
        response.getOutputStream().write(byteArrayPdf);
	}
	
	
	/**
	 * Gera atraves de um arquivo .jasper uma byte array de um arquivo pdf e a retorna.
	 * 
	 * @param parametros 	parametros do formulario
	 * @param nomeArquivo 	nome do arquivo .jasper que sera o templete para o pdf
	 * @return byte[]
	 */
	private byte[] gerarPDF(Map parametros, String nomeArquivo) {
		try {
			   
	        Connection conexao = null;
	        
			 try {
			 Class.forName("org.postgresql.Driver");
			conexao = DriverManager.getConnection("jdbc:postgresql://localhost:5432/moneymanager", "postgres", "postgres");
			} catch (Exception e) {
			//TODO: handle exception
		    }
			
			JasperPrint jasperPrint = JasperFillManager.fillReport(nomeArquivo, parametros,conexao);

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			JasperExportManager.exportReportToPdfStream(jasperPrint, baos);
			return baos.toByteArray();
		
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	
}

