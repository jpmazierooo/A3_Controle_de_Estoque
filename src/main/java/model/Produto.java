package model;

public class Produto {

    private String nome;
    private double precoUn;
    private int unidade;
    private int quantidade;
    private int quantidadeMin;
    private int quantidadeMax;
    private String categoria;

    public Produto(String nome, double precoUn, int unidade, int quantidade, int quantidadeMin, int quantidadeMax, String categoria) {
        this.nome = nome;
        this.precoUn = precoUn;
        this.unidade = unidade;
        this.quantidade = quantidade;
        this.quantidadeMin = quantidadeMin;
        this.quantidadeMax = quantidadeMax;
        this.categoria = categoria;
    }

    public String getNome() {
        return nome;
    }

    public double getPrecoUn() {
        return precoUn;
    }

    public int getUnidade() {
        return unidade;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public int getQuantidadeMin() {
        return quantidadeMin;
    }

    public int getQuantidadeMax() {
        return quantidadeMax;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setPrecoUn(double precoUn) {
        this.precoUn = precoUn;
    }

    public void setUnidade(int unidade) {
        this.unidade = unidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public void setQuantidadeMin(int quantidadeMin) {
        this.quantidadeMin = quantidadeMin;
    }

    public void setQuantidadeMax(int quantidadeMax) {
        this.quantidadeMax = quantidadeMax;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

}
 