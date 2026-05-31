-- =============================================================
-- Sistema de Controle de Estoque
-- Script de criação do banco de dados
--
-- Autor: João Marimon
-- Banco:  sistema_estoque (MySQL)
--
-- Tabelas:
--   categorias    → armazena as categorias dos produtos
--   produtos      → armazena os produtos (FK para categorias)
--   movimentacoes → registra entradas e saídas do estoque
-- =============================================================

CREATE DATABASE IF NOT EXISTS sistema_estoque;

USE sistema_estoque;

-- -------------------------------------------------------------
-- Tabela: categorias
-- Usada por: CategoriaDAO.java, FrmGerenciarCategoria.java
-- -------------------------------------------------------------
CREATE TABLE IF NOT EXISTS categorias (
    id        INT          PRIMARY KEY AUTO_INCREMENT,
    nome      VARCHAR(100) NOT NULL,
    tamanho   VARCHAR(20)  NOT NULL,  -- valores: Pequeno, Medio, Grande
    embalagem VARCHAR(20)  NOT NULL   -- valores: Lata, Vidro, Plástico
);

-- -------------------------------------------------------------
-- Tabela: produtos
-- Usada por: ProdutoDAO.java, FrmGerenciarProdutos.java
-- FK: categoria_id → categorias(id)
-- -------------------------------------------------------------
CREATE TABLE IF NOT EXISTS produtos (
    id                 INT            PRIMARY KEY AUTO_INCREMENT,
    nome               VARCHAR(100)   NOT NULL,
    preco_unitario     DECIMAL(10, 2) NOT NULL,
    unidade            VARCHAR(20)    NOT NULL,  -- valores: KG, UN, L
    quantidade_estoque INT            NOT NULL,
    quantidade_minima  INT            NOT NULL,
    quantidade_maxima  INT            NOT NULL,
    categoria_id       INT            NOT NULL,
    FOREIGN KEY (categoria_id) REFERENCES categorias(id)
);

-- -------------------------------------------------------------
-- Tabela: movimentacoes
-- Usada por: MovimentacaoDAO.java, FrmMovimentacao.java
-- Registra cada entrada ou saída de produto no estoque
-- -------------------------------------------------------------
CREATE TABLE IF NOT EXISTS movimentacoes (
    id             INT          AUTO_INCREMENT PRIMARY KEY,
    nome           VARCHAR(100) NOT NULL,               -- nome do produto
    tipo           VARCHAR(100) NOT NULL,               -- unidade do produto
    qtd            INT          NOT NULL,               -- quantidade movimentada
    data           DATE         NOT NULL,               -- data da movimentação
    movimentacao   VARCHAR(20)  NOT NULL,               -- Entrada ou Saida
    status_estoque VARCHAR(20)  NOT NULL                -- Normal, Abaixo do Minimo, Acima do Maximo
);
