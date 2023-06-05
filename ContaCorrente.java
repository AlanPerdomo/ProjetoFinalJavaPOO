public class ContaCorrente extends Conta {
    private double limite = -500;
    public ContaCorrente(String titular, String numeroConta, double saldo) {
        super(titular, numeroConta, saldo);
        this.limite = limite;
    }
    @Override
    public void sacar(double valor){
        if (valor <= (getSaldo()+limite)){
            setSaldo(getSaldo() - valor);
        }
    }
}
