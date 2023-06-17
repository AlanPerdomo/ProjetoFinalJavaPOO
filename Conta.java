import java.sql.ResultSet;
import java.util.Scanner;

import Data.DbContext;

public abstract class Conta {
    public int numeroConta;
    public Pessoa nome;
    public double saldo;

    public Conta(int numeroConta, Pessoa nome, double saldo) {
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
                System.out.println("\n Selecionoe o tipo de conta: \n");
                System.out.println("1 - Conta Corrente");
                System.out.println("2 - Conta Poupança");
                System.out.print("\n Opção: ");

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
                    }

                    boolean statusQuery = database.executarUpdateSql(
                            "INSERT INTO public.contas(tipoconta,cpf) VALUES ('" + tipoConta + "', '" + cpf + "')");
                    if (statusQuery) {
                        Main.mensagemStatus("'" + tipoConta + "' foi criada!");
                    }
                } catch (Exception e) {
                    System.out.println("Entrada inválida! Digite um número correspondente à opção desejada.");
                    scanner.nextLine(); // Limpar o buffer do scanner
                }
            } else {
                Main.mensagemStatus("CPF não cadastrado!");
            }
            database.desconectarBanco();
        } catch (Exception e) {
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

            ResultSet resultadoConsulta = database.executarQuerySql("SELECT * FROM public.contas");

            while (resultadoConsulta.next()) {
                System.out.println("ID - " + resultadoConsulta.getString("id") + " | NOME - "
                        + resultadoConsulta.getString("nome"));
            }

            database.desconectarBanco();

        } catch (Exception e) {
        }

        Main.start();
    }

}
