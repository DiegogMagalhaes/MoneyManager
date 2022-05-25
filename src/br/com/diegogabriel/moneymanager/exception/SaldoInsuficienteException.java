package br.com.diegogabriel.moneymanager.exception;

public class SaldoInsuficienteException extends RuntimeException{

	public SaldoInsuficienteException(String message) {
        super("Saldo insuficiente para realizar esse pagamento" + message);
    }
	
	public SaldoInsuficienteException() {
        super("Saldo insuficiente para realizar esse pagamento");
    }
 
 
    public SaldoInsuficienteException(Throwable t) {
        super(t);
    }
	
	
}
