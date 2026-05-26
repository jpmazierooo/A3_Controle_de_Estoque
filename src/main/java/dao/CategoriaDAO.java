    package dao;
    import dao.db.Database;
    import dao.db.DbException;
    import java.sql.*;
    import java.util.ArrayList;
    import java.util.List;
    import modelo.Categoria;

    public class CategoriaDAO {
        private Connection conn;

        public CategoriaDAO() {
            this.conn = Database.getConnection(); 
        }

        // INSERIR
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

        // ATUALIZAR
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

        // DELETAR
        public void deletar(int id) {
            String sql = "DELETE FROM categorias WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, id);
                stmt.executeUpdate();
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }

        // BUSCAR POR ID
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

        // LISTAR TODOS
        public List<Categoria> listarTodos() {
            List<Categoria> lista = new ArrayList<>();
            String sql = "SELECT * FROM categorias";
            try (PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {
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