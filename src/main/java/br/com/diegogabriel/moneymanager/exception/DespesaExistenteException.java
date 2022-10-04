package br.com.diegogabriel.moneymanager.exception;

/**
 * Exeção criada para casos onde o usuario esta tentando criar uma nova despesa com o mesmo nome que a anterior.
 * 
 * @author Diego Gabriel
 * @version 1.0
 */

public class DespesaExistenteException extends RuntimeException {
	
	public DespesaExistenteException() {
        super("Ja existe uma despesa com o mesmo nome.");
    }
 
    public DespesaExistenteException(Throwable t) {
        super(t);
    }
	
}
