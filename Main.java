import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("---------------------");
        System.out.println("----- BEM VINDO -----");
        start();
    }

    public static void start() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("---------------------");
        System.out.println("\nSelecione uma opção:\n");
        System.out.println("1 - Cadastrar Pessoa");
        System.out.println("2 - Listar Pessoas");
        System.out.println("3 - Criar Conta");
        System.out.println("4 - Listar Contas");
        System.out.println("5 - Depositar");
        System.out.println("6 - Sacar");
        System.out.println("7 - Saldo");

        System.out.print("\nOpção: ");

        try {
            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    Pessoa.cadastrarPessoa();
                    break;
                case 2:
                    Pessoa.listarPessoas();
                    break;
                case 3:
                    Conta.criarConta();
                    break;
                case 4:
                    Conta.listarContas();
                    break;
                case 5:
                    Conta.fazerDeposito();
                    break;
                case 6:
                    // Conta.sacar();
                    break;
                case 7:
                    // Conta.imprimeSaldo();
                    System.out.println("\nInforme o numero da conta: ");
                    int numconta = scanner.nextInt();
                    double saldoConta = Conta.consultarSaldo(numconta);
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } catch (Exception e) {
            System.out.println("Entrada inválida! Digite um número correspondente à opção desejada.");
            scanner.nextLine(); // Limpar o buffer do scanner
        }
        start();
    }

    public static void mensagemStatus(String mensagem) {
        System.out.println("\n---------------------");
        System.out.println(mensagem);
        System.out.print("---------------------");
    }
}
