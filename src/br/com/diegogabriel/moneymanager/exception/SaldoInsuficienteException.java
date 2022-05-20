package br.com.diegogabriel.moneymanager.exception;

public class SaldoInsuficienteException extends RuntimeException{

	public SaldoInsuficienteException(String message) {
        super("Saldo insuficiente para realizar esse pagamento, Adicição de " + message + "R$ sera necessaria");
    }
 
    public SaldoInsuficienteException(Throwable t) {
        super(t);
    }
	
	
}
