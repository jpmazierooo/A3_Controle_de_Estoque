package dao;

import dao.db.Database;
import dao.db.DbException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import modelo.Movimentacao;

/**
 * DAO (Data Access Object) responsável pelas operações de persistência
 * relacionadas a {@link Movimentacao} no banco de dados.
 *
 * <p>
 * Esta classe encapsula o acesso à tabela {@code movimentacoes}, oferecendo
 * operações de inserção e consulta. A conexão com o banco é obtida no momento
 * da instanciação por meio de {@link Database#getConnection()}.</p>
 *
 * <p>
 * Em caso de falha de acesso ao banco, os métodos lançam
 * {@link DbException}.</p>
 *
 * @see Movimentacao
 * @see Database
 * @see DbException
 */
public class MovimentacaoDAO {

    /**
     * Conexão ativa com o banco de dados, utilizada por todas as operações
     * desta instância.
     */
    private Connection conn;

    /**
     * Cria uma nova instância de {@code MovimentacaoDAO} e obtém uma conexão
     * com o banco de dados através de {@link Database#getConnection()}.
     */
    public MovimentacaoDAO() {
        this.conn = Database.getConnection();
    }

    /**
     * Insere uma nova movimentação no banco de dados.
     *
     * <p>
     * Os dados são extraídos do objeto informado e gravados na tabela
     * {@code movimentacoes}. O identificador (campo {@code id}) é gerado
     * automaticamente pelo banco e, portanto, não é utilizado na inserção.</p>
     *
     * @param obj a movimentação a ser persistida; não deve ser {@code null}
     * @throws DbException se ocorrer um erro de acesso ao banco de dados
     * durante a inserção
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
     * Busca todas as movimentações cadastradas no banco de dados.
     *
     * <p>
     * O resultado é ordenado da movimentação mais recente para a mais antiga,
     * utilizando os campos {@code data} e {@code id} em ordem decrescente.</p>
     *
     * @return uma lista de {@link Movimentacao} com todas as movimentações
     * encontradas; retorna uma lista vazia caso não haja registros
     * @throws DbException se ocorrer um erro de acesso ao banco de dados
     * durante a consulta
     */
    public List<Movimentacao> listarMovimentacoes() {
        List<Movimentacao> lista = new ArrayList<>();

        // Organiza a lista da movimentação mais recente para a mais antiga
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
     * Verifica se existe pelo menos uma movimentação cadastrada no banco de dados.
     *
     * <p>Internamente, este método consulta todas as movimentações por meio de
     * {@link #listarMovimentacoes()} e verifica se a lista resultante não está
     * vazia.</p>
     *
     * @return {@code true} se houver ao menos uma movimentação cadastrada;
     *         {@code false} caso contrário
     * @throws DbException se ocorrer um erro de acesso ao banco de dados
     *                     durante a consulta
     */
    public boolean possuiMovimentacoes() {
        return !listarMovimentacoes().isEmpty();
    }
}
