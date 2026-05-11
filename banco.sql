CREATE DATABASE IF NOT EXISTS sistema_estoque;

USE sistema_estoque;

-- Criação da tabela produto
CREATE TABLE categoria (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    tamanho ENUM('PEQUENO', 'MEDIO', 'GRANDE') NOT NULL,
    embalagem ENUM('LATA', 'VIDRO', 'PLASTICO') NOT NULL
);

-- Criação da tabela produto
CREATE TABLE IF NOT EXISTS produto (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    preco_unitario DECIMAL(10,2) NOT NULL,
    unidade VARCHAR(20) NOT NULL,
    quantidade_estoque INT NOT NULL,
    quantidade_minima INT NOT NULL,
    quantidade_maxima INT NOT NULL,
    categoria VARCHAR(100) NOT NULL
);

-- Criação da tabela registro
CREATE TABLE IF NOT EXISTS registro (
    id INT AUTO_INCREMENT PRIMARY KEY,
    data DATE NOT NULL,
    tipo VARCHAR(100) NOT NULL,
    quantidade INT NOT NULL,
    movimentacao VARCHAR(20) NOT NULL,
    status VARCHAR(20) NOT NULL
);