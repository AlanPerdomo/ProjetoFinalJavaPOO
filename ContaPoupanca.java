

public class ContaPoupanca extends Conta {
    private static final double TAXA_JUROS = 0.005;

    public ContaPoupanca(String numero, Pessoa titular, double saldo) {
        super(numero, titular, saldo);
    }

    @Override
    public void sacar(double valor) {
        if (valor <= saldo) {
            saldo -= valor;
        } else {
            throw new IllegalArgumentException("Saldo insuficiente!");
        }
    }

    public void aplicarJuros() {
        saldo += saldo * TAXA_JUROS;
    }
}
