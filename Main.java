public class Main {
   public static void main(String[] args) {
    ContaCorrente contaCorrente = new ContaCorrente("Alan", "1234", 10000);
    contaCorrente.getSaldo();
    contaCorrente.sacar(500);
    contaCorrente.getSaldo();
    System.out.println("Saldo da conta corrente: " + contaCorrente.getSaldo());
    }
}