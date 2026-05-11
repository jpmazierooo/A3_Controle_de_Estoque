package model;
import dao.ProdutoDAO;
import java.util.ArrayList;

public class Produto {
    private int id;
    private String nome;
    private double precoUn;
    private int unidade;
    private int quantidade;
    private int quantidadeMin;
    private int quantidadeMax;
    private String categoria;

    public Produto() {
    }
    

    public Produto(int id, String nome, double precoUn, int unidade, int quantidade, int quantidadeMin, int quantidadeMax, String categoria) {
        this.id = id;
        this.nome = nome;
        this.precoUn = precoUn;
        this.unidade = unidade;
        this.quantidade = quantidade;
        this.quantidadeMin = quantidadeMin;
        this.quantidadeMax = quantidadeMax;
        this.categoria = categoria;
    }

    public int getId() {
        return id;
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

    public void setId(int id) {
        this.id = id;
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
    public ArrayList<Produto> getMinhaLista(){
        return ProdutoDAO.getMinhaLista();
    }
    // Cadastra novo produto
    public boolean insertProdutoBD(String nome, double precoUn, int unidade, int quantidade, int quantidadeMin, int quantidadeMax, String categoria){
        int id = ProdutoDAO.maiorID() + 1;
        Produto objeto = new Produto(id, nome, precoUn, unidade, quantidade, quantidadeMin, quantidadeMax, categoria);
        getMinhaLista().add(objeto);
        return true;
    }

}
 