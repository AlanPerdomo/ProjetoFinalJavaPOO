public class ContaPoupanca extends Conta {
    private static final double TAXA_JUROS = 0.005;

    public ContaPoupanca(int numconta, Pessoa cpf, double saldo) {
        super(numconta, cpf, saldo);
    }

    @Override
    public void sacar(double valor) {
        if (valor <= saldo) {
            saldo -= valor;
        } else {
            throw new IllegalArgumentException("Saldo insuficiente!");
        }
    }

    @Override
    public void depositar(double valor) {
        saldo += valor;
        aplicarJuros();
    }

    public void aplicarJuros() {
        saldo += saldo * TAXA_JUROS;
    }
}
