/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Visualização;

import DAO.ProdutoDAO;
import DAO.FornecedorDAO;
import Beans.Fornecedor;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.JComboBox;

/**
 *
 * @author Marina Souza
 */
public class Produto extends javax.swing.JFrame {
private boolean edicaoAtiva=false;    
private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Produto.class.getName());
    public Produto() {
        initComponents();
        preencherComboFornecedores();
        limparFormulario();
        btnEditar.setEnabled(false);
        btnExcluir.setEnabled(false);
        txtPrd.requestFocusInWindow();
        txtCod.setFocusable(false);
        atualizarProximoId();
        tblProduto.getSelectionModel().addListSelectionListener(e -> {
        if (!e.getValueIsAdjusting()) {
        int linha = tblProduto.getSelectedRow();
        btnEditar.setEnabled(linha >= 0);
        btnExcluir.setEnabled(linha >= 0);
        }}); 
        
         getContentPane().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                tblProduto.clearSelection();
                btnEditar.setEnabled(false);
                btnExcluir.setEnabled(false);
            }
        });
      
    }
   
    private void atualizarProximoId() {
            ProdutoDAO dao = new ProdutoDAO();
            int proximoId = dao.getNextId();
            txtCod.setText(String.valueOf(proximoId));
             }
    
     private void limparFormulario(){
        txtCodB.setText("");
        txtDescricao.setText("");
        txtPrd.setText("");
        txtPreco.setText("");
        txtQtdEstoque.setValue(0);
        cmbFornecedor.setSelectedIndex(0);
        btnSalvar.setText("Salvar");
    }
    
    public void preencherComboFornecedores(){
        cmbFornecedor.removeAllItems();
        FornecedorDAO fDAO= new FornecedorDAO();
        List<Fornecedor> listaFornecedor =fDAO.getFornecedor();
        for(Fornecedor fornecedor: listaFornecedor){
            cmbFornecedor.addItem(fornecedor);
        }
        if (cmbFornecedor.getItemCount() > 0) {
        cmbFornecedor.setSelectedIndex(0);
        }
    }
    
     public void preencherTabela(){
        ProdutoDAO pDAO=new ProdutoDAO();
        List<Beans.Produto> listaProdutos=pDAO.getProduto();
        
        DefaultTableModel tabelaProdutos=(DefaultTableModel) 
                tblProduto.getModel();
        tabelaProdutos.setRowCount(0); 
        
        for(Beans.Produto c: listaProdutos){
            Object[] obj= new Object[]{
                c.getId(),
                c.getNome(),
                c.getCodBarras(),
                c.getDescricao(),
                c.getFornecedor().getNome(),
                c.getPrecoVenda(),
                c.getQtdEstoque(),
                
            }; 
             tabelaProdutos.addRow(obj);
        }
        
    }  
      

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTitulo = new javax.swing.JLabel();
        txtPrd = new javax.swing.JTextField();
        lblCod = new javax.swing.JLabel();
        txtCod = new javax.swing.JTextField();
        lblNome = new javax.swing.JLabel();
        lblDescricao = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDescricao = new javax.swing.JTextArea();
        lblPreco = new javax.swing.JLabel();
        lblQtdEstoque = new javax.swing.JLabel();
        txtQtdEstoque = new javax.swing.JSpinner();
        lblFornecedor = new javax.swing.JLabel();
        cmbFornecedor = new javax.swing.JComboBox();
        btnListar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnSalvar = new javax.swing.JButton();
        btnLimpar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblProduto = new javax.swing.JTable();
        btnEditar = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txtCodB = new javax.swing.JTextField();
        txtPreco = new javax.swing.JTextField();
        jMenuBar1 = new javax.swing.JMenuBar();
        menInicio = new javax.swing.JMenu();
        menPrincipal = new javax.swing.JMenuItem();
        menCliente = new javax.swing.JMenu();
        menCli = new javax.swing.JMenuItem();
        menFornecedor = new javax.swing.JMenu();
        menFrn = new javax.swing.JMenuItem();
        menNF = new javax.swing.JMenu();
        menENF = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        lblTitulo.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblTitulo.setText("Cadastro do Produto");

        lblCod.setText("Código do produto");

        txtCod.setEditable(false);
        txtCod.setBackground(new java.awt.Color(153, 153, 153));

        lblNome.setText("Nome do produto");

        lblDescricao.setText("Descrição do produto");

        txtDescricao.setColumns(20);
        txtDescricao.setRows(5);
        jScrollPane1.setViewportView(txtDescricao);

        lblPreco.setText("Preço de venda");

        lblQtdEstoque.setText("Quantidade em estoque");

        lblFornecedor.setText("Fornecedor");

        btnListar.setLabel("Listar Produtos");
        btnListar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnListarActionPerformed(evt);
            }
        });

        btnCancelar.setText("Cancelar");

        btnSalvar.setText("Salvar");
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });

        btnLimpar.setText("Limpar os Campos");
        btnLimpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimparActionPerformed(evt);
            }
        });

        tblProduto.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Nome", "Codigo_Barras", "Descricao", "Fornecedor", "Preço_venda", "Qtd_estoque"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tblProduto);

        btnEditar.setText("Editar");
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        btnExcluir.setText("Excluir");
        btnExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirActionPerformed(evt);
            }
        });

        jLabel1.setText("Código de Barras");

        txtPreco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPrecoActionPerformed(evt);
            }
        });

        menInicio.setText("Início");

        menPrincipal.setText("Menu Principal");
        menPrincipal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menPrincipalActionPerformed(evt);
            }
        });
        menInicio.add(menPrincipal);

        jMenuBar1.add(menInicio);

        menCliente.setText("Cliente");

        menCli.setText("Gerenciar Clientes");
        menCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menCliActionPerformed(evt);
            }
        });
        menCliente.add(menCli);

        jMenuBar1.add(menCliente);

        menFornecedor.setText("Fornecedor");

        menFrn.setText("Gerenciar Fornecedores");
        menFrn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menFrnActionPerformed(evt);
            }
        });
        menFornecedor.add(menFrn);

        jMenuBar1.add(menFornecedor);

        menNF.setText("Nota Fiscal");

        menENF.setText("Emitir Nota Fiscal");
        menENF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menENFActionPerformed(evt);
            }
        });
        menNF.add(menENF);

        jMenuBar1.add(menNF);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblPreco)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnSalvar)
                                .addGap(18, 18, 18)
                                .addComponent(btnLimpar)
                                .addGap(18, 18, 18)
                                .addComponent(btnListar, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(26, 26, 26)
                                .addComponent(btnCancelar, javax.swing.GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblFornecedor)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(cmbFornecedor, 0, 159, Short.MAX_VALUE)
                                        .addComponent(lblQtdEstoque)
                                        .addComponent(txtQtdEstoque)))
                                .addGap(24, 24, 24))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(131, 131, 131)
                        .addComponent(lblTitulo)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblCod, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtCod, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(35, 35, 35)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(lblNome))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txtCodB, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtPrd))
                                .addContainerGap())))))
            .addGroup(layout.createSequentialGroup()
                .addGap(112, 112, 112)
                .addComponent(btnEditar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnExcluir)
                .addGap(110, 110, 110))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblDescricao)
                    .addComponent(txtPreco, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(lblTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCod)
                    .addComponent(lblNome))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPrd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtCodB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblDescricao)
                .addGap(2, 2, 2)
                .addComponent(lblFornecedor)
                .addGap(2, 2, 2)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblPreco)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPreco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnSalvar)
                            .addComponent(btnLimpar)
                            .addComponent(btnListar)
                            .addComponent(btnCancelar))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnExcluir)
                            .addComponent(btnEditar)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(cmbFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblQtdEstoque)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtQtdEstoque, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnListarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnListarActionPerformed
       preencherTabela();
    }//GEN-LAST:event_btnListarActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        int linhaSelecionada = tblProduto.getSelectedRow();
    
    if (linhaSelecionada >= 0) {
        edicaoAtiva=true;
        DefaultTableModel modelo = (DefaultTableModel) tblProduto.getModel();

      
        int id = (int) modelo.getValueAt(linhaSelecionada, 0);
        String nome = (String) modelo.getValueAt(linhaSelecionada, 1);
        String codBarras = (String) modelo.getValueAt(linhaSelecionada, 2);
        String descricao = (String) modelo.getValueAt(linhaSelecionada, 3);
        String Fornecedor = (String) modelo.getValueAt(linhaSelecionada, 4);
        double precoVenda = (double) modelo.getValueAt(linhaSelecionada, 5);
        int qtdEstoque = (Integer) modelo.getValueAt(linhaSelecionada, 6);

        txtCod.setText(String.valueOf(id));
        txtPrd.setText(nome);
        txtCodB.setText(codBarras);
        txtDescricao.setText(descricao);
        txtPreco.setText(String.valueOf(precoVenda));
        txtQtdEstoque.setValue(qtdEstoque);

        
        for (int i = 0; i < cmbFornecedor.getItemCount(); i++) {
            Fornecedor f = (Fornecedor) cmbFornecedor.getItemAt(i);
            if (f.getNome().equals(Fornecedor)) {
                cmbFornecedor.setSelectedIndex(i);
                break;
            }
        }
        btnSalvar.setText("Atualizar");

        JOptionPane.showMessageDialog(this, 
            "Modo de edição ativado. Faça as alterações e "
                    + "clique em 'Atualizar'.");
    } else {
        JOptionPane.showMessageDialog(this, 
            "Selecione um cliente na tabela para editar!");
    }  
    }//GEN-LAST:event_btnEditarActionPerformed

    private void menPrincipalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menPrincipalActionPerformed
        Menu tela= new Menu();
        tela.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_menPrincipalActionPerformed

    private void menCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menCliActionPerformed
        Cliente tela= new Cliente();
        tela.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_menCliActionPerformed

    private void menFrnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menFrnActionPerformed
        Visualização.Fornecedor tela= new Visualização.Fornecedor();
        tela.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_menFrnActionPerformed

    private void menENFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menENFActionPerformed
        NotaFiscal tela= new NotaFiscal();
        tela.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_menENFActionPerformed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed

    String nome = txtPrd.getText().trim();
    String codBarras = txtCodB.getText().trim();
    String descricao = txtDescricao.getText().trim();
    double precoVenda=Double.parseDouble(txtPreco.getText().trim());;

    

    int qtdEstoque = (Integer) txtQtdEstoque.getValue();
    Fornecedor fornecedorSelecionado = (Fornecedor) 
            cmbFornecedor.getSelectedItem();

    if (nome.isEmpty() || codBarras.isEmpty() || descricao.isEmpty() 
            || precoVenda <= 0 || qtdEstoque < 0 || 
            fornecedorSelecionado == null) 
    {
        JOptionPane.showMessageDialog(this, "Todos os campos são obrigatórios!");
        return;
    }
   System.out.println("Fornecedor selecionado: " + 
           cmbFornecedor.getSelectedItem());
    Beans.Produto produto = new Beans.Produto();
    produto.setNome(nome);
    produto.setCodBarras(codBarras);
    produto.setDescricao(descricao);
    produto.setPrecoVenda(precoVenda);
    produto.setQtdEstoque(qtdEstoque);
    produto.setFornecedor(fornecedorSelecionado);

    ProdutoDAO dao = new ProdutoDAO();
    boolean sucesso;

    if (!edicaoAtiva) {
        // Inserir
        System.out.println("Fornecedor dentro do produto: " 
                + produto.getFornecedor());
        int idGerado = dao.inserirProduto(produto);
        sucesso = (idGerado != -1);
        if (sucesso) {
            JOptionPane.showMessageDialog(this, 
                    "Produto inserido com sucesso! ID: " + idGerado);
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao inserir produto!");
        }
    } else {

        int id = Integer.parseInt(txtCod.getText().trim());
        produto.setId(id);
        sucesso = dao.editarProduto(produto);
        if (sucesso) {
            JOptionPane.showMessageDialog(this, 
                    "Produto atualizado com sucesso!");
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar produto!");
        }
        edicaoAtiva = false;
    } 
    if (sucesso) {
        limparFormulario();
        atualizarProximoId();
        DefaultTableModel modelo = (DefaultTableModel) tblProduto.getModel();
        modelo.setRowCount(0); 
        txtPrd.requestFocusInWindow();
    }           

    }//GEN-LAST:event_btnSalvarActionPerformed

    private void btnLimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimparActionPerformed
       limparFormulario();
    }//GEN-LAST:event_btnLimparActionPerformed

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
      int linhaSelecionada = tblProduto.getSelectedRow();

    if (linhaSelecionada >= 0) {
        DefaultTableModel modelo = (DefaultTableModel) tblProduto.getModel();
        int id = (int) modelo.getValueAt(linhaSelecionada, 0); // pega o ID da linha selecionada

        // Confirmação antes de excluir
        int opcao = JOptionPane.showConfirmDialog(this, 
            "Deseja realmente excluir este cliente?", 
            "Confirmação", 
            JOptionPane.YES_NO_OPTION);

        if (opcao == JOptionPane.YES_OPTION) {
            // Cria um objeto Cliente com o ID
            Beans.Fornecedor fornecedor= new Beans.Fornecedor();
            fornecedor.setId(id);

            // Chama o DAO para excluir
            FornecedorDAO dao = new FornecedorDAO();
            dao.excluirFornecedor(fornecedor); 

            // Remove a linha da tabela
            modelo.removeRow(linhaSelecionada);

            JOptionPane.showMessageDialog(this, "Cliente excluído com sucesso!");
        }
    } else {
        JOptionPane.showMessageDialog(this, "Selecione um cliente na tabela para excluir!");
    }  
    }//GEN-LAST:event_btnExcluirActionPerformed

    private void txtPrecoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPrecoActionPerformed

    }//GEN-LAST:event_txtPrecoActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new Produto().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnLimpar;
    private javax.swing.JButton btnListar;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JComboBox cmbFornecedor;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblCod;
    private javax.swing.JLabel lblDescricao;
    private javax.swing.JLabel lblFornecedor;
    private javax.swing.JLabel lblNome;
    private javax.swing.JLabel lblPreco;
    private javax.swing.JLabel lblQtdEstoque;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JMenuItem menCli;
    private javax.swing.JMenu menCliente;
    private javax.swing.JMenuItem menENF;
    private javax.swing.JMenu menFornecedor;
    private javax.swing.JMenuItem menFrn;
    private javax.swing.JMenu menInicio;
    private javax.swing.JMenu menNF;
    private javax.swing.JMenuItem menPrincipal;
    private javax.swing.JTable tblProduto;
    private javax.swing.JTextField txtCod;
    private javax.swing.JTextField txtCodB;
    private javax.swing.JTextArea txtDescricao;
    private javax.swing.JTextField txtPrd;
    private javax.swing.JTextField txtPreco;
    private javax.swing.JSpinner txtQtdEstoque;
    // End of variables declaration//GEN-END:variables
}
