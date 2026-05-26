package dao;

import dao.db.Database;
import dao.db.DbException;
import java.sql.*;
import java.util.ArrayList;
import model.Produto;

/**
 * Classe Data Access Object (DAO) para o Produto.
 * Realiza operações CRUD na tabela "produtos" do banco de dados.
 *
 * @author João Pedro Maziero
 */
public class ProdutoDAO {

    private Connection conn;

    public ProdutoDAO() {
        this.conn = Database.getConnection();
    }

    /**
     * Insere um novo produto no banco de dados.
     *
     * @param obj O produto a ser inserido.
     */
    public void inserir(Produto obj) {
        String sql = "INSERT INTO produtos (nome, preco_unitario, unidade, quantidade_estoque, quantidade_minima, quantidade_maxima, categoria_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, obj.getNome());
            stmt.setDouble(2, obj.getPrecoUn());
            stmt.setString(3, obj.getUnidade());
            stmt.setInt(4, obj.getQuantidade());
            stmt.setInt(5, obj.getQuantidadeMin());
            stmt.setInt(6, obj.getQuantidadeMax());
            stmt.setInt(7, obj.getCategoriaId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    /**
     * Atualiza os dados de um produto existente no banco de dados.
     *
     * @param obj O produto com os dados atualizados (deve conter o ID).
     */
    public void atualizar(Produto obj) {
        String sql = "UPDATE produtos SET nome = ?, preco_unitario = ?, unidade = ?, quantidade_estoque = ?, quantidade_minima = ?, quantidade_maxima = ?, categoria_id = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, obj.getNome());
            stmt.setDouble(2, obj.getPrecoUn());
            stmt.setString(3, obj.getUnidade());
            stmt.setInt(4, obj.getQuantidade());
            stmt.setInt(5, obj.getQuantidadeMin());
            stmt.setInt(6, obj.getQuantidadeMax());
            stmt.setInt(7, obj.getCategoriaId());
            stmt.setInt(8, obj.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    /**
     * Remove um produto do banco de dados pelo seu ID.
     *
     * @param id O identificador único do produto a ser removido.
     */
    public void deletar(int id) {
        String sql = "DELETE FROM produtos WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    /**
     * Retorna todos os produtos cadastrados no banco de dados.
     *
     * @return Um {@link ArrayList} com todos os produtos em ordem alfabética.
     */
    public ArrayList<Produto> listarTodos() {
        ArrayList<Produto> lista = new ArrayList<>();
        String sql = "SELECT * FROM produtos ORDER BY nome";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                lista.add(new Produto(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getDouble("preco_unitario"),
                    rs.getString("unidade"),
                    rs.getInt("quantidade_estoque"),
                    rs.getInt("quantidade_minima"),
                    rs.getInt("quantidade_maxima"),
                    rs.getInt("categoria_id")
                ));
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        return lista;
    }

    /**
     * Retorna a lista de produtos do banco. Mantido para compatibilidade com Produto.java.
     *
     * @return Um {@link ArrayList} contendo todas as instâncias de {@link Produto}.
     */
    public static ArrayList<Produto> getMinhaLista() {
        return new ProdutoDAO().listarTodos();
    }

    /**
     * Atualiza apenas a quantidade em estoque de um produto.
     * Usado pelas operações de Entrada e Saída.
     *
     * @param id              ID do produto.
     * @param novaQuantidade  Nova quantidade em estoque.
     */
    public void atualizarQuantidade(int id, int novaQuantidade) {
        String sql = "UPDATE produtos SET quantidade_estoque = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, novaQuantidade);
            stmt.setInt(2, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    /** Não utilizado com JDBC (AUTO_INCREMENT). Mantido para compatibilidade. */
    public static int maiorID() {
        return 0;
    }
}
