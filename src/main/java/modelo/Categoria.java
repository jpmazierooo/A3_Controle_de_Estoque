package modelo;

/**
 * Representa uma categoria de produtos do sistema de estoque.
 * <p>
 * Cada categoria possui um nome, tamanho e tipo de embalagem. O identificador
 * (id) é gerado automaticamente pelo banco de dados.
 * </p>
 *
 * @author Pedro Henrique
 */
public class Categoria {

    /**
     * Identificador único da categoria, gerado pelo banco de dados.
     */
    private int id;
    /**
     * Nome da categoria.
     */
    private String nome;
    /**
     * Tamanho da categoria. Valores esperados: Pequeno, Medio, Grande.
     */
    private String tamanho;
    /**
     * Tipo de embalagem. Valores esperados: Lata, Vidro, Plástico.
     */
    private String embalagem;

    /**
     * Construtor completo utilizado ao recuperar uma categoria do banco de
     * dados.
     *
     * @param id identificador único da categoria
     * @param nome nome da categoria
     * @param tamanho tamanho da categoria (Pequeno, Medio, Grande)
     * @param embalagem tipo de embalagem (Lata, Vidro, Plástico)
     */
    public Categoria(int id, String nome, String tamanho, String embalagem) {
        this.id = id;
        this.nome = nome;
        this.tamanho = tamanho;
        this.embalagem = embalagem;
    }

    /**
     * Construtor sem id, utilizado ao cadastrar uma nova categoria. O id será
     * gerado automaticamente pelo banco de dados.
     *
     * @param nome nome da categoria
     * @param tamanho tamanho da categoria (Pequeno, Medio, Grande)
     * @param embalagem tipo de embalagem (Lata, Vidro, Plástico)
     */
    public Categoria(String nome, String tamanho, String embalagem) {
        this.nome = nome;
        this.tamanho = tamanho;
        this.embalagem = embalagem;
    }

    /**
     * Retorna o identificador único da categoria.
     *
     * @return id da categoria
     */
    public int getId() {
        return id;
    }

    /**
     * Retorna o nome da categoria.
     *
     * @return nome da categoria
     */
    public String getNome() {
        return nome;
    }

    /**
     * Retorna o tamanho da categoria.
     *
     * @return tamanho da categoria
     */
    public String getTamanho() {
        return tamanho;
    }

    /**
     * Retorna o tipo de embalagem da categoria.
     *
     * @return embalagem da categoria
     */
    public String getEmbalagem() {
        return embalagem;
    }

    /**
     * Define o identificador da categoria.
     *
     * @param id novo id da categoria
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Define o nome da categoria.
     *
     * @param nome novo nome da categoria
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Define o tamanho da categoria.
     *
     * @param tamanho novo tamanho da categoria (Pequeno, Medio, Grande)
     */
    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }

    /**
     * Define o tipo de embalagem da categoria.
     *
     * @param embalagem novo tipo de embalagem (Lata, Vidro, Plástico)
     */
    public void setEmbalagem(String embalagem) {
        this.embalagem = embalagem;
    }

}
