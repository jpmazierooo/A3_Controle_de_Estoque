package dao;

import dao.db.Database;
import dao.db.DbException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import modelo.Categoria;

/**
 * Classe responsável pelo acesso aos dados da entidade {@link Categoria}.
 * <p>
 * Implementa as operações CRUD (Create, Read, Update, Delete) para a tabela
 * {@code categorias} no banco de dados, seguindo o padrão DAO (Data Access
 * Object).
 * </p>
 *
 * @author Pedro Henrique
 */
public class CategoriaDAO {

    /**
     * Conexão com o banco de dados obtida através da classe {@link Database}.
     */
    private Connection conn;

    /**
     * Construtor da classe CategoriaDAO. Obtém a conexão com o banco de dados
     * ao ser instanciado.
     */
    public CategoriaDAO() {
        this.conn = Database.getConnection();
    }

    /**
     * Insere uma nova categoria no banco de dados.
     * <p>
     * O id não é informado pois é gerado automaticamente pelo banco.
     * </p>
     *
     * @param obj objeto {@link Categoria} contendo os dados a serem inseridos
     * @throws DbException se ocorrer um erro ao executar o comando SQL
     */
    public void inserir(Categoria obj) {
        String sql = "INSERT INTO categorias (nome, tamanho, embalagem) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, obj.getNome());
            stmt.setString(2, obj.getTamanho());
            stmt.setString(3, obj.getEmbalagem());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    /**
     * Atualiza os dados de uma categoria existente no banco de dados. A
     * categoria é identificada pelo seu id.
     *
     * @param obj objeto {@link Categoria} contendo os novos dados e o id da
     * categoria a ser atualizada
     * @throws DbException se ocorrer um erro ao executar o comando SQL
     */
    public void atualizar(Categoria obj) {
        String sql = "UPDATE categorias SET nome = ?, tamanho = ?, embalagem = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, obj.getNome());
            stmt.setString(2, obj.getTamanho());
            stmt.setString(3, obj.getEmbalagem());
            stmt.setInt(4, obj.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    /**
     * Remove uma categoria do banco de dados pelo seu identificador.
     *
     * @param id identificador único da categoria a ser removida
     * @throws DbException se ocorrer um erro ao executar o comando SQL
     */
    public void deletar(int id) {
        String sql = "DELETE FROM categorias WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    /**
     * Busca uma categoria no banco de dados pelo seu identificador.
     *
     * @param id identificador único da categoria a ser buscada
     * @return objeto {@link Categoria} com os dados encontrados, ou
     * {@code null} se não existir
     * @throws DbException se ocorrer um erro ao executar o comando SQL
     */
    public Categoria buscarPorId(int id) {
        String sql = "SELECT * FROM categorias WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Categoria(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("tamanho"),
                        rs.getString("embalagem")
                );
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        return null;
    }

    /**
     * Retorna uma lista com todas as categorias cadastradas no banco de dados.
     *
     * @return {@link List} de objetos {@link Categoria} com todos os registros
     * encontrados, ou uma lista vazia se não houver nenhum registro
     * @throws DbException se ocorrer um erro ao executar o comando SQL
     */
    public List<Categoria> listarTodos() {
        List<Categoria> lista = new ArrayList<>();
        String sql = "SELECT * FROM categorias";
        try (PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                lista.add(new Categoria(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("tamanho"),
                        rs.getString("embalagem")
                ));
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        return lista;
    }
}
