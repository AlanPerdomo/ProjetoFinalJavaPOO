import java.sql.ResultSet;
import java.util.Scanner;
import Data.DbContext;

public abstract class Conta {
    public static int numconta;
    public static Pessoa cpf;
    public static double saldo;

    public Conta(int numconta, Pessoa cpf, double saldo) {
        Conta.numconta = numconta;
        Conta.cpf = cpf;
        Conta.saldo = saldo;
    }

    public void sacar(double valor) {
        saldo -= valor;
    };

    public abstract void depositar(double valor);

    public static void fazerDeposito() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\nInforme o numero da conta: ");
        numconta = scanner.nextInt();
        String tipoConta = verificarTipoConta(numconta);
        double valor;

        System.out.println("\nInforme o valor: ");
        valor = scanner.nextDouble();

        try {
            switch (tipoConta) {
                case "contaCorrente":
                    ContaCorrente contaCorrente = new ContaCorrente(numconta, cpf, saldo);
                    contaCorrente.depositar(valor);
                    break;
                case "contaPoupanca":
                    ContaPoupanca contaPoupanca = new ContaPoupanca(numconta, cpf, saldo);
                    contaPoupanca.depositar(valor);
                    break;
                default:
                    System.out.println("Conta inválida!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static double consultarSaldo(int numeroConta) {
        DbContext database = new DbContext();

        try {
            database.conectarBanco();

            ResultSet resultadoConta = database.executarQuerySql(
                    "SELECT saldo FROM public.contas WHERE numconta = " + numeroConta);

            if (resultadoConta.next()) {
                return resultadoConta.getDouble("saldo");
            } else {
                System.out.println("Não foi possível encontrar a conta com o número informado.");
                return 0.0; // ou outro valor indicando um saldo inválido
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0.0; // ou outro valor indicando um saldo inválido
        } finally {
            database.desconectarBanco();
        }
    }

    public static void criarConta() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\nInforme o CPF da Pessoa: ");
        String cpf = scanner.next();
        String tipoConta = "";

        if (!Pessoa.validarCPF(cpf)) {
            Main.mensagemStatus("O CPF informado é inválido!");
            Main.start();
            return;
        }

        DbContext database = new DbContext();

        try {
            database.conectarBanco();
            boolean pessoaExistente = Pessoa.verificarPessoaExistente(database, cpf);
            if (pessoaExistente) {

                System.out.println("---------------------");
                System.out.println("\nSelecione o tipo de conta: \n");
                System.out.println("1 - Conta Corrente");
                System.out.println("2 - Conta Poupança");
                System.out.print("\nOpção: ");

                try {
                    int opcao = scanner.nextInt();
                    scanner.nextLine();

                    switch (opcao) {
                        case 1:
                            tipoConta = "contaCorrente";
                            break;
                        case 2:
                            tipoConta = "contaPoupanca";
                            break;
                        default:
                            System.out.println("Opção inválida!");
                            Main.start();
                            return;
                    }

                    boolean statusQuery = database.executarUpdateSql(
                            "INSERT INTO public.contas(tipoconta,cpf) VALUES ('" + tipoConta + "', '" + cpf + "')");
                    if (statusQuery) {
                        Main.mensagemStatus("'" + tipoConta + "' foi criada!");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Main.start();
                    return;
                }
            } else {
                Main.mensagemStatus("CPF não cadastrado!");
            }
            database.desconectarBanco();
        } catch (Exception e) {
            e.printStackTrace();
            Main.start();
            return;
        }
        Main.start();
    }

    public static void listarContas() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\nInforme o CPF da Pessoa: ");
        String cpf = scanner.next();

        if (!Pessoa.validarCPF(cpf)) {
            Main.mensagemStatus("O CPF informado é inválido!");
            Main.start();
            return;
        }

        DbContext database = new DbContext();

        try {
            database.conectarBanco();

            ResultSet resultadoPessoa = database
                    .executarQuerySql("SELECT * FROM public.pessoas WHERE cpf ='" + cpf + "'");
            if (!resultadoPessoa.next()) {
                System.out.println("Não foi possível encontrar informações da Pessoa com o CPF informado.");
                database.desconectarBanco();
                Main.start();
                return;
            }

            String nomePessoa = resultadoPessoa.getString("nome");

            ResultSet resultadoConsulta = database
                    .executarQuerySql("SELECT * FROM public.contas WHERE cpf ='" + cpf + "'");

            if (!resultadoConsulta.isBeforeFirst()) {
                System.out.println("Não há contas cadastradas para " + nomePessoa + " (CPF: " + cpf + ").");
            } else {
                System.out.println("Contas cadastradas para " + nomePessoa + " (CPF: " + cpf + "):");
                while (resultadoConsulta.next()) {
                    System.out.println("ID - " + resultadoConsulta.getString("id") + " | Numero da conta - "
                            + resultadoConsulta.getString("numconta") + " | Tipo de Conta - "
                            + resultadoConsulta.getString("tipoconta"));
                }
            }

            database.desconectarBanco();

        } catch (Exception e) {
            e.printStackTrace();
            Main.start();
            return;
        }

        Main.start();
    }

    public static String verificarTipoConta(int numeroConta) {
        DbContext database = new DbContext();

        try {
            database.conectarBanco();

            ResultSet resultadoConta = database.executarQuerySql(
                    "SELECT tipoconta FROM public.contas WHERE numconta = " + numeroConta);

            if (resultadoConta.next()) {
                return resultadoConta.getString("tipoconta");
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            database.desconectarBanco();
        }
    }
}
