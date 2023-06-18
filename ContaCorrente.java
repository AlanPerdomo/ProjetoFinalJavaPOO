public class ContaCorrente extends Conta {
    private static final double CHEQUE_ESPECIAL = 500.0;

    public ContaCorrente(int numconta, Pessoa cpf, double saldo) {
        super(numconta, cpf, saldo);
    }

    @Override
    public void sacar(double valor) {
        double saldoTotal = saldo + CHEQUE_ESPECIAL;

        if (valor <= saldoTotal) {
            this.saldo -= valor;
        } else {
            throw new IllegalArgumentException("Saldo insuficiente!");
        }
    }

    @Override
    public void depositar(double valor) {
        this.saldo += valor;
    }
}
