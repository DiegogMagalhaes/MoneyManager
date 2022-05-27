package br.com.diegogabriel.moneymanager.exception;

/**
 * Exeção criada para casos onde uma Particao recebe um valor que excede seu limite.
 * 
 * @author Diego Gabriel
 * @version 1.0
 */

public class LimiteParticaoException extends RuntimeException {
	
	public LimiteParticaoException(String messager) {
        super("O limite de gastos dessa partição foi excedida, por favor insira um valor menos que: " + messager);
    }
 
    public LimiteParticaoException(Throwable t) {
        super(t);
    }
	
}
