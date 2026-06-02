package modelo;
 
/**
 * Representa uma movimentação de estoque.
 *
 * <p>Uma movimentação armazena os dados de uma operação de entrada ou saída
 * de um item do estoque, incluindo o nome do item, seu tipo, a quantidade
 * envolvida, a data, o tipo de movimentação (entrada ou saída) e o status
 * resultante do estoque.</p>
 *
 * @see #isEntrada()
 * @see #isSaida()
 */
public class Movimentacao {
 
    /** Identificador único da movimentação. */
    private int id;
 
    /** Nome do item movimentado. */
    private String nome;
 
    /** Tipo/categoria do item movimentado. */
    private String tipo;
 
    /** Quantidade de itens envolvidos na movimentação. */
    private int qtd;
 
    /** Data em que a movimentação foi realizada. */
    private String data;
 
    /** Tipo da movimentação (por exemplo, {@code "Entrada"} ou {@code "Saida"}). */
    private String movimentacao;
 
    /** Status do estoque após a movimentação. */
    private String statusEstoque;
 
    /**
     * Cria uma movimentação com todos os dados preenchidos.
     *
     * @param id            identificador único da movimentação
     * @param nome          nome do item movimentado
     * @param tipo          tipo/categoria do item
     * @param qtd           quantidade de itens movimentados
     * @param data          data da movimentação
     * @param movimentacao  tipo da movimentação ({@code "Entrada"} ou {@code "Saida"})
     * @param statusEstoque status do estoque após a movimentação
     */
    public Movimentacao(int id, String nome, String tipo, int qtd, String data, String movimentacao, String statusEstoque) {
        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
        this.qtd = qtd;
        this.data = data;
        this.movimentacao = movimentacao;
        this.statusEstoque = statusEstoque;
    }
 
    // Métodos setters: responsáveis por alterar os atributos do objeto.
 
    /**
     * Define o identificador da movimentação.
     *
     * @param id o identificador a ser atribuído
     */
    public void setId(int id) {
        this.id = id;
    }
 
    /**
     * Define o nome do item movimentado.
     *
     * @param nome o nome a ser atribuído
     */
    public void setNome(String nome) {
        this.nome = nome;
    }
 
    /**
     * Define o tipo/categoria do item movimentado.
     *
     * @param tipo o tipo a ser atribuído
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
 
    /**
     * Define a quantidade de itens movimentados.
     *
     * @param qtd a quantidade a ser atribuída
     */
    public void setQtd(int qtd) {
        this.qtd = qtd;
    }
 
    /**
     * Define a data da movimentação.
     *
     * @param data a data a ser atribuída
     */
    public void setData(String data) {
        this.data = data;
    }
 
    /**
     * Define o tipo da movimentação.
     *
     * @param movimentacao o tipo de movimentação a ser atribuído
     *                     ({@code "Entrada"} ou {@code "Saida"})
     */
    public void setMovimentacao(String movimentacao) {
        this.movimentacao = movimentacao;
    }
 
    /**
     * Define o status do estoque após a movimentação.
     *
     * @param statusEstoque o status a ser atribuído
     */
    public void setStatusEstoque(String statusEstoque) {
        this.statusEstoque = statusEstoque;
    }
 
    // Métodos getters: responsáveis por retornar os valores dos atributos.
 
    /**
     * Retorna o identificador da movimentação.
     *
     * @return o identificador único
     */
    public int getId() {
        return id;
    }
 
    /**
     * Retorna o nome do item movimentado.
     *
     * @return o nome do item
     */
    public String getNome() {
        return nome;
    }
 
    /**
     * Retorna o tipo/categoria do item movimentado.
     *
     * @return o tipo do item
     */
    public String getTipo() {
        return tipo;
    }
 
    /**
     * Retorna a quantidade de itens movimentados.
     *
     * @return a quantidade
     */
    public int getQtd() {
        return qtd;
    }
 
    /**
     * Retorna a data da movimentação.
     *
     * @return a data
     */
    public String getData() {
        return data;
    }
 
    /**
     * Retorna o tipo da movimentação.
     *
     * @return o tipo de movimentação ({@code "Entrada"} ou {@code "Saida"})
     */
    public String getMovimentacao() {
        return movimentacao;
    }
 
    /**
     * Retorna o status do estoque após a movimentação.
     *
     * @return o status do estoque
     */
    public String getStatusEstoque() {
        return statusEstoque;
    }
 
    /**
     * Cria uma movimentação sem valores iniciais.
     *
     * <p>Os atributos devem ser preenchidos posteriormente por meio dos
     * métodos {@code set} correspondentes.</p>
     */
    public Movimentacao() {
    }
 
    /**
     * Verifica se a movimentação é do tipo Entrada.
     *
     * <p>A comparação é feita de forma insensível a maiúsculas e minúsculas
     * com o valor {@code "Entrada"}.</p>
     *
     * @return {@code true} se a movimentação for uma entrada; {@code false} caso contrário
     * @throws NullPointerException se o tipo de movimentação não tiver sido definido
     */
    public boolean isEntrada() {
        return movimentacao.equalsIgnoreCase("Entrada");
    }
 
    /**
     * Verifica se a movimentação é do tipo Saída.
     *
     * <p>A comparação é feita de forma insensível a maiúsculas e minúsculas
     * com o valor {@code "Saida"}.</p>
     *
     * @return {@code true} se a movimentação for uma saída; {@code false} caso contrário
     * @throws NullPointerException se o tipo de movimentação não tiver sido definido
     */
    public boolean isSaida() {
        return movimentacao.equalsIgnoreCase("Saida");
    }
}