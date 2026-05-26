# A3 - Controle de Estoque

## 👥 Integrantes

* **Pedro Cazonatti**
  RA: 10726111733
  GitHub: [cazonatti](https://github.com/cazonatti)

* **João Pedro Maziero**
  RA: 10726111499
  GitHub: [jpmazierooo](https://github.com/jpmazierooo)

* **João Pedro Pleskacznieski Marimon**
  RA: 10726112374
  GitHub: [JoaoPedroMarimon](https://github.com/JoaoPedroMarimon)

* **Derek Slater Rocha da Silva**
  RA: 10726111839
  GitHub: [dereksslater](https://github.com/dereksslater)

* **Pedro Henrique Ramos de Menezes Pacheco**
  RA: 1072617695
  GitHub: [pedropachecoh7](https://github.com/pedropachecoh7)

---

## 📌 Contexto do Sistema

O projeto consiste no desenvolvimento de um sistema de controle de estoque para uma empresa de comércio, com o objetivo de melhorar a gestão dos produtos armazenados e auxiliar no planejamento de compras.

O sistema deve permitir o cadastro completo dos produtos (inclusão, alteração, consulta e exclusão), garantindo que as informações reflitam com precisão a realidade do estoque. Cada produto possui dados como nome, preço, unidade, quantidade em estoque, limites mínimo e máximo, além de sua categoria.

Os produtos são organizados em categorias (como limpeza, enlatados, vegetais, entre outros), que também possuem características próprias, como tamanho e tipo de embalagem.

O controle do estoque ocorre por meio das movimentações de entrada e saída de produtos. A cada movimentação, o sistema atualiza automaticamente a quantidade disponível. Além disso, deve emitir alertas importantes:

* Quando a quantidade estiver abaixo do mínimo (indicando necessidade de reposição);
* Quando estiver acima do máximo (evitando compras desnecessárias).

O sistema também deve permitir o reajuste de preços dos produtos com base em um percentual informado.

Para auxiliar na tomada de decisão, o sistema deve gerar relatórios essenciais, como:

* Lista de preços dos produtos;
* Balanço físico e financeiro do estoque;
* Produtos abaixo da quantidade mínima;
* Quantidade de produtos por categoria;
* Produtos com maior entrada e saída.

O desenvolvimento será feito em grupo, utilizando como base um software modelo fornecido, e será evoluído ao longo do semestre, podendo receber ajustes e melhorias conforme novas demandas (issues) forem propostas.

---

✅ Requisitos Funcionais
RF01 - Gerenciamento de Produtos
O sistema deve permitir cadastrar, consultar, alterar e excluir produtos.

RF02 - Gerenciamento de Categorias
O sistema deve permitir cadastrar e consultar categorias de produtos.

RF03 - Controle de Estoque
O sistema deve registrar entradas e saídas de produtos, atualizando automaticamente o estoque.

RF04 - Alertas de Estoque
O sistema deve informar quando a quantidade de produtos estiver abaixo do mínimo ou acima do máximo.

RF05 - Reajuste de Preços
O sistema deve permitir reajustar preços com base em percentual informado.

RF06 - Geração de Relatórios
O sistema deve gerar relatórios de produtos, balanço financeiro, produtos abaixo do mínimo e produtos por categoria.

---

⚙️ Requisitos Não Funcionais
RNF01 - Interface Gráfica
O sistema deve possuir interface gráfica em Java Swing.

RNF02 - Banco de Dados
O sistema deve utilizar banco de dados relacional para armazenamento das informações.

RNF03 - Exportação de Relatórios
Os relatórios devem ser gerados em formato PDF.

RNF04 - Compatibilidade
O sistema deve funcionar em computadores com Java instalado.

RNF05 - Organização do Projeto
O sistema deve seguir o paradigma de orientação a objetos.

---

🛠 Tecnologias Utilizadas
Java 25 • Apache NetBeans 29 • MySQL 8.0 • Git • GitHub
