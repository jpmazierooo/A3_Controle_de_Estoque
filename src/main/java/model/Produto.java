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
    private int categoria;

    public Produto() {
    }

    public Produto(int id, String nome, double precoUn, int unidade, int quantidade, int quantidadeMin, int quantidadeMax, int categoria) {
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

    public int getCategoria() {
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

    public void setCategoria(int categoria) {
        this.categoria = categoria;
    }

    public ArrayList<Produto> getMinhaLista() {
        return ProdutoDAO.getMinhaLista();
    }

    // Cadastra novo produto
    public boolean insertProdutoBD(String nome, double precoUn, int unidade, int quantidade, int quantidadeMin, int quantidadeMax, int categoria) {
        int id = ProdutoDAO.maiorID() + 1;
        Produto objeto = new Produto(id, nome, precoUn, unidade, quantidade, quantidadeMin, quantidadeMax, categoria);
        getMinhaLista().add(objeto);
        return true;
    }

    // Deleta um produto pelo ID
    public boolean deleteProdutoBD(int id) {
        int indice = this.procuraIndice(id);
        getMinhaLista().remove(indice);
        return true;
    }

    //Edita um produto específico pelo seu campo ID
    public boolean updateProdutoBD(int id, String nome, double precoUn, int unidade, int quantidade, int quantidadeMin, int quantidadeMax, int categoria) {
        Produto objeto = new Produto(id, nome, precoUn, unidade, quantidade, quantidadeMin, quantidadeMax, categoria);
        int indice = this.procuraIndice(id);
        getMinhaLista().set(indice, objeto);
        return true;

    }

    // procura o INDICE de objeto da minhaListaque contem o ID enviado.
    private int procuraIndice(int id) {
        int indice = -1;
        for (int i = 0; i < getMinhaLista().size(); i++) {
            if (getMinhaLista().get(i).getId() == id) {
                indice = i;
            }
        }
        return indice;
    }
// carrega dados de um produto específico pelo seu ID

    public Produto carregaProduto(int id) {
        int indice = this.procuraIndice(id);
        return getMinhaLista().get(indice);
    }

    // retorna o maior ID da nossa base de dados
    public int maiorID() {
        return ProdutoDAO.maiorID();
    }

}
