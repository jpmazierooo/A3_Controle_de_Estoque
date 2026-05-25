CREATE DATABASE IF NOT EXISTS sistema_estoque;

USE sistema_estoque;

-- Tabela usada por CategoriaDAO (todas as queries referenciam "categorias")
CREATE TABLE IF NOT EXISTS categorias (
    id        INT          PRIMARY KEY AUTO_INCREMENT,
    nome      VARCHAR(100) NOT NULL,
    tamanho   VARCHAR(20)  NOT NULL,  -- valores: Pequeno, Medio, Grande
    embalagem VARCHAR(20)  NOT NULL   -- valores: Lata, Vidro, Plástico
);

-- Tabela de produtos (model: Produto.java — categoria referencia o id de categorias)
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

-- Tabela de movimentações (model: Movimentacao.java)
CREATE TABLE IF NOT EXISTS movimentacoes (
    id             INT          AUTO_INCREMENT PRIMARY KEY,
    nome           VARCHAR(100) NOT NULL,
    tipo           VARCHAR(100) NOT NULL,
    qtd            INT          NOT NULL,
    data           DATE         NOT NULL,
    movimentacao   VARCHAR(20)  NOT NULL,  -- valores: Entrada, Saida
    status_estoque VARCHAR(20)  NOT NULL
);
