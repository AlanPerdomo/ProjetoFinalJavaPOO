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

        if (!Pessoa.validarCPF(cpf)) {
            Main.mensagemStatus("O CPF informado é inválido!");
            Main.start();
            return;
        }

        DbContext database = new DbContext();

        try {
            database.conectarBanco();
            boolean pessoaExistente = verificarPessoaExistente(database, nome, cpf);
            if (pessoaExistente) {
                Main.mensagemStatus("CPF já cadastrado!");
            } else {
                boolean statusQuery = database.executarUpdateSql(
                        "INSERT INTO public.pessoa(nome,cpf) VALUES ('" + nome + "', '" + cpf + "')");
                if (statusQuery) {
                    mensagemStatus("'" + nome + "' foi cadastrado(a)!");
                }
            }
            database.desconectarBanco();
        } catch (Exception e) {
        }
        Main.start();
    }

}
