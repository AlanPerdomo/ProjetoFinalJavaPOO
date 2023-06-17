
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Scanner;
import Data.DbContext;

public class Pessoa {
    public String nome;
    public String cpf;

    public Pessoa(String nome, String cpf) {
        this.nome = nome;
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public static void listarPessoas() {
        DbContext database = new DbContext();

        try {
            database.conectarBanco();

            ResultSet resultadoConsulta = database.executarQuerySql("SELECT * FROM public.pessoas");

            while (resultadoConsulta.next()) {
                System.out.println("ID - " + resultadoConsulta.getString("id") + " | NOME - "
                        + resultadoConsulta.getString("nome") + " | CPF - " + resultadoConsulta.getString("cpf"));
            }

            database.desconectarBanco();

        } catch (Exception e) {
        }

        start();
    }

    public static void cadastrarPessoa() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\nInforme o nome da Pessoa: ");
        String nome = scanner.nextLine().trim();

        if (!validarNome(nome)) {
            Main.mensagemStatus("O nome informado é inválido!");
            start();
            return;
        }

        System.out.println("\nInforme o CPF da Pessoa: ");
        String cpf = scanner.next();

        if (!validarCPF(cpf)) {
            Main.mensagemStatus("O CPF informado é inválido!");
            start();
            return;
        }

        DbContext database = new DbContext();

        try {
            database.conectarBanco();
            boolean pessoaExistente = verificarPessoaExistente(database, cpf);
            if (pessoaExistente) {
                Main.mensagemStatus("CPF já cadastrado!");
            } else {
                boolean statusQuery = database.executarUpdateSql(
                        "INSERT INTO public.pessoas(nome,cpf) VALUES ('" + nome + "', '" + cpf + "')");
                if (statusQuery) {
                    Main.mensagemStatus("'" + nome + "' foi cadastrado(a)!");
                }
            }
            database.desconectarBanco();
        } catch (Exception e) {
        }
        start();
    }

    public static boolean verificarPessoaExistente(DbContext database, String cpf) throws SQLException {
        String query = "SELECT COUNT(*) FROM public.pessoas WHERE cpf = '" + cpf + "'";
        ResultSet resultSet = database.executarQuerySql(query);

        if (resultSet.next()) {
            int count = resultSet.getInt(1);
            return count > 0;
        }

        return false;
    }

    public static boolean validarNome(String nome) {
        String regex = "^[a-zA-Z\\s]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(nome);
        return matcher.matches();
    }

    public static boolean validarCPF(String cpf) {
        String regex = "^[0-9]{11}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(cpf);
        return matcher.matches();
    }

    public static void start() {
        Main.start();
    }
}
