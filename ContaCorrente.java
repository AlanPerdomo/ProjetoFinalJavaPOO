

public class ContaCorrente extends Conta {
    private static final double CHEQUE_ESPECIAL = 500.0;

    public ContaCorrente(String numero, Pessoa titular, double saldo) {
        super(numero, titular, saldo);
    }

    @Override
    public void sacar(double valor) {
        if (valor <= saldo + CHEQUE_ESPECIAL) {
            saldo -= valor;
        } else {
            throw new IllegalArgumentException("Saldo insuficiente!");
        }
    }
}
