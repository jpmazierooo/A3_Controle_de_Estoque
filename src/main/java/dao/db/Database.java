package dao.db;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Classe utilitária responsável por gerenciar a conexão com o banco de dados.
 * Implementa o padrão Singleton: mantém uma única instância de {@link Connection}
 * durante toda a execução da aplicação.
 *
 * <p>As credenciais e a URL de conexão são lidas do arquivo {@code db.propriedade},
 * que deve estar presente no classpath (diretório {@code src/main/recurso}).</p>
 *
 * @author João Marimon
 */
public class Database {

    private static Connection conn = null;

    /**
     * Retorna a conexão ativa com o banco de dados.
     * Se ainda não existir uma conexão, ela é criada a partir das propriedades
     * definidas em {@code db.propriedade}.
     *
     * @return {@link Connection} ativa com o banco de dados.
     * @throws DbException se ocorrer erro ao estabelecer a conexão.
     */
    public static Connection getConnection() {
        if (conn == null) {
            try {
                Properties props = loadProperties();
                String url = props.getProperty("dburl");
                conn = DriverManager.getConnection(url, props);
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
        return conn;
    }

    /**
     * Fecha a conexão com o banco de dados, caso esteja aberta.
     *
     * @throws DbException se ocorrer erro ao fechar a conexão.
     */
    public static void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
    }

    /**
     * Carrega as propriedades de conexão do arquivo {@code db.propriedade} do classpath.
     *
     * @return {@link Properties} com as configurações de conexão (dburl, user, password).
     * @throws DbException se o arquivo não for encontrado ou ocorrer erro de leitura.
     */
    private static Properties loadProperties() {
        try (InputStream is = Database.class.getClassLoader().getResourceAsStream("db.propriedade")) {
            if (is == null) {
                throw new DbException("Arquivo db.propriedade nao encontrado no classpath");
            }
            Properties props = new Properties();
            props.load(is);
            return props;
        } catch (IOException e) {
            throw new DbException(e.getMessage());
        }
    }
}
