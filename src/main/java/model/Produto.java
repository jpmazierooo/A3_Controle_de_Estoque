package model;

import dao.ProdutoDAO;
import java.util.ArrayList;
/**
 * Representa um Produto dentro do sistema, contendo suas informações cadastrais,
 * regras de estoque mínimo/máximo e métodos para manipulação de dados via DAO.
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
    private String categoria;
    
    /**
     * Construtor vazio para a criação de um Produto sem atributos.
     */
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
     * @param categoria     O identificador da categoria à qual o produto pertence.
     */
    public Produto(int id, String nome, double precoUn, String unidade, int quantidade, int quantidadeMin, int quantidadeMax, String categoria) {
        this.id = id;
        this.nome = nome;
        this.precoUn = precoUn;
        this.unidade = unidade;
        this.quantidade = quantidade;
        this.quantidadeMin = quantidadeMin;
        this.quantidadeMax = quantidadeMax;
        this.categoria = categoria;
    }
    /** @return O identificador único do produto. */
    public int getId() {
        return id;
    }

    /** @return O nome do produto. */
    public String getNome() {
        return nome;
    }

    /** @return O preço unitário do produto. */
    public double getPrecoUn() {
        return precoUn;
    }

    /** @return A unidade de medida do produto. */
    public String getUnidade() {
        return unidade;
    }

    /** @return A quantidade atual em estoque. */
    public int getQuantidade() {
        return quantidade;
    }

    /** @return A quantidade mínima estipulada para o estoque. */
    public int getQuantidadeMin() {
        return quantidadeMin;
    }

    /** @return A quantidade máxima estipulada para o estoque. */
    public int getQuantidadeMax() {
        return quantidadeMax;
    }

    /** @return O identificador da categoria do produto. */
    public String getCategoria() {
        return categoria;
    }

    /** @param id O identificador único a ser definido para o produto. */
    public void setId(int id) {
        this.id = id;
    }

    /** @param nome O nome a ser definido para o produto. */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /** @param precoUn O preço unitário a ser definido para o produto. */
    public void setPrecoUn(double precoUn) {
        this.precoUn = precoUn;
    }

    /** @param unidade A unidade de medida a ser definida para o produto. */
    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    /** @param quantidade A quantidade em estoque a ser definida para o produto. */
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    /** @param quantidadeMin A quantidade mínima a ser definida para o estoque. */
    public void setQuantidadeMin(int quantidadeMin) {
        this.quantidadeMin = quantidadeMin;
    }

    /** @param quantidadeMax A quantidade máxima a ser definida para o estoque. */
    public void setQuantidadeMax(int quantidadeMax) {
        this.quantidadeMax = quantidadeMax;
    }

    /** @param categoria A categoria a ser definida para o produto. */
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    
     /**
     * Recupera a lista completa de produtos armazenada na camada de dados (DAO).
     * 
     * @return Um {@link ArrayList} contendo todos os produtos cadastrados.
     */
    public ArrayList<Produto> getMinhaLista() {
        return ProdutoDAO.getMinhaLista();
    }

    /**
     * Cadastra um novo produto na base de dados. O ID é gerado automaticamente
     * incrementando o maior ID existente.
     * 
     * @param nome          Nome do produto.
     * @param precoUn       Preço unitário.
     * @param unidade       Unidade de medida.
     * @param quantidade    Quantidade inicial em estoque.
     * @param quantidadeMin Quantidade mínima de estoque.
     * @param quantidadeMax Quantidade máxima de estoque.
     * @param categoria     Categoria do produto.
     * @return {@code true} se o produto foi inserido com sucesso.
     */
    public boolean insertProdutoBD(String nome, double precoUn, String unidade, int quantidade, int quantidadeMin, int quantidadeMax, String categoria) {
        int id = ProdutoDAO.maiorID() + 1;
        Produto objeto = new Produto(id, nome, precoUn, unidade, quantidade, quantidadeMin, quantidadeMax, categoria);
        getMinhaLista().add(objeto);
        return true;
    }

    /**
     * Exclui um produto da base de dados com base no seu ID.
     * 
     * @param id O identificador único do produto a ser removido.
     * @return {@code true} se o produto foi removido com sucesso.
     * @throws IndexOutOfBoundsException Se o ID informado não for encontrado na lista.
     */
    public boolean deleteProdutoBD(int id) {
        int indice = this.procuraIndice(id);
        getMinhaLista().remove(indice);
        return true;
    }

    /**
     * * Atualiza os dados de um produto específico já existente na base de dados.
     * 
     * @param id            O ID do produto que será atualizado.
     * @param nome          O novo nome do produto.
     * @param precoUn       O novo preço unitário.
     * @param unidade       A nova unidade de medida.
     * @param quantidade    A nova quantidade em estoque.
     * @param quantidadeMin A nova quantidade mínima.
     * @param quantidadeMax A nova quantidade máxima.
     * @param categoria     A nova categoria do produto.
     * @return {@code true} se o produto foi atualizado com sucesso.
     * @throws IndexOutOfBoundsException Se o ID informado não for encontrado na lista.
     */
     
     
    public boolean updateProdutoBD(int id, String nome, double precoUn, String unidade, int quantidade, int quantidadeMin, int quantidadeMax, String categoria) {
        Produto objeto = new Produto(id, nome, precoUn, unidade, quantidade, quantidadeMin, quantidadeMax, categoria);
        int indice = this.procuraIndice(id);
        getMinhaLista().set(indice, objeto);
        return true;
    }

    /**
     * Método interno auxiliar que localiza a posição (índice) de um produto 
     * dentro do ArrayList utilizando o ID como critério de busca.
     * 
     * @param id O identificador único do produto buscado.
     * @return O índice do produto na lista, ou {@code -1} caso o ID não seja encontrado.
     */
    private int procuraIndice(int id) {
        int indice = -1;
        for (int i = 0; i < getMinhaLista().size(); i++) {
            if (getMinhaLista().get(i).getId() == id) {
                indice = i;
            }
        }
        return indice;
    }

    /** Carrega e retorna um objeto Produto completo localizado pelo seu ID.
     * 
     * @param id O identificador único do produto que se deseja carregar.
     * @return O objeto {@link Produto} correspondente ao ID informado.
     */
    public Produto carregaProduto(int id) {
        int indice = this.procuraIndice(id);
        return getMinhaLista().get(indice);
    }

    /**
     * Consulta e retorna o maior ID atualmente registrado na base de dados (DAO).
     * Útil para o controle de autoincremento manual.
     * 
     * @return O maior valor de ID encontrado na base de dados.
     */
    public int maiorID() {
        return ProdutoDAO.maiorID();
    }

}
