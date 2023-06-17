import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import Data.DbContext;


public class Main {
    public static void main(String[] args) {
        System.out.println("---------------------");
        System.out.println("----- BEM VINDO -----");
        start();
    }



    public static void start() {

        Scanner scanner = new Scanner(System.in);

        System.out.println("---------------------");
        System.out.println("\n Selecionoe uma opção: \n");
        System.out.println("1 - Cadastrar Pessoa");
        System.out.println("2 - Listar Pessoas");
        System.out.println("3 - Criar Conta");
        System.out.println("4 - Listar Contas");
        System.out.println("5 - Depositar");
        System.out.println("6 - Sacar");


        System.out.print("\n Opção: ");

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
                    Conta.depositar();
                default:
                    Conta.sacar();
                    System.out.println("Opção inválida!");
            }
        } catch (Exception e) {
            System.out.println("Entrada inválida! Digite um número correspondente à opção desejada.");
            scanner.nextLine(); // Limpar o buffer do scanner
        }
    }
    public static void mensagemStatus(String mensagem) {
        System.out.print("\n");
        System.out.print("---------------------");
        System.out.print("\n " + mensagem + " \n");
    }
}