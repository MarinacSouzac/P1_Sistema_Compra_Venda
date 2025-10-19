
package Visualização;
import DAO.NotaFiscalDAO;
import Beans.NotaFiscal;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.time.format.DateTimeFormatter;



public class ConsultaNotaFiscal extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = 
    java.util.logging.Logger.getLogger(ConsultaNotaFiscal.class.getName());     

    public ConsultaNotaFiscal() {
     initComponents();
     mostrarTodasNotas();
     setLocationRelativeTo(null);
     cmbID.setEnabled(false);
     cmbTipo.addActionListener(e -> {
        cmbID.setEnabled(true); 
        atualizarIDsPorTipo();  
     });
     btnBuscar.addActionListener(e -> filtrarNotas());
     btnLimpar.addActionListener(e -> {
        cmbTipo.setSelectedIndex(-1);
        cmbID.setSelectedItem("");
        cmbID.setEnabled(false);
        mostrarTodasNotas();
     });
        
    }
    
 private void atualizarIDsPorTipo() {
    int tipo = cmbTipo.getSelectedIndex(); 
    NotaFiscalDAO dao = new NotaFiscalDAO();
    List<NotaFiscal> notas = dao.buscarPorTipo(tipo);
    cmbID.removeAllItems();
    for (NotaFiscal nf : notas) {
        cmbID.addItem(String.valueOf(nf.getId()));
    }

    cmbID.setEnabled(true);
}

 private void filtrarNotas() {
    NotaFiscalDAO dao = new NotaFiscalDAO();
    List<NotaFiscal> notasFiltradas = new ArrayList<>();

    String idTexto = (String) cmbID.getEditor().getItem();
    boolean idPreenchido = idTexto != null && !idTexto.trim().isEmpty();
    int tipoSelecionado = cmbTipo.getSelectedIndex(); 

    if (idPreenchido) {
        try {
            int id = Integer.parseInt(idTexto.trim());
            NotaFiscal nf = dao.buscarPorId(id);
            if (nf != null) {
                int tipoNota = nf.isTipo() ? 1 : 0;
                if (tipoNota == tipoSelecionado) {
                    notasFiltradas.add(nf);
                } else {
                    JOptionPane.showMessageDialog(this, "ID não pertence "
                            + "ao tipo selecionado.");
                    mostrarTodasNotas();
                    return;
                }
            } else {
                JOptionPane.showMessageDialog(this, "Nota com ID " + id + " "
                        + "não encontrada.");
                mostrarTodasNotas();
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID inválido.");
            mostrarTodasNotas();
            return;
        }
    } else {
        notasFiltradas = dao.buscarPorTipo(tipoSelecionado);
    }

    DefaultTableModel model = (DefaultTableModel) tblLtNF.getModel();
    model.setRowCount(0);

    for (NotaFiscal nf : notasFiltradas) {
        model.addRow(new Object[]{
            nf.getId(),
            nf.isTipo() ? "Saída" : "Entrada",
            nf.getFornecedor() != null ? nf.getFornecedor().getNome() : "-",
            nf.getCliente() != null ? nf.getCliente().getNome() : "-",
            nf.getDataVenda().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
            nf.getQtdTotal(),
            nf.getValorTotal()
        });
    }
}

private void mostrarTodasNotas() {
    NotaFiscalDAO dao = new NotaFiscalDAO();
    List<NotaFiscal> notas = dao.getNotas();

    DefaultTableModel model = (DefaultTableModel) tblLtNF.getModel();
    model.setRowCount(0);

    for (NotaFiscal nf : notas) {
        model.addRow(new Object[]{
            nf.getId(),
            nf.isTipo() ? "Saída" : "Entrada",
            nf.getFornecedor() != null ? nf.getFornecedor().getNome() : "-",
            nf.getCliente() != null ? nf.getCliente().getNome() : "-",
            nf.getDataVenda().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
            nf.getQtdTotal(),
            nf.getValorTotal()
        });
    }
}

 
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTitulo = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblLtNF = new javax.swing.JTable();
        lblTipo = new javax.swing.JLabel();
        lblID = new javax.swing.JLabel();
        cmbTipo = new javax.swing.JComboBox<>();
        cmbID = new javax.swing.JComboBox<>();
        btnBuscar = new javax.swing.JButton();
        btnLimpar = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        menPrincipal = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        mncCli = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        menFnc = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        menENF = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblTitulo.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblTitulo.setText("Lista das Notas Fiscais");

        tblLtNF.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Tipo", "Fornecedor", "Cliente", "Data", "Quantidade Total", "Valor Total"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Integer.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblLtNF);

        lblTipo.setText("Tipo:");

        lblID.setText("ID:");

        cmbTipo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0-Entrada", "1-Saída" }));

        cmbID.setEditable(true);

        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        btnLimpar.setText("Limpar");

        jMenu1.setText("Inicio");

        menPrincipal.setText("Menu Principal");
        menPrincipal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menPrincipalActionPerformed(evt);
            }
        });
        jMenu1.add(menPrincipal);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Cliente");

        mncCli.setText("Gerenciar Clientes");
        mncCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mncCliActionPerformed(evt);
            }
        });
        jMenu2.add(mncCli);

        jMenuBar1.add(jMenu2);

        jMenu3.setText("Fornecedor");

        menFnc.setText("Gerenciar Fornecedores");
        menFnc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menFncActionPerformed(evt);
            }
        });
        jMenu3.add(menFnc);

        jMenuBar1.add(jMenu3);

        jMenu4.setText("Nota Fiscal");

        menENF.setText("Emitir Nota Fiscal");
        menENF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menENFActionPerformed(evt);
            }
        });
        jMenu4.add(menENF);

        jMenuBar1.add(jMenu4);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 589, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(lblTitulo)
                        .addGap(194, 194, 194))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblTipo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(49, 49, 49)
                        .addComponent(lblID)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cmbID, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnBuscar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnLimpar)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(lblTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblID)
                    .addComponent(lblTipo)
                    .addComponent(cmbTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscar)
                    .addComponent(btnLimpar))
                .addGap(19, 19, 19)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(37, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void menPrincipalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menPrincipalActionPerformed
        Menu tela= new Menu();
        tela.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_menPrincipalActionPerformed

    private void menFncActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menFncActionPerformed
        Visualização.Fornecedor tela= new Visualização.Fornecedor();
        tela.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_menFncActionPerformed

    private void mncCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mncCliActionPerformed
        Visualização.Cliente tela= new Visualização.Cliente();
        tela.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_mncCliActionPerformed

    private void menENFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menENFActionPerformed
        Visualização.NotaFiscal tela= new Visualização.NotaFiscal();
        tela.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_menENFActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnBuscarActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new ConsultaNotaFiscal().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnLimpar;
    private javax.swing.JComboBox<String> cmbID;
    private javax.swing.JComboBox<String> cmbTipo;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblID;
    private javax.swing.JLabel lblTipo;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JMenuItem menENF;
    private javax.swing.JMenuItem menFnc;
    private javax.swing.JMenuItem menPrincipal;
    private javax.swing.JMenuItem mncCli;
    private javax.swing.JTable tblLtNF;
    // End of variables declaration//GEN-END:variables
}
