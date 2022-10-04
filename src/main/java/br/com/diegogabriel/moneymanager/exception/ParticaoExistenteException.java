package br.com.diegogabriel.moneymanager.exception;

/**
 * Exeção criada para casos onde o usuario esta tentando criar uma nova partição com o mesmo nome que uma partição anterior.
 * 
 * @author Diego Gabriel
 * @version 1.0
 */

public class ParticaoExistenteException extends RuntimeException {
	
	public ParticaoExistenteException() {
        super("Ja existe uma partição com o mesmo nome.");
    }
 
    public ParticaoExistenteException(Throwable t) {
        super(t);
    }
	
}
