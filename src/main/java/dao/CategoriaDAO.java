package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CategoriaDAO {

    // Conexão com o banco - ajuste os dados para os seus
    private Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/sistema_estoque";
        String usuario = "root";
        String senha = "123456789";
        return DriverManager.getConnection(url, usuario, senha);
    }

    public void deletar(int id) throws SQLException {
        Connection con = getConnection();
        PreparedStatement stmt = con.prepareStatement("DELETE DE categoria WHERE id = ?");
        stmt.setInt(1, id);
        stmt.executeUpdate();
        stmt.close();
        con.close();
    }
}