package classes;
public abstract class Conta {
    public String numeroConta;
    public Pessoa nome;
    public double saldo;

    public Conta(String numeroConta, Pessoa nome, double saldo) {
        this.numeroConta = numeroConta;
        this.nome = nome;
        this.saldo = saldo;
    }

    public abstract void sacar(double valor);

    public void depositar(double valor) {
        saldo += valor;
    }

    public double consultarSaldo() {
        return saldo;
    }
}
