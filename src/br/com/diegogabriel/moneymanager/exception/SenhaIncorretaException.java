package br.com.diegogabriel.moneymanager.exception;

public class SenhaIncorretaException extends RuntimeException{

	public SenhaIncorretaException() {
        super("Senha incorreta, tente novamente");
    }
 
    public SenhaIncorretaException(Throwable t) {
        super(t);
    }
	
}
