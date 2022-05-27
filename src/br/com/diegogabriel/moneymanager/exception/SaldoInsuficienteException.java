package br.com.diegogabriel.moneymanager.exception;

/**
 * Exceção criada para casos onde o saldo seja insuficiente para pagar algum despesa
 * 
 * @author Diego Gabriel
 * @version 1.0
 */

public class SaldoInsuficienteException extends RuntimeException{


	public SaldoInsuficienteException(String message) {
        super(message);
    }
	
	public SaldoInsuficienteException() {
        super("Saldo insuficiente para realizar esse pagamento");
    }
 
 
    public SaldoInsuficienteException(Throwable t) {
        super(t);
    }
	
	
}
