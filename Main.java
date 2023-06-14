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
        System.out.print("\n Opção: ");

        try {
            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    cadastrarPessoa();
                    break;
                case 2:
                    // listarPessoas();
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } catch (Exception e) {
            System.out.println("Entrada inválida! Digite um número correspondente à opção desejada.");
            scanner.nextLine(); // Limpar o buffer do scanner
        }
    }

    public static void cadastrarPessoa() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n Informe o nome da Pessoa: ");
        String nome = scanner.next();
        System.out.println("\n Informe o cpf da Pessoa: ");
        String cpf = scanner.next();

        DbContext database = new DbContext();

        try {
            database.conectarBanco();
            boolean pessoaExistente = verificarPessoaExistente(database, nome, cpf);
            if (pessoaExistente) {
                mensagemStatus("CPF ja cadastrado!");
            } else {
                boolean statusQuery = database
                        .executarUpdateSql(
                                "INSERT INTO public.pessoa(nome,cpf) VALUES ('" + nome + "', '" + cpf + "')");
                if (statusQuery) {
                    mensagemStatus(" '"+ nome +"' foi cadastrado(a)!");
                }
            }
            database.desconectarBanco();
        } catch (Exception e) {
        }
        start();
    }

    public static boolean verificarPessoaExistente(DbContext database, String nome, String cpf) throws SQLException {
        String query = "SELECT COUNT(*) FROM public.pessoa WHERE cpf = '" + cpf + "'";
        ResultSet resultSet = database.executarQuerySql(query);

        if (resultSet.next()) {
            int count = resultSet.getInt(1);
            return count > 0;
        }

        return false;
    }

    public static void mensagemStatus(String mensagem) {
        System.out.print("\n");
        System.out.print("---------------------");
        System.out.print("\n " + mensagem + " \n");
        System.out.print("---------------------");
        System.out.print("\n");
    };
}

/*
 * 
 * public class Main {
 * public static void main(String[] args) {
 * Map<String, Pessoa> pessoas = new HashMap<>();
 * Map<String, Conta> contas = new HashMap<>();
 * 
 * Scanner scanner = new Scanner(System.in);
 * 
 * boolean executando = true;
 * while (executando) {
 * System.out.println("---------- MENU ----------");
 * System.out.println("1. Criar Pessoa");
 * System.out.println("2. Criar Conta Corrente");
 * System.out.println("3. Criar Conta Poupança");
 * System.out.println("4. Realizar Operação");
 * System.out.println("5. Sair");
 * System.out.print("Escolha uma opção: ");
 * 
 * try {
 * int opcao = scanner.nextInt();
 * scanner.nextLine(); // Limpar o buffer do scanner
 * 
 * switch (opcao) {
 * case 1:
 * System.out.print("Digite o CPF da pessoa: ");
 * String cpf = scanner.nextLine();
 * System.out.print("Digite o nome da pessoa: ");
 * String nome = scanner.nextLine();
 * Pessoa pessoa = new Pessoa(cpf, nome);
 * pessoas.put(cpf, pessoa);
 * System.out.println("Pessoa criada com sucesso!");
 * break;
 * 
 * case 2:
 * System.out.print("Digite o CPF da pessoa titular: ");
 * cpf = scanner.nextLine();
 * pessoa = pessoas.get(cpf);
 * if (pessoa != null) {
 * System.out.print("Digite o número da conta corrente: ");
 * String numeroCC = scanner.nextLine();
 * ContaCorrente cc = new ContaCorrente(numeroCC, pessoa, 0.0);
 * contas.put(numeroCC, cc);
 * System.out.println("Conta corrente criada com sucesso!");
 * } else {
 * System.out.println("CPF não encontrado. Criação de conta corrente cancelada."
 * );
 * }
 * break;
 * 
 * case 3:
 * System.out.print("Digite o CPF da pessoa titular: ");
 * cpf = scanner.nextLine();
 * pessoa = pessoas.get(cpf);
 * if (pessoa != null) {
 * System.out.print("Digite o número da conta poupança: ");
 * String numeroCP = scanner.nextLine();
 * ContaPoupanca cp = new ContaPoupanca(numeroCP, pessoa, 0.0);
 * contas.put(numeroCP, cp);
 * System.out.println("Conta poupança criada com sucesso!");
 * } else {
 * System.out.println("CPF não encontrado. Criação de conta poupança cancelada."
 * );
 * }
 * break;
 * 
 * case 4:
 * System.out.print("Digite o número da conta: ");
 * String numeroConta = scanner.nextLine();
 * Conta conta = contas.get(numeroConta);
 * if (conta != null) {
 * System.out.println("---------- MENU DE OPERAÇÕES ----------");
 * System.out.println("1. Sacar");
 * System.out.println("2. Depositar");
 * System.out.println("3. Consultar Saldo");
 * System.out.print("Escolha uma operação: ");
 * int operacao = scanner.nextInt();
 * scanner.nextLine(); // Limpar o buffer do scanner
 * 
 * switch (operacao) {
 * case 1:
 * System.out.print("Digite o valor a ser sacado: ");
 * double valorSaque = scanner.nextDouble();
 * scanner.nextLine(); // Limpar o buffer do scanner
 * try {
 * conta.sacar(valorSaque);
 * System.out.println("Saque realizado com sucesso!");
 * } catch (IllegalArgumentException e) {
 * System.out.println(e.getMessage());
 * }
 * break;
 * 
 * case 2:
 * System.out.print("Digite o valor a ser depositado: ");
 * double valorDeposito = scanner.nextDouble();
 * scanner.nextLine(); // Limpar o buffer do scanner
 * conta.depositar(valorDeposito);
 * System.out.println("Depósito realizado com sucesso!");
 * break;
 * 
 * case 3:
 * double saldo = conta.consultarSaldo();
 * System.out.println("Saldo da conta: " + saldo);
 * break;
 * 
 * default:
 * System.out.println("Operação inválida!");
 * }
 * } else {
 * System.out.println("Conta não encontrada!");
 * }
 * break;
 * 
 * case 5:
 * executando = false;
 * break;
 * 
 * default:
 * System.out.println("Opção inválida!");
 * }
 * } catch (Exception e) {
 * System.out.
 * println("Entrada inválida! Digite um número correspondente à opção desejada."
 * );
 * scanner.nextLine(); // Limpar o buffer do scanner
 * }
 * }
 * }
 * }
 */