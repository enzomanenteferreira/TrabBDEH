import java.sql.*;

public class parte3{
    public static void main(String[] args) {
        // Configurações de conexão com o banco de dados
        try{
            Class.forName("com.mysql.cj.jdbc.Driver"); /* Aqui registra */
        } catch(ClassNotFoundException ex){
            System.out.println("driver nao localizado");
        }
        String url = "jdbc:mysql://localhost:3306/parte3"; // Altere para a URL do seu banco de dados
        String usuario = "root"; // Altere para o usuário do seu banco de dados
        String senha = "pingo88490"; // Altere para a senha do seu banco de dados


        try {
            // Conectar ao banco de dados
            Connection conexao = DriverManager.getConnection(url, usuario, senha);
            // Criar uma declaração SQL
            Statement statement = conexao.createStatement();

            //Comando para pegar todos os cursos
            String sqlGetCoursos = "SELECT nomeCurso FROM Curso";
            Statement statement1 = conexao.createStatement();
            ResultSet coursos = statement1.executeQuery(sqlGetCoursos);
            System.out.println("Cursos disponíveis:");

            //Imprimir todos os cursos no console
            while (coursos.next()) {
                System.out.println(coursos.getString("nomeCurso"));
            }

            //Comando para pegar todas as disciplinas de um curso - suponho que seja o curso com id=1
            String sqlGetDisciplinas = "SELECT Disciplina.nomeDisciplina FROM Disciplina JOIN MatriculaDisciplina ON Disciplina.idDisciplina = MatriculaDisciplina.idDisciplina JOIN MatriculaAluno ON MatriculaDisciplina.nroMatricula = MatriculaAluno.nroMatricula WHERE MatriculaAluno.idCurso = 1";
            Statement statement2 = conexao.createStatement();
            ResultSet disciplinas = statement2.executeQuery(sqlGetDisciplinas);
            System.out.println("\nDisciplinas disponíveis no curso selecionado:");

            //Imprimir todas as disciplinas no console
            while (disciplinas.next()) {
                System.out.println(disciplinas.getString("nomeDisciplina"));
            }

            // Comando para pegar todos os alunos não matriculados em nenhum curso
            String sqlGetalunosnaomatriculados = "SELECT nomeAluno FROM Aluno LEFT JOIN MatriculaAluno ON Aluno.idAluno = MatriculaAluno.idAluno WHERE MatriculaAluno.idAluno IS NULL";
            Statement statement3 = conexao.createStatement();
            ResultSet alunosnaomatriculados = statement3.executeQuery(sqlGetalunosnaomatriculados);
            System.out.println("\nAlunos ainda não matriculados em nenhum curso:");

            while (alunosnaomatriculados.next()) {
                System.out.println(alunosnaomatriculados.getString("nomeAluno"));
            }

            // Comando para pegar os alunos que estão matriculados em mais de um curso
            String sqlGetMaisdeumCurso = "SELECT nomeAluno FROM MatriculaAluno JOIN Aluno ON MatriculaAluno.idAluno = Aluno.idAluno GROUP BY Aluno.idAluno HAVING COUNT(*) > 1";
            Statement statement4 = conexao.createStatement();
            ResultSet maisdeumCurso = statement4.executeQuery(sqlGetMaisdeumCurso);
            System.out.println("\nAlunos matriculados em mais de um curso:");

            while (maisdeumCurso.next()) {
                System.out.println(maisdeumCurso.getString("nomeAluno"));
            }

            // Comando para pegar os alunos matriculados no curso de Ciência da Computação que estão fazendo a disciplina de Banco de Dados
            String sqlGetcursoEspecifico = "SELECT Aluno.nomeAluno FROM Aluno JOIN MatriculaAluno ON Aluno.idAluno = MatriculaAluno.idAluno JOIN MatriculaDisciplina ON MatriculaAluno.nroMatricula = MatriculaDisciplina.nroMatricula JOIN Disciplina ON MatriculaDisciplina.idDisciplina = Disciplina.idDisciplina JOIN Curso ON MatriculaAluno.idCurso = Curso.idCurso WHERE Curso.nomeCurso = 'Ciência da Computação' AND Disciplina.nomeDisciplina = 'Banco de Dados'";
            Statement statement5 = conexao.createStatement();
            ResultSet cursoEspecifico = statement5.executeQuery(sqlGetcursoEspecifico);
            System.out.println("\nAlunos matriculados no curso de Ciência da Computação que fazem a disciplina de Banco de Dados:");

            while (cursoEspecifico.next()) {
                System.out.println(cursoEspecifico.getString("nomeAluno"));
            }


            //Fechar a conexão
            conexao.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}