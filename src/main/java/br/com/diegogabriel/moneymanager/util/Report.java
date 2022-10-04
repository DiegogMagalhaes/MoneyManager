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

@WebServlet("/relatorio.pdf")
public class Report extends HttpServlet implements Serializable {
	
	private static final long serialVersionUID = -448827697779537746L;
	
	public Report() {
		
	}
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		Map params = (Map) request.getSession().getAttribute("params");
		String arquivo = (String) request.getSession().getAttribute("arquivo");
		
		System.out.println("------params "+params);
		System.out.println("------arquivo "+arquivo);
		
		byte[] byteArrayPdf = gerarPDF(params, arquivo);

        response.setContentType("application/pdf");
        response.setContentLength(byteArrayPdf.length);
        response.getOutputStream().write(byteArrayPdf);
	}
	
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

