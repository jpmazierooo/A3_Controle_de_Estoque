package modelo;

/**
 * Classe que representa uma movimentação de estoque.
 */
public class Movimentacao {

    private int id;
    private String nome;
    private String tipo;
    private int qtd;
    private String data;
    private String movimentacao;
    private String statusEstoque;

    /**
     * Construtor utilizado para criar uma movimentação com todos os dados.
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
    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setQtd(int qtd) {
        this.qtd = qtd;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setMovimentacao(String movimentacao) {
        this.movimentacao = movimentacao;
    }

    public void setStatusEstoque(String statusEstoque) {
        this.statusEstoque = statusEstoque;
    }

    // Métodos getters: responsáveis por retornar os valores dos atributos.
    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getTipo() {
        return tipo;
    }

    public int getQtd() {
        return qtd;
    }

    public String getData() {
        return data;
    }

    public String getMovimentacao() {
        return movimentacao;
    }

    public String getStatusEstoque() {
        return statusEstoque;
    }

    /**
     * Construtor vazio utilizado para criação de objetos sem valores iniciais.
     */
    public Movimentacao() {
    }

    /**
     * Verifica se a movimentação é do tipo Entrada.
     */
    public boolean isEntrada() {
        return movimentacao.equalsIgnoreCase("Entrada");

    }

    /**
     * Verifica se a movimentação é do tipo Saída.
     */
    public boolean isSaida() {
        return movimentacao.equalsIgnoreCase("Saida");

    }

}
