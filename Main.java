import java.util.HashMap;
import java.util.Map;
import java.sql.ResultSet;
import java.util.Scanner;

import Data.DbContext;

import classes.Conta;
import classes.ContaCorrente;
import classes.ContaPoupanca;
import classes.Pessoa;

public class Main {
    public static void main(String[] args) {
        Map<String, Pessoa> pessoas = new HashMap<>();
        Map<String, Conta> contas = new HashMap<>();

        Scanner scanner = new Scanner(System.in);

        boolean executando = true;
        while (executando) {
            System.out.println("---------- MENU ----------");
            System.out.println("1. Criar Pessoa");
            System.out.println("2. Criar Conta Corrente");
            System.out.println("3. Criar Conta Poupança");
            System.out.println("4. Realizar Operação");
            System.out.println("5. Sair");
            System.out.print("Escolha uma opção: ");

            try {
                int opcao = scanner.nextInt();
                scanner.nextLine(); // Limpar o buffer do scanner

                switch (opcao) {
                    case 1:
                        System.out.print("Digite o CPF da pessoa: ");
                        String cpf = scanner.nextLine();
                        System.out.print("Digite o nome da pessoa: ");
                        String nome = scanner.nextLine();
                        Pessoa pessoa = new Pessoa(cpf, nome);
                        pessoas.put(cpf, pessoa);
                        System.out.println("Pessoa criada com sucesso!");
                        break;

                    case 2:
                        System.out.print("Digite o CPF da pessoa titular: ");
                        cpf = scanner.nextLine();
                        pessoa = pessoas.get(cpf);
                        if (pessoa != null) {
                            System.out.print("Digite o número da conta corrente: ");
                            String numeroCC = scanner.nextLine();
                            ContaCorrente cc = new ContaCorrente(numeroCC, pessoa, 0.0);
                            contas.put(numeroCC, cc);
                            System.out.println("Conta corrente criada com sucesso!");
                        } else {
                            System.out.println("CPF não encontrado. Criação de conta corrente cancelada.");
                        }
                        break;

                    case 3:
                        System.out.print("Digite o CPF da pessoa titular: ");
                        cpf = scanner.nextLine();
                        pessoa = pessoas.get(cpf);
                        if (pessoa != null) {
                            System.out.print("Digite o número da conta poupança: ");
                            String numeroCP = scanner.nextLine();
                            ContaPoupanca cp = new ContaPoupanca(numeroCP, pessoa, 0.0);
                            contas.put(numeroCP, cp);
                            System.out.println("Conta poupança criada com sucesso!");
                        } else {
                            System.out.println("CPF não encontrado. Criação de conta poupança cancelada.");
                        }
                        break;

                    case 4:
                        System.out.print("Digite o número da conta: ");
                        String numeroConta = scanner.nextLine();
                        Conta conta = contas.get(numeroConta);
                        if (conta != null) {
                            System.out.println("---------- MENU DE OPERAÇÕES ----------");
                            System.out.println("1. Sacar");
                            System.out.println("2. Depositar");
                            System.out.println("3. Consultar Saldo");
                            System.out.print("Escolha uma operação: ");
                            int operacao = scanner.nextInt();
                            scanner.nextLine(); // Limpar o buffer do scanner

                            switch (operacao) {
                                case 1:
                                    System.out.print("Digite o valor a ser sacado: ");
                                    double valorSaque = scanner.nextDouble();
                                    scanner.nextLine(); // Limpar o buffer do scanner
                                    try {
                                        conta.sacar(valorSaque);
                                        System.out.println("Saque realizado com sucesso!");
                                    } catch (IllegalArgumentException e) {
                                        System.out.println(e.getMessage());
                                    }
                                    break;

                                case 2:
                                    System.out.print("Digite o valor a ser depositado: ");
                                    double valorDeposito = scanner.nextDouble();
                                    scanner.nextLine(); // Limpar o buffer do scanner
                                    conta.depositar(valorDeposito);
                                    System.out.println("Depósito realizado com sucesso!");
                                    break;

                                case 3:
                                    double saldo = conta.consultarSaldo();
                                    System.out.println("Saldo da conta: " + saldo);
                                    break;

                                default:
                                    System.out.println("Operação inválida!");
                            }
                        } else {
                            System.out.println("Conta não encontrada!");
                        }
                        break;

                    case 5:
                        executando = false;
                        break;

                    default:
                        System.out.println("Opção inválida!");
                }
            } catch (Exception e) {
                System.out.println("Entrada inválida! Digite um número correspondente à opção desejada.");
                scanner.nextLine(); // Limpar o buffer do scanner
            }
        }
    }
}
