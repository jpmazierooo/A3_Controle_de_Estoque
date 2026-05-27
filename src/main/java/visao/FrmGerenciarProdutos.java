package visao;

import modelo.Produto;
import modelo.Categoria;
import modelo.Movimentacao;
import dao.ProdutoDAO;
import dao.CategoriaDAO;
import dao.MovimentacaoDAO;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import visao.Mensagem;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 * Interface gráfica responsável pelo gerenciamento de produtos do sistema.
 * Permite realizar o cadastro, edição, exclusão, listagem de produtos, além do
 * controle de entrada e saída do estoque com validações de limites mínimos e
 * máximos.
 *
 * @author João Pedro Maziero
 */
public class FrmGerenciarProdutos extends javax.swing.JFrame {

    /**
     * Objeto de modelo utilizado para interagir com os dados e listas de
     * produtos.
     */
    private Produto objetoProduto;

    /**
     * Lista em cache que armazena as categorias cadastradas no sistema.
     */
    private List<Categoria> categorias = new ArrayList<>();

    /**
     * Construtor padrão da interface. Inicializa os componentes do formulário,
     * centraliza a janela no meio da tela, instancia o objeto do modelo e
     * popula os dados iniciais de categorias e tabela.
     */
    public FrmGerenciarProdutos() {
        initComponents();
        setLocationRelativeTo(null);
        this.objetoProduto = new Produto();
        carregarCategorias();
        this.carregaTabela();
        b_Entrada.addActionListener(this::b_EntradaActionPerformed);
    }

    /**
     * Lógica comum de Entrada e Saída: valida, atualiza estoque, registra
     * movimentação e exibe avisos.
     *
     * @param tipoMovimentacao "Entrada" ou "Saida"
     */
    private void processarMovimentacao(String tipoMovimentacao) {
        int linhaSelecionada = jTableGerenciar.getSelectedRow();
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(null, "Selecione um produto na tabela.");
            return;
        }
        String qtdTexto = jTextField1.getText().trim();
        if (qtdTexto.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Informe a quantidade no campo ao lado.");
            return;
        }
        int qtd;
        try {
            qtd = Integer.parseInt(qtdTexto);
            if (qtd <= 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Quantidade deve ser um número maior que zero.");
            return;
        }

        int id = Integer.parseInt(jTableGerenciar.getValueAt(linhaSelecionada, 0).toString());
        String nome = jTableGerenciar.getValueAt(linhaSelecionada, 1).toString();
        int quantidadeAtual = Integer.parseInt(jTableGerenciar.getValueAt(linhaSelecionada, 4).toString());
        int quantidadeMin = Integer.parseInt(jTableGerenciar.getValueAt(linhaSelecionada, 5).toString());
        int quantidadeMax = Integer.parseInt(jTableGerenciar.getValueAt(linhaSelecionada, 6).toString());
        String categoria = jTableGerenciar.getValueAt(linhaSelecionada, 7).toString();

        int novaQuantidade;
        if (tipoMovimentacao.equals("Entrada")) {
            novaQuantidade = quantidadeAtual + qtd;
        } else {
            novaQuantidade = quantidadeAtual - qtd;
            if (novaQuantidade < 0) {
                JOptionPane.showMessageDialog(null, "Estoque insuficiente para essa saída.");
                return;
            }
        }

        String statusEstoque;
        String aviso = null;
        if (tipoMovimentacao.equals("Entrada") && novaQuantidade > quantidadeMax) {
            statusEstoque = "Acima do maximo";
            aviso = "Atenção: estoque acima da quantidade máxima após essa entrada!";
        } else if (tipoMovimentacao.equals("Saida") && novaQuantidade < quantidadeMin) {
            statusEstoque = "Abaixo do minimo";
            aviso = "Atenção: estoque abaixo da quantidade mínima após essa saída!";
        } else {
            statusEstoque = "Estoque normal";
        }

        try {
            new ProdutoDAO().atualizarQuantidade(id, novaQuantidade);

            Movimentacao mov = new Movimentacao(
                    0, nome, categoria, qtd,
                    LocalDate.now().toString(),
                    tipoMovimentacao, statusEstoque
            );
            new MovimentacaoDAO().inserir(mov);

            if (aviso != null) {
                JOptionPane.showMessageDialog(null, aviso, "Aviso de Estoque", JOptionPane.WARNING_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, tipoMovimentacao + " registrada com sucesso!");
            }
            jTextField1.setText("");
            carregaTabela();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao registrar movimentação: " + e.getMessage());
        }
    }

    /**
     * Manipulador de evento para o clique no botão de Entrada de Estoque.
     *
     * @param evt O evento de ação gerado pelo botão.
     */
    private void b_EntradaActionPerformed(java.awt.event.ActionEvent evt) {
        processarMovimentacao("Entrada");
    }

    /**
     * Carrega a lista de categorias do banco de dados através do CategoriaDAO e
     * popula o componente visual ComboBox de categorias.
     */
    private void carregarCategorias() {
        try {
            categorias = new CategoriaDAO().listarTodos();
            cb_Categoria.removeAllItems();
            for (Categoria c : categorias) {
                cb_Categoria.addItem(c.getNome());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar categorias: " + e.getMessage());
        }
    }

    /**
     * Retorna o nome da categoria com base no ID fornecido buscando na lista em
     * cache. Caso o ID não seja encontrado na lista local, retorna a
     * representação textual do próprio ID.
     *
     * @param id O identificador numérico da categoria.
     * @return O nome correspondente da categoria ou o ID convertido em string.
     */
    private String getNomeCategoria(int id) {
        for (Categoria c : categorias) {
            if (c.getId() == id) {
                return c.getNome();
            }
        }
        return String.valueOf(id);
    }

    /**
     * Recupera o ID da categoria que se encontra atualmente selecionada no
     * ComboBox.
     *
     * @return O ID da categoria selecionada, ou -1 se nenhuma seleção for
     * válida.
     */
    private int getIdCategoriaSelecionada() {
        int idx = cb_Categoria.getSelectedIndex();
        if (idx >= 0 && idx < categorias.size()) {
            return categorias.get(idx).getId();
        }
        return -1;
    }

    /**
     * Inicializa alternativamente o formulário de gerenciamento de produtos.
     * Reinicializa os componentes e limpa/instancia um novo modelo de Produto.
     */
    public void GerenciaProduto() {
        initComponents();
        this.objetoProduto = new Produto();
    }

    /**
     * Limpa as linhas atuais e atualiza os dados da tabela visual
     * (`jTableGerenciar`). Consome os dados da lista retornada pelo modelo e
     * converte as chaves estrangeiras de categorias em nomes amigáveis para
     * exibição.
     */
    public void carregaTabela() {
        DefaultTableModel modelo = (DefaultTableModel) this.jTableGerenciar.getModel();
        modelo.setNumRows(0);
        ArrayList<Produto> minhaLista = objetoProduto.getMinhaLista();
        for (Produto a : minhaLista) {
            modelo.addRow(new Object[]{
                a.getId(),
                a.getNome(),
                a.getPrecoUn(),
                a.getUnidade(),
                a.getQuantidade(),
                a.getQuantidadeMin(),
                a.getQuantidadeMax(),
                getNomeCategoria(a.getCategoriaId())});
        }
    }

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(FrmGerenciarProdutos.class.getName());

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        c_NomeProd = new javax.swing.JTextField();
        c_PrecoUN = new javax.swing.JTextField();
        c_QtdEstoque = new javax.swing.JTextField();
        cb_Unidade = new javax.swing.JComboBox<>();
        c_QtdMin = new javax.swing.JTextField();
        c_QtdMax = new javax.swing.JTextField();
        cb_Categoria = new javax.swing.JComboBox<>();
        b_Cadastrar = new javax.swing.JButton();
        b_Editar = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableGerenciar = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        b_Entrada = new javax.swing.JButton();
        b_Saída = new javax.swing.JButton();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Gerenciamento de Produtos");

        jLabel1.setText("Nome:");

        jLabel2.setText("Preço UN:");

        jLabel3.setText("Unidade:");

        jLabel4.setText("Qtd. Estoque:");

        jLabel5.setText("Qtd. Mínima:");

        jLabel6.setText("Qtd. Máxima:");

        jLabel7.setText("Categoria: ");

        c_NomeProd.addActionListener(this::c_NomeProdActionPerformed);

        c_PrecoUN.addActionListener(this::c_PrecoUNActionPerformed);

        c_QtdEstoque.addActionListener(this::c_QtdEstoqueActionPerformed);

        cb_Unidade.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "KG", "UN", "L" }));
        cb_Unidade.addActionListener(this::cb_UnidadeActionPerformed);

        c_QtdMin.addActionListener(this::c_QtdMinActionPerformed);

        c_QtdMax.addActionListener(this::c_QtdMaxActionPerformed);

        cb_Categoria.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Comida", "Bebida", "Material de Limpeza", " " }));

        b_Cadastrar.setText("Cadastrar");
        b_Cadastrar.addActionListener(this::b_CadastrarActionPerformed);

        b_Editar.setText("Editar");
        b_Editar.addActionListener(this::b_EditarActionPerformed);

        jButton1.setText("Apagar");
        jButton1.addActionListener(this::jButton1ActionPerformed);

        jButton2.setText("Voltar");
        jButton2.addActionListener(this::jButton2ActionPerformed);

        jTableGerenciar.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nome", "Preço UN", "Unidade", "Qtd. Estoque", "Qtd. Mín.", "Qtd. Máx.", "Categoria"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableGerenciar.setAlignmentX(0.6F);
        jTableGerenciar.getTableHeader().setReorderingAllowed(false);
        jTableGerenciar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableGerenciarMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTableGerenciar);
        if (jTableGerenciar.getColumnModel().getColumnCount() > 0) {
            jTableGerenciar.getColumnModel().getColumn(0).setPreferredWidth(60);
            jTableGerenciar.getColumnModel().getColumn(1).setPreferredWidth(55);
            jTableGerenciar.getColumnModel().getColumn(2).setPreferredWidth(28);
            jTableGerenciar.getColumnModel().getColumn(3).setPreferredWidth(30);
            jTableGerenciar.getColumnModel().getColumn(4).setPreferredWidth(30);
            jTableGerenciar.getColumnModel().getColumn(5).setPreferredWidth(30);
        }

        jLabel8.setText("Entrada e Saída Estoque:");

        jTextField1.addActionListener(this::jTextField1ActionPerformed);

        b_Entrada.setText("Entrada");

        b_Saída.setText("Saída");
        b_Saída.addActionListener(this::b_SaídaActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(59, 59, 59)
                        .addComponent(b_Cadastrar)
                        .addGap(18, 18, 18)
                        .addComponent(b_Editar)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(59, 59, 59)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel4))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(19, 19, 19)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(c_NomeProd, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(c_PrecoUN, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(c_QtdEstoque, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(cb_Unidade, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(c_QtdMin, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(c_QtdMax, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(cb_Categoria, 0, 1, Short.MAX_VALUE)
                                                .addGap(114, 114, 114)))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8)
                                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(b_Entrada, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(b_Saída, javax.swing.GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE))))
                                .addGap(75, 75, 75))
                            .addComponent(jLabel7)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 638, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(19, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(39, 39, 39)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel1)
                                    .addComponent(c_NomeProd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel8)
                                .addGap(3, 3, 3)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel2)
                                    .addComponent(c_PrecoUN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel3)
                                    .addComponent(cb_Unidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel4)
                                    .addComponent(c_QtdEstoque, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(b_Entrada)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(b_Saída)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(c_QtdMin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(c_QtdMax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(cb_Categoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(b_Cadastrar)
                    .addComponent(b_Editar)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap(26, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void c_PrecoUNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_c_PrecoUNActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_c_PrecoUNActionPerformed

    private void c_QtdEstoqueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_c_QtdEstoqueActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_c_QtdEstoqueActionPerformed

    private void c_QtdMinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_c_QtdMinActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_c_QtdMinActionPerformed

    private void c_QtdMaxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_c_QtdMaxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_c_QtdMaxActionPerformed

    private void cb_UnidadeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb_UnidadeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cb_UnidadeActionPerformed
    /**
     * Fecha a janela de gerenciamento de produtos atual liberando seus
     * recursos.
     *
     * @param evt O evento de clique gerado pelo botão "Sair".
     */
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        this.dispose();        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    /**
     * Dispara o processamento de uma movimentação com a flag de "Saida".
     *
     * @param evt O evento de clique do botão "Saída".
     */
    private void b_SaídaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_SaídaActionPerformed
        processarMovimentacao("Saida");
    }//GEN-LAST:event_b_SaídaActionPerformed

    private void c_NomeProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_c_NomeProdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_c_NomeProdActionPerformed

    /**
     * Captura os dados da tela gráfica, executa as validações de consistência e
     * tamanho de strings, e chama o método de inserção de produto no banco de
     * dados através da classe de modelo. Limpa os campos da tela se a operação
     * for bem-sucedida.
     *
     * @param evt O evento de clique associado ao botão "Cadastrar".
     */
    private void b_CadastrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_CadastrarActionPerformed
        try {
            String nome = "";
            double precoUn = 0;
            String unidade = "";
            int quantidade = 0;
            int quantidadeMin = 0;
            int quantidadeMax = 0;
            quantidade = Integer.parseInt(this.c_QtdEstoque.getText());
            if (this.c_NomeProd.getText().length() < 2) {
                throw new Mensagem("Nome deve conter ao menos 2 caracteres.");
            } else {
                nome = this.c_NomeProd.getText();
            }

            if (this.c_PrecoUN.getText().length() <= 0) {
                throw new Mensagem("O Preço Unitário deve ser número e maior que zero.");
            } else {
                precoUn = Double.parseDouble(this.c_PrecoUN.getText());
            }
            if (this.c_QtdMax.getText().length() <= 0) {
                throw new Mensagem("A Quantidade Máxima deve ser número e maior que zero.");
            } else {
                quantidadeMax = Integer.parseInt(this.c_QtdMax.getText());
            }
            if (this.c_QtdMin.getText().length() <= 0) {
                throw new Mensagem("A Quantidade Mínima deve ser número e maior que zero.");
            } else {
                quantidadeMin = Integer.parseInt(this.c_QtdMin.getText());
            }
            unidade = this.cb_Unidade.getSelectedItem().toString();
            int categoriaId = getIdCategoriaSelecionada();
            if (categoriaId == -1) {
                throw new Mensagem("Selecione uma categoria válida.");
            }
            ArrayList<Produto> listaAtual = objetoProduto.getMinhaLista();
            for (Produto p : listaAtual) {
                if (p.getNome().equalsIgnoreCase(nome)
                        && p.getUnidade().equalsIgnoreCase(unidade)
                        && p.getCategoriaId() == categoriaId) {
                    JOptionPane.showMessageDialog(null, "Já existe um produto com esse nome, unidade e categoria!");
                    return;
                }
            }
            // envia os dados para o DAO cadastrar
            if (this.objetoProduto.insertProdutoBD(nome, precoUn, unidade, quantidade, quantidadeMin, quantidadeMax, categoriaId)) {
                JOptionPane.showMessageDialog(null, "Produto Cadastrado com Sucesso!");
            } // limpa campos da interface
            this.c_NomeProd.setText("");
            this.c_PrecoUN.setText("");
            this.cb_Unidade.setSelectedIndex(0);
            this.c_QtdEstoque.setText("");
            this.c_QtdMin.setText("");
            this.c_QtdMax.setText("");
            this.cb_Categoria.setSelectedIndex(0);

        } catch (Mensagem erro) {
            JOptionPane.showMessageDialog(null, erro.getMessage());
        } catch (NumberFormatException erro2) {
            JOptionPane.showMessageDialog(null, "Informe um número válido.");
        } finally {
            carregaTabela();
        }

    }//GEN-LAST:event_b_CadastrarActionPerformed

    /**
     * Detecta a linha selecionada na tabela quando o usuário clica com o mouse,
     * recupera as informações das colunas e preenche automaticamente os campos
     * de entrada do formulário.
     *
     * @param evt O evento de clique de mouse gerado pela tabela
     * `jTableGerenciar`.
     */
    private void jTableGerenciarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableGerenciarMouseClicked
        if (this.jTableGerenciar.getSelectedRow() != -1) {
            String nome = this.jTableGerenciar.getValueAt(this.jTableGerenciar.getSelectedRow(), 1).toString();
            String precoUn = this.jTableGerenciar.getValueAt(this.jTableGerenciar.getSelectedRow(), 2).toString();
            String unidade = this.jTableGerenciar.getValueAt(this.jTableGerenciar.getSelectedRow(), 3).toString();
            String quantidade = this.jTableGerenciar.getValueAt(this.jTableGerenciar.getSelectedRow(), 4).toString();
            String quantidadeMin = this.jTableGerenciar.getValueAt(this.jTableGerenciar.getSelectedRow(), 5).toString();
            String quantidadeMax = this.jTableGerenciar.getValueAt(this.jTableGerenciar.getSelectedRow(), 6).toString();
            String categoria = this.jTableGerenciar.getValueAt(this.jTableGerenciar.getSelectedRow(), 7).toString();

            String nomeCategoria = this.jTableGerenciar.getValueAt(this.jTableGerenciar.getSelectedRow(), 7).toString();
            this.c_NomeProd.setText(nome);
            this.c_PrecoUN.setText(precoUn);
            this.cb_Unidade.setSelectedItem(unidade);
            this.c_QtdEstoque.setText(quantidade);
            this.c_QtdMin.setText(quantidadeMin);
            this.c_QtdMax.setText(quantidadeMax);
            this.cb_Categoria.setSelectedItem(nomeCategoria);
        }
    }//GEN-LAST:event_jTableGerenciarMouseClicked

    /**
     * Resgata as informações atualizadas dos campos de texto, valida as regras
     * de negócio e o ID do produto baseado na linha ativa da tabela, repassando
     * o comando de alteração para o BD.
     *
     * @param evt O evento de clique originado do botão "Editar".
     */
    private void b_EditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_EditarActionPerformed
        try {
            int id = 0;
            String nome = "";
            double precoUn = 0;
            String unidade = "";
            int quantidade = 0;
            int quantidadeMin = 0;
            int quantidadeMax = 0;
            quantidade = Integer.parseInt(this.c_QtdEstoque.getText());
            if (this.c_NomeProd.getText().length() < 2) {
                throw new Mensagem("Nome deve conter ao menos 2 caracteres.");
            } else {
                nome = this.c_NomeProd.getText();
            }
            if (this.c_PrecoUN.getText().length() <= 0) {
                throw new Mensagem("O Preço Unitário deve ser número e maior que zero.");
            } else {
                precoUn = Double.parseDouble(this.c_PrecoUN.getText());
            }
            if (this.c_QtdMax.getText().length() <= 0) {
                throw new Mensagem("A Quantidade Máxima deve ser número e maior que zero.");
            } else {
                quantidadeMax = Integer.parseInt(this.c_QtdMax.getText());
            }
            if (this.c_QtdMin.getText().length() <= 0) {
                throw new Mensagem("A Quantidade Mínima deve ser número e maior que zero.");
            } else {
                quantidadeMin = Integer.parseInt(this.c_QtdMin.getText());
            }
            if (this.jTableGerenciar.getSelectedRow() == -1) {
                throw new Mensagem("Primeiro Selecione um Produto para Alterar");
            } else {
                id = Integer.parseInt(this.jTableGerenciar.getValueAt(this.jTableGerenciar.getSelectedRow(), 0).toString());
            }
            unidade = this.cb_Unidade.getSelectedItem().toString();
            int categoriaId = getIdCategoriaSelecionada();
            if (categoriaId == -1) {
                throw new Mensagem("Selecione uma categoria válida.");
            }

            // envia os dados para o DAO editar
            if (this.objetoProduto.updateProdutoBD(id, nome, precoUn, unidade, quantidade, quantidadeMin, quantidadeMax, categoriaId)) {
                JOptionPane.showMessageDialog(null, "Produto Editado com Sucesso!");
            } // limpa campos da interface
            this.c_NomeProd.setText("");
            this.c_PrecoUN.setText("");
            this.cb_Unidade.setSelectedIndex(0);
            this.c_QtdEstoque.setText("");
            this.c_QtdMin.setText("");
            this.c_QtdMax.setText("");
            this.cb_Categoria.setSelectedIndex(0);

        } catch (Mensagem erro) {
            JOptionPane.showMessageDialog(null, erro.getMessage());
        } catch (NumberFormatException erro2) {
            JOptionPane.showMessageDialog(null, "Informe um número válido.");
        } finally {
            carregaTabela();
        }

    }//GEN-LAST:event_b_EditarActionPerformed

    /**
     * Obtém o identificador numérico da linha selecionada na tabela e exibe uma
     * caixa de diálogo para confirmação de deleção. Executa o comando de
     * deleção no banco e limpa os controles.
     *
     * @param evt O evento de clique gerado pelo botão "Apagar".
     */
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            // validando dados da interface gráfica.
            int id = 0;
            if (this.jTableGerenciar.getSelectedRow() == -1) {
                throw new Mensagem("Primeiro Selecione um Produto para APAGAR");
            } else {
                id = Integer.parseInt(this.jTableGerenciar.getValueAt(this.jTableGerenciar.getSelectedRow(), 0).toString());
            }
            // retorna 0 -> primeiro botão | 1 -> segundo botão | 2 -> terceiro botão
            int respostaUsuario = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja apagar este Produto ?");
            if (respostaUsuario == 0) {
                // clicou em SIM
                // envia os dados para o Aluno processar
                if (this.objetoProduto.deleteProdutoBD(id)) {
                    // limpa os campos
                    this.c_NomeProd.setText("");
                    this.c_PrecoUN.setText("");
                    this.cb_Unidade.setSelectedIndex(0);
                    this.c_QtdEstoque.setText("");
                    this.c_QtdMin.setText("");
                    this.c_QtdMax.setText("");
                    this.cb_Categoria.setSelectedIndex(0);
                    JOptionPane.showMessageDialog(rootPane, "Produto Apagado com Sucesso!");
                }
            }
            // atualiza a tabela.
            System.out.println(this.objetoProduto.getMinhaLista().toString());
        } catch (Mensagem erro) {
            JOptionPane.showMessageDialog(null, erro.getMessage());
        } finally {
            // atualiza a tabela.
            carregaTabela();
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new FrmGerenciarProdutos().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton b_Cadastrar;
    private javax.swing.JButton b_Editar;
    private javax.swing.JButton b_Entrada;
    private javax.swing.JButton b_Saída;
    private javax.swing.JTextField c_NomeProd;
    private javax.swing.JTextField c_PrecoUN;
    private javax.swing.JTextField c_QtdEstoque;
    private javax.swing.JTextField c_QtdMax;
    private javax.swing.JTextField c_QtdMin;
    private javax.swing.JComboBox<String> cb_Categoria;
    private javax.swing.JComboBox<String> cb_Unidade;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTableGerenciar;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
