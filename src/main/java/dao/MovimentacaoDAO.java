package dao;

import dao.db.Database;
import dao.db.DbException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import modelo.Movimentacao;

/**
 * DAO responsável pelas operações de movimentação no banco de dados.
 */
public class MovimentacaoDAO {

    private Connection conn;

    public MovimentacaoDAO() {
        this.conn = Database.getConnection();
    }

    /**
     * Insere uma nova movimentação no banco de dados.
     */
    public void inserir(Movimentacao obj) {
        String sql = "INSERT INTO movimentacoes (nome, tipo, qtd, data, movimentacao, status_estoque) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, obj.getNome());
            stmt.setString(2, obj.getTipo());
            stmt.setInt(3, obj.getQtd());
            stmt.setString(4, obj.getData());
            stmt.setString(5, obj.getMovimentacao());
            stmt.setString(6, obj.getStatusEstoque());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    /**
     * Busca todas as movimentações cadastradas e retorna uma lista.
     */
    public List<Movimentacao> listarMovimentacoes() {
        List<Movimentacao> lista = new ArrayList<>();

        // Ordena da movimentação mais recente para a mais antiga
        String sql = "SELECT * FROM movimentacoes ORDER BY data DESC, id DESC";

        try (PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                lista.add(new Movimentacao(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("tipo"),
                        rs.getInt("qtd"),
                        rs.getString("data"),
                        rs.getString("movimentacao"),
                        rs.getString("status_estoque")
                ));
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        return lista;
    }

    /**
     * Verifica se existe pelo menos uma movimentação cadastrada.
     */
    public boolean possuiMovimentacoes() {
        return !listarMovimentacoes().isEmpty();
    }
}
