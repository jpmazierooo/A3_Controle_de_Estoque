package model;

public class Categoria {

    private String nome;
    private String tamanho;
    private String embalagem;

    public Categoria(String nome, String tamanho, String embalagem) {
        this.nome = nome;
        this.tamanho = tamanho;
        this.embalagem = embalagem;
    }

    public String getNome() {
        return nome;
    }

    public String getTamanho() {
        return tamanho;
    }

    public String getEmbalagem() {
        return embalagem;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }

    public void setEmbalagem(String embalagem) {
        this.embalagem = embalagem;
    }

}
