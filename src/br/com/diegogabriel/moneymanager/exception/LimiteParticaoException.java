package br.com.diegogabriel.moneymanager.exception;

public class LimiteParticaoException extends RuntimeException {
	
	public LimiteParticaoException(String messager) {
        super("O limite de gastos dessa partição foi excedida, por favor insira um valor menos que: " + messager);
    }
 
    public LimiteParticaoException(Throwable t) {
        super(t);
    }
	
}
