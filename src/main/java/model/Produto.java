package model;

import dao.ProdutoDAO;
import java.util.ArrayList;

/**
 * Representa um Produto dentro do sistema, contendo suas informações cadastrais,
 * regras de estoque mínimo/máximo e métodos para manipulação de dados via DAO.
 *
 * @author João Pedro Maziero
 */
public class Produto {

    private int id;
    private String nome;
    private double precoUn;
    private String unidade;
    private int quantidade;
    private int quantidadeMin;
    private int quantidadeMax;
    private int categoriaId;

    /** Construtor vazio para a criação de um Produto sem atributos. */
    public Produto() {
    }

    /**
     * Construtor completo para inicializar um Produto com todos os seus atributos.
     *
     * @param id            O identificador único do produto.
     * @param nome          O nome ou descrição do produto.
     * @param precoUn       O preço unitário do produto.
     * @param unidade       A unidade de medida (ex: KG, L e UN).
     * @param quantidade    A quantidade atual em estoque.
     * @param quantidadeMin A quantidade mínima permitida em estoque.
     * @param quantidadeMax A quantidade máxima permitida em estoque.
     * @param categoriaId   O ID da categoria à qual o produto pertence.
     */
    public Produto(int id, String nome, double precoUn, String unidade, int quantidade, int quantidadeMin, int quantidadeMax, int categoriaId) {
        this.id = id;
        this.nome = nome;
        this.precoUn = precoUn;
        this.unidade = unidade;
        this.quantidade = quantidade;
        this.quantidadeMin = quantidadeMin;
        this.quantidadeMax = quantidadeMax;
        this.categoriaId = categoriaId;
    }

    /** @return O identificador único do produto. */
    public int getId() { return id; }

    /** @return O nome do produto. */
    public String getNome() { return nome; }

    /** @return O preço unitário do produto. */
    public double getPrecoUn() { return precoUn; }

    /** @return A unidade de medida do produto. */
    public String getUnidade() { return unidade; }

    /** @return A quantidade atual em estoque. */
    public int getQuantidade() { return quantidade; }

    /** @return A quantidade mínima estipulada para o estoque. */
    public int getQuantidadeMin() { return quantidadeMin; }

    /** @return A quantidade máxima estipulada para o estoque. */
    public int getQuantidadeMax() { return quantidadeMax; }

    /** @return O ID da categoria do produto. */
    public int getCategoriaId() { return categoriaId; }

    /** @param id O identificador único a ser definido para o produto. */
    public void setId(int id) { this.id = id; }

    /** @param nome O nome a ser definido para o produto. */
    public void setNome(String nome) { this.nome = nome; }

    /** @param precoUn O preço unitário a ser definido para o produto. */
    public void setPrecoUn(double precoUn) { this.precoUn = precoUn; }

    /** @param unidade A unidade de medida a ser definida para o produto. */
    public void setUnidade(String unidade) { this.unidade = unidade; }

    /** @param quantidade A quantidade em estoque a ser definida para o produto. */
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }

    /** @param quantidadeMin A quantidade mínima a ser definida para o estoque. */
    public void setQuantidadeMin(int quantidadeMin) { this.quantidadeMin = quantidadeMin; }

    /** @param quantidadeMax A quantidade máxima a ser definida para o estoque. */
    public void setQuantidadeMax(int quantidadeMax) { this.quantidadeMax = quantidadeMax; }

    /** @param categoriaId O ID da categoria a ser definida para o produto. */
    public void setCategoriaId(int categoriaId) { this.categoriaId = categoriaId; }

    /**
     * Recupera a lista completa de produtos armazenada na camada de dados (DAO).
     *
     * @return Um {@link ArrayList} contendo todos os produtos cadastrados.
     */
    public ArrayList<Produto> getMinhaLista() {
        return ProdutoDAO.getMinhaLista();
    }

    /**
     * Cadastra um novo produto no banco de dados.
     *
     * @param nome          Nome do produto.
     * @param precoUn       Preço unitário.
     * @param unidade       Unidade de medida.
     * @param quantidade    Quantidade inicial em estoque.
     * @param quantidadeMin Quantidade mínima de estoque.
     * @param quantidadeMax Quantidade máxima de estoque.
     * @param categoriaId   ID da categoria do produto.
     * @return {@code true} se o produto foi inserido com sucesso.
     */
    public boolean insertProdutoBD(String nome, double precoUn, String unidade, int quantidade, int quantidadeMin, int quantidadeMax, int categoriaId) {
        Produto objeto = new Produto(0, nome, precoUn, unidade, quantidade, quantidadeMin, quantidadeMax, categoriaId);
        new ProdutoDAO().inserir(objeto);
        return true;
    }

    /**
     * Exclui um produto do banco de dados com base no seu ID.
     *
     * @param id O identificador único do produto a ser removido.
     * @return {@code true} se o produto foi removido com sucesso.
     */
    public boolean deleteProdutoBD(int id) {
        new ProdutoDAO().deletar(id);
        return true;
    }

    /**
     * Atualiza os dados de um produto específico já existente no banco de dados.
     *
     * @param id            O ID do produto que será atualizado.
     * @param nome          O novo nome do produto.
     * @param precoUn       O novo preço unitário.
     * @param unidade       A nova unidade de medida.
     * @param quantidade    A nova quantidade em estoque.
     * @param quantidadeMin A nova quantidade mínima.
     * @param quantidadeMax A nova quantidade máxima.
     * @param categoriaId   O novo ID da categoria.
     * @return {@code true} se o produto foi atualizado com sucesso.
     */
    public boolean updateProdutoBD(int id, String nome, double precoUn, String unidade, int quantidade, int quantidadeMin, int quantidadeMax, int categoriaId) {
        Produto objeto = new Produto(id, nome, precoUn, unidade, quantidade, quantidadeMin, quantidadeMax, categoriaId);
        new ProdutoDAO().atualizar(objeto);
        return true;
    }

    /**
     * Busca um produto pelo ID na lista carregada do banco.
     *
     * @param id O identificador único do produto que se deseja carregar.
     * @return O objeto {@link Produto} correspondente ao ID, ou {@code null} se não encontrado.
     */
    public Produto carregaProduto(int id) {
        for (Produto p : getMinhaLista()) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }
}
