import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.*;
import java.util.Scanner;


public class ConexaoMySQL {
    public static void main(String[] args) {
        // Configurações de conexão com o banco de dados
        String url = "jdbc:mysql://localhost:3306/trabalhobd"; // Altere para a URL do seu banco de dados
        String usuario = "root"; // Altere para o usuário do seu banco de dados
        String senha = "senha"; // Altere para a senha do seu banco de dados

        try {
            // Conectar ao banco de dados
            Connection conexao = DriverManager.getConnection(url, usuario, senha);

            // Criar uma declaração SQL
            Statement statement = conexao.createStatement();

            // Consulta para obter os cursos disponíveis
            String consultaCursos = "SELECT DISTINCT nomeCurso FROM MatriculaAluno";
            ResultSet resultSetCursos = statement.executeQuery(consultaCursos);

            // Imprimir os cursos disponíveis
            System.out.println("Cursos Disponíveis:");
            while (resultSetCursos.next()) {
                String curso = resultSetCursos.getString("nomeCurso");
                System.out.println(curso);
            }

            // Consulta para obter as disciplinas disponíveis para cada curso
            String consultaDisciplinas = "SELECT DISTINCT nomeCurso, nomeDisciplina FROM MatriculaAluno";
            ResultSet resultSetDisciplinas = statement.executeQuery(consultaDisciplinas);

            // Imprimir as disciplinas disponíveis para cada curso
            System.out.println("\nDisciplinas Disponíveis por Curso:");
            while (resultSetDisciplinas.next()) {
                String curso = resultSetDisciplinas.getString("nomeCurso");
                String disciplina = resultSetDisciplinas.getString("nomeDisciplina");
                System.out.println("Curso: " + curso + ", Disciplina: " + disciplina);
            }

            // Consulta para obter os alunos que não estão matriculados em nenhum curso
            String consultaAlunosNaoMatriculados = "SELECT DISTINCT nomeAluno FROM MatriculaAluno WHERE nomeCurso IS NULL";
            ResultSet resultSetAlunosNaoMatriculados = statement.executeQuery(consultaAlunosNaoMatriculados);

            // Imprimir os alunos não matriculados em nenhum curso
            System.out.println("Alunos não matriculados em nenhum curso:");
            while (resultSetAlunosNaoMatriculados.next()) {
                String aluno = resultSetAlunosNaoMatriculados.getString("nomeAluno");
                System.out.println(aluno);
            }

            // Consulta para obter os alunos matriculados na disciplina de "Banco de Dados"
            String consultaAlunosEmBancoDeDados = "SELECT DISTINCT nomeAluno FROM MatriculaAluno WHERE nomeDisciplina = 'Banco de Dados'";
            ResultSet resultSetAlunosEmBancoDeDados = statement.executeQuery(consultaAlunosEmBancoDeDados);

            // Imprimir os alunos matriculados na disciplina de "Banco de Dados"
            System.out.println("Alunos matriculados na disciplina de Banco de Dados:");
            while (resultSetAlunosEmBancoDeDados.next()) {
                String aluno = resultSetAlunosEmBancoDeDados.getString("nomeAluno");
                System.out.println(aluno);
            }

            // Solicitar o nome do aluno para busca
            Scanner scanner = new Scanner(System.in);
            System.out.print("\nDigite o nome do aluno que deseja buscar: ");
            String nomeAluno = scanner.nextLine();

            // Consulta para obter informações do aluno pelo nome
            String consultaAlunoPorNome = "SELECT nomeAluno, nomeCurso, nomeDisciplina, cpfAluno, foneAluno FROM MatriculaAluno WHERE nomeAluno = ?";
            PreparedStatement preparedStatement = conexao.prepareStatement(consultaAlunoPorNome);
            preparedStatement.setString(1, nomeAluno);

            ResultSet resultSetAluno = ((PreparedStatement) preparedStatement).executeQuery();

            // Imprimir as informações do aluno
            System.out.println("\nInformações do Aluno:");
            while (resultSetAluno.next()) {
                String aluno = resultSetAluno.getString("nomeAluno");
                String curso = resultSetAluno.getString("nomeCurso");
                String disciplina = resultSetAluno.getString("nomeDisciplina");
                String cpf = resultSetAluno.getString("cpfAluno");
                String telefone = resultSetAluno.getString("foneAluno");
                System.out.println("Aluno: " + aluno + ", Curso: " + curso + ", Disciplina: " + disciplina + ", Cpf: " + cpf +", telefone: " +telefone);
            }

            // Fechar recursos
            resultSetAlunosEmBancoDeDados.close();
            resultSetAlunosNaoMatriculados.close();
            resultSetCursos.close();
            resultSetDisciplinas.close();
            statement.close();
            conexao.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}