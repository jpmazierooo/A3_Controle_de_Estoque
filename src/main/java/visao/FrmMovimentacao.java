package visao;

import dao.MovimentacaoDAO;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import modelo.Movimentacao;


/**
 * Interface gráfica responsável pela exibição das movimentações de estoque do
 * sistema. Apresenta, em uma tabela, todas as movimentações cadastradas
 * (entradas e saídas) e disponibiliza um botão para retorno ao menu principal.
 *
 * @author [Seu Nome]
 */
public class FrmMovimentacao extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(FrmMovimentacao.class.getName());

    /**
     * Objeto de acesso a dados utilizado para recuperar as movimentações
     * cadastradas no banco de dados.
     */
    private MovimentacaoDAO movimentacaoDAO = new MovimentacaoDAO();

    /**
     * Construtor padrão da interface. Inicializa os componentes do formulário,
     * impede o redimensionamento da janela, popula a tabela com as
     * movimentações, centraliza a janela no meio da tela e ajusta a largura
     * preferencial de cada coluna da tabela.
     */
    public FrmMovimentacao() {

        initComponents();
        setResizable(false);
        carregarTabela();
        setLocationRelativeTo(null);
        jTable3.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);

        jTable3.getColumnModel().getColumn(0).setPreferredWidth(120);
        jTable3.getColumnModel().getColumn(1).setPreferredWidth(50);
        jTable3.getColumnModel().getColumn(2).setPreferredWidth(100);
        jTable3.getColumnModel().getColumn(3).setPreferredWidth(60);
        jTable3.getColumnModel().getColumn(4).setPreferredWidth(100);
        jTable3.getColumnModel().getColumn(5).setPreferredWidth(120);
        jTable3.getColumnModel().getColumn(6).setPreferredWidth(160);
    }

    /**
     * Limpa as linhas atuais e atualiza os dados da tabela visual
     * (`jTable3`). Consome os dados da lista retornada pelo MovimentacaoDAO e
     * adiciona uma linha para cada movimentação encontrada. Caso ocorra algum
     * erro durante a operação, exibe uma mensagem ao usuário.
     */
    private void carregarTabela() {
        DefaultTableModel modelo = (DefaultTableModel) jTable3.getModel();
        modelo.setRowCount(0);
        try {
            for (Movimentacao m : movimentacaoDAO.listarMovimentacoes()) {
                modelo.addRow(new Object[]{
                    m.getNome(),
                    m.getId(),
                    m.getTipo(),
                    m.getQtd(),
                    m.getData(),
                    m.getMovimentacao(),
                    m.getStatusEstoque()
                });
            }
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(null, "Erro ao carregar movimentações: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        btnVoltar = new javax.swing.JButton();

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

        jLabel6.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("MOVIMENTAÇÃO DE ESTOQUE");

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nome", "ID", "Tipo", "Qtd", "Data", "Movimentação", "Status Estoque"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable3.getTableHeader().setResizingAllowed(false);
        jTable3.getTableHeader().setReorderingAllowed(false);
        jScrollPane3.setViewportView(jTable3);

        btnVoltar.setText("Voltar");
        btnVoltar.addActionListener(this::btnVoltarActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(68, 68, 68)
                        .addComponent(jLabel6))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(222, 222, 222)
                        .addComponent(btnVoltar)))
                .addGap(91, 91, 91))
            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 522, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(145, 145, 145)
                .addComponent(btnVoltar)
                .addContainerGap(164, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnVoltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVoltarActionPerformed
        new FrmaMenuPrincipal().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnVoltarActionPerformed

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(() -> new FrmMovimentacao().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnVoltar;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable3;
    // End of variables declaration//GEN-END:variables
}
