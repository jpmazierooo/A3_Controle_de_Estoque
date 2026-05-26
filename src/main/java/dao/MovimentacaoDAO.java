package dao;

import dao.db.Database;
import dao.db.DbException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Movimentacao;

/**
 * Classe Data Access Object (DAO) para Movimentacao.
 * Realiza operações de inserção e consulta na tabela "movimentacoes".
 *
 * @author Derek Slater
 */
public class MovimentacaoDAO {

    private Connection conn;

    public MovimentacaoDAO() {
        this.conn = Database.getConnection();
    }

    /**
     * Insere uma nova movimentação no banco de dados.
     *
     * @param obj A movimentação a ser inserida.
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
     * Retorna todas as movimentações registradas, da mais recente para a mais antiga.
     *
     * @return Lista de {@link Movimentacao}.
     */
    public List<Movimentacao> listarMovimentacoes() {
        List<Movimentacao> lista = new ArrayList<>();
        String sql = "SELECT * FROM movimentacoes ORDER BY data DESC, id DESC";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
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

    /** @return {@code true} se existem movimentações cadastradas. */
    public boolean possuiMovimentacoes() {
        return !listarMovimentacoes().isEmpty();
    }
}
