/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Visualização;


import DAO.FornecedorDAO;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author Marina Souza
 */
public class Fornecedor extends javax.swing.JFrame {
    private boolean edicaoAtiva=false;
    private static final java.util.logging.Logger 
            logger = java.util.logging.Logger.getLogger(Fornecedor.class.getName());

    public Fornecedor() {
        initComponents();
        
        limparFormulario();
        btnEditar.setEnabled(false);
        btnExcluir.setEnabled(false);
        txtNome.requestFocusInWindow();
        txtCod.setFocusable(false);
        atualizarProximoId();
        
         tblFornecedor.getSelectionModel().addListSelectionListener(e -> {
    if (!e.getValueIsAdjusting()) {
        int linha = tblFornecedor.getSelectedRow();
        btnEditar.setEnabled(linha >= 0);
        btnExcluir.setEnabled(linha >= 0);
    }
});

// Adiciona listener no painel principal (ou frame) para clicar fora da tabela
        getContentPane().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                // Limpa seleção da tabela
                tblFornecedor.clearSelection();
                btnEditar.setEnabled(false);
                btnExcluir.setEnabled(false);
            }
        });

        
        
    }
        
    
private void atualizarProximoId() {
    FornecedorDAO dao = new FornecedorDAO();
    int proximoId = dao.getNextId();
    txtCod.setText(String.valueOf(proximoId));
     }
    
      private void limparFormulario(){
        txtNome.setText("");
        txtNomeFt.setText("");
        txtCnpj.setText("");
        txtEmail.setText("");
        txtTelefone.setText("");
        txtRua.setText("");
        txtNum.setText("");
        txtBairro.setText("");
        txtCidade.setText("");
        txtCEP.setText("");
        txtPais.setText("");
        btnSalvar.setText("Salvar");
    }
     public void preencherTabela(){
        FornecedorDAO fDAO=new FornecedorDAO();
        List<Beans.Fornecedor> listaFornecedor=fDAO.getFornecedor();
        
        DefaultTableModel tabelaFornecedor=(DefaultTableModel) tblFornecedor.getModel();
        tabelaFornecedor.setRowCount(0); 
        
        for(Beans.Fornecedor c: listaFornecedor){
            Object[] obj= new Object[]{
                c.getId(),
                c.getNome(),
                c.getCnpj(),
                c.getNome_fantasia(),
                c.getEmail(),
                c.getTelefone(),
                c.getPais(),
                c.getEstado(),
                c.getCidade(),
                c.getCep(),
                c.getRua(),
                c.getNumero(),
                c.getBairro()
                
            }; 
             tabelaFornecedor.addRow(obj);
        }
        
    }  
      
   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton6 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        lblTitulo = new javax.swing.JLabel();
        txtNome = new javax.swing.JTextField();
        lblCod = new javax.swing.JLabel();
        txtCod = new javax.swing.JTextField();
        lblNome = new javax.swing.JLabel();
        lblCnpj = new javax.swing.JLabel();
        txtCnpj = new javax.swing.JFormattedTextField();
        lblNomeFt = new javax.swing.JLabel();
        txtNomeFt = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        lblTelefone = new javax.swing.JLabel();
        txtTelefone = new javax.swing.JFormattedTextField();
        lblEmail = new javax.swing.JLabel();
        lblCidade = new javax.swing.JLabel();
        txtCidade = new javax.swing.JTextField();
        lblPais = new javax.swing.JLabel();
        txtPais = new javax.swing.JTextField();
        lblUF = new javax.swing.JLabel();
        cmbUF = new javax.swing.JComboBox<>();
        lblCEP = new javax.swing.JLabel();
        txtBairro = new javax.swing.JTextField();
        lblRua = new javax.swing.JLabel();
        txtNum = new javax.swing.JTextField();
        lblBairro = new javax.swing.JLabel();
        txtRua = new javax.swing.JTextField();
        txtCEP = new javax.swing.JFormattedTextField();
        lblNum = new javax.swing.JLabel();
        btnSalvar = new javax.swing.JButton();
        btnLimpar = new javax.swing.JButton();
        btnListar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblFornecedor = new javax.swing.JTable();
        btnEditar = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        jMenuBar2 = new javax.swing.JMenuBar();
        menInicio = new javax.swing.JMenu();
        menPrincipal = new javax.swing.JMenuItem();
        menCliente = new javax.swing.JMenu();
        menCli = new javax.swing.JMenuItem();
        menProduto = new javax.swing.JMenu();
        menPrd = new javax.swing.JMenuItem();
        menNF = new javax.swing.JMenu();
        menENF = new javax.swing.JMenuItem();

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

        jButton6.setText("Excluir");

        jButton5.setText("Editar");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        lblTitulo.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblTitulo.setText("Cadastro do Fornecedor");

        lblCod.setText("Código do fornecedor");

        txtCod.setEditable(false);
        txtCod.setBackground(new java.awt.Color(153, 153, 153));

        lblNome.setText("Nome do fornecedor");

        lblCnpj.setText("CNPJ");

        try {
            txtCnpj.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##.###.###/####-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        lblNomeFt.setText("Nome fantasia");

        txtNomeFt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomeFtActionPerformed(evt);
            }
        });

        txtEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEmailActionPerformed(evt);
            }
        });

        lblTelefone.setText("Telefone");

        try {
            txtTelefone.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("## #####-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        lblEmail.setText("Email");

        lblCidade.setText("Cidade");

        lblPais.setText("País");

        lblUF.setText("UF");

        cmbUF.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO" }));

        lblCEP.setText("CEP");

        lblRua.setText("Rua");

        txtNum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNumActionPerformed(evt);
            }
        });

        lblBairro.setText("Bairro");

        try {
            txtCEP.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("#####-###")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        lblNum.setText("Número");

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

        btnListar.setText("Listar Fornecedores");
        btnListar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnListarActionPerformed(evt);
            }
        });

        btnCancelar.setText("Cancelar");

        tblFornecedor.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Nome", "CNPJ", "Nome Fantasia", "Email", "Telefone", "País", "UF", "Cidade", "CEP", "Rua", "Número", "Bairro"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, true, true, true, true, true, true, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tblFornecedor);

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

        menInicio.setText("Início");
        menInicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menInicioActionPerformed(evt);
            }
        });

        menPrincipal.setText("Menu Principal");
        menPrincipal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menPrincipalActionPerformed(evt);
            }
        });
        menInicio.add(menPrincipal);

        jMenuBar2.add(menInicio);

        menCliente.setText("Cliente");

        menCli.setText("Gerenciar Clientes");
        menCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menCliActionPerformed(evt);
            }
        });
        menCliente.add(menCli);

        jMenuBar2.add(menCliente);

        menProduto.setText("Produto");

        menPrd.setText("Gerenciar Produtos");
        menPrd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menPrdActionPerformed(evt);
            }
        });
        menProduto.add(menPrd);

        jMenuBar2.add(menProduto);

        menNF.setText("Nota Fiscal");

        menENF.setText("Emitir Nota Fiscal");
        menENF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menENFActionPerformed(evt);
            }
        });
        menNF.add(menENF);

        jMenuBar2.add(menNF);

        setJMenuBar(jMenuBar2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblCnpj)
                            .addComponent(lblCod, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtCod)
                            .addComponent(txtCnpj))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNome)
                            .addComponent(lblNomeFt)
                            .addComponent(txtNomeFt)
                            .addComponent(txtNome)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(txtPais, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblTitulo)
                                .addGap(109, 109, 109))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cmbUF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblUF))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblTelefone)
                                    .addComponent(lblCidade)
                                    .addComponent(txtTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtCidade)))))
                    .addComponent(jScrollPane2)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblPais, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtCEP, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblCEP))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtRua, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblRua))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblNum)
                                    .addComponent(txtNum, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblBairro)
                                    .addComponent(txtBairro, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(18, 18, 18))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(btnSalvar)
                .addGap(69, 69, 69)
                .addComponent(btnLimpar)
                .addGap(39, 39, 39)
                .addComponent(btnListar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnCancelar)
                .addGap(26, 26, 26))
            .addGroup(layout.createSequentialGroup()
                .addGap(161, 161, 161)
                .addComponent(btnEditar)
                .addGap(103, 103, 103)
                .addComponent(btnExcluir)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lblEmail)
                        .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(362, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCod)
                    .addComponent(lblNome))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCnpj)
                    .addComponent(lblNomeFt))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCnpj, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNomeFt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addComponent(lblTelefone)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPais)
                    .addComponent(lblUF)
                    .addComponent(lblCidade))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPais, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbUF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCEP)
                    .addComponent(lblRua)
                    .addComponent(lblBairro)
                    .addComponent(lblNum))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCEP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtRua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtBairro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSalvar)
                    .addComponent(btnLimpar)
                    .addComponent(btnListar)
                    .addComponent(btnCancelar))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEditar)
                    .addComponent(btnExcluir))
                .addContainerGap(10, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(146, 146, 146)
                    .addComponent(lblEmail)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(320, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtNomeFtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomeFtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNomeFtActionPerformed

    private void txtEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEmailActionPerformed

    private void txtNumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNumActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNumActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
     int linhaSelecionada = tblFornecedor.getSelectedRow();
    
    if (linhaSelecionada >= 0) {
        edicaoAtiva=true;
        DefaultTableModel modelo = (DefaultTableModel) tblFornecedor.getModel();

        // Pegando os valores da tabela
        int id = (int) modelo.getValueAt(linhaSelecionada, 0);
        String nome = (String) modelo.getValueAt(linhaSelecionada, 1);
        String cnpj = (String) modelo.getValueAt(linhaSelecionada, 2);
        String nomeFantasia = (String) modelo.getValueAt(linhaSelecionada, 3);
        String email = (String) modelo.getValueAt(linhaSelecionada, 4);
        String telefone = (String) modelo.getValueAt(linhaSelecionada, 5);
        String pais = (String) modelo.getValueAt(linhaSelecionada, 6);
        String estado = (String) modelo.getValueAt(linhaSelecionada, 7);
        String cidade = (String) modelo.getValueAt(linhaSelecionada, 8);
        String cep = (String) modelo.getValueAt(linhaSelecionada, 9);
        String rua = (String) modelo.getValueAt(linhaSelecionada, 10);
        String numero = (String) modelo.getValueAt(linhaSelecionada, 11);
        String bairro = (String) modelo.getValueAt(linhaSelecionada, 12);

        txtCod.setText(String.valueOf(id));
        txtNome.setText(nome);
        txtCnpj.setText(cnpj);
        txtNomeFt.setText(nomeFantasia);
        txtEmail.setText(email);
        txtTelefone.setText(telefone);
        txtPais.setText(pais);
        cmbUF.setSelectedItem(estado);
        txtCidade.setText(cidade);
        txtCEP.setText(cep);
        txtRua.setText(rua);
        txtNum.setText(numero);
        txtBairro.setText(bairro);

     

        // Muda o texto do botão salvar para indicar edição
        btnSalvar.setText("Atualizar");

        JOptionPane.showMessageDialog(this, 
            "Modo de edição ativado. Faça as alterações e clique em 'Atualizar'.");
    } else {
        JOptionPane.showMessageDialog(this, 
            "Selecione um cliente na tabela para editar!");
    }      
    }//GEN-LAST:event_btnEditarActionPerformed

    private void menInicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menInicioActionPerformed
        
    }//GEN-LAST:event_menInicioActionPerformed

    private void menPrdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menPrdActionPerformed
        Produto tela= new Produto();
        tela.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_menPrdActionPerformed

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

    private void menENFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menENFActionPerformed
        NotaFiscal tela= new NotaFiscal();
        tela.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_menENFActionPerformed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        String nome = txtNome.getText().trim();
        String Cnpj = txtCnpj.getText().replaceAll("\\D", "");
        
         if (nome.isEmpty() ||Cnpj.isEmpty()||txtNomeFt.getText().trim().isEmpty()
                 ||
        txtEmail.getText().trim().isEmpty() ||
        txtTelefone.getText().replaceAll("\\D","").isEmpty() ||
        txtRua.getText().trim().isEmpty() ||
        txtNum.getText().trim().isEmpty() ||
        txtBairro.getText().trim().isEmpty() ||
        txtCidade.getText().trim().isEmpty() ||
        txtCEP.getText().trim().isEmpty() ||
        txtPais.getText().trim().isEmpty()
        )
    {
        JOptionPane.showMessageDialog(this, "Todos os campos são obrigatórios!");
        return;
    }
         Beans.Fornecedor fornecedor= new Beans.Fornecedor();
         FornecedorDAO dao= new FornecedorDAO();
         
            fornecedor.setNome(nome);
            fornecedor.setCnpj(txtCnpj.getText().replaceAll("\\D",""));
            fornecedor.setNome_fantasia(txtNomeFt.getText().trim());
            fornecedor.setEmail(txtEmail.getText().trim());
            fornecedor.setTelefone(txtTelefone.getText().replaceAll("\\D",""));
            fornecedor.setRua(txtRua.getText().trim());
            fornecedor.setNumero(txtNum.getText().trim());
            fornecedor.setBairro(txtBairro.getText().trim());
            fornecedor.setCidade(txtCidade.getText().trim());
            fornecedor.setEstado((String) cmbUF.getSelectedItem());
            fornecedor.setCep(txtCEP.getText().trim());
            fornecedor.setPais(txtPais.getText().trim());
             boolean sucesso = false;

    // 🔹 Verifica se estamos no modo edição
    

    if (!edicaoAtiva) {
        // INSERIR
        int idGerado = dao.inserirFornecedor(fornecedor);
        sucesso = (idGerado != -1);

        if (sucesso) {
            JOptionPane.showMessageDialog(this, "Cliente inserido com sucesso! ID: " + idGerado);
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao inserir cliente!");
        }
    } else {
        // ATUALIZAR
        int id = Integer.parseInt(txtCod.getText().trim());
        fornecedor.setId(id);
        sucesso = dao.editarFornecedor(fornecedor);

        if (sucesso) {
            JOptionPane.showMessageDialog(this, "Cliente atualizado com sucesso!");
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar cliente!");
        }
        edicaoAtiva= false;
    }

    // 🔹 Atualiza a interface após salvar/editar
    if (sucesso) {
        limparFormulario();         // Zera os campos e botão
        atualizarProximoId();       // Atualiza o próximo ID, se necessário
        DefaultTableModel modelo = (DefaultTableModel) tblFornecedor.getModel();
        modelo.setRowCount(0);      // Limpa tabela para recarregar
        txtNome.requestFocusInWindow();
    }
         
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void btnLimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimparActionPerformed
        limparFormulario();
    }//GEN-LAST:event_btnLimparActionPerformed

    private void btnListarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnListarActionPerformed
        preencherTabela();
    }//GEN-LAST:event_btnListarActionPerformed

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        int linhaSelecionada = tblFornecedor.getSelectedRow();

    if (linhaSelecionada >= 0) {
        DefaultTableModel modelo = (DefaultTableModel) tblFornecedor.getModel();
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
            dao.excluirFornecedor(fornecedor); // método void que você já ajustou

            // Remove a linha da tabela
            modelo.removeRow(linhaSelecionada);

            JOptionPane.showMessageDialog(this, "Cliente excluído com sucesso!");
        }
    } else {
        JOptionPane.showMessageDialog(this, "Selecione um cliente na tabela para excluir!");
    }
    }//GEN-LAST:event_btnExcluirActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new Fornecedor().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnLimpar;
    private javax.swing.JButton btnListar;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JComboBox<String> cmbUF;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lblBairro;
    private javax.swing.JLabel lblCEP;
    private javax.swing.JLabel lblCidade;
    private javax.swing.JLabel lblCnpj;
    private javax.swing.JLabel lblCod;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblNome;
    private javax.swing.JLabel lblNomeFt;
    private javax.swing.JLabel lblNum;
    private javax.swing.JLabel lblPais;
    private javax.swing.JLabel lblRua;
    private javax.swing.JLabel lblTelefone;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblUF;
    private javax.swing.JMenuItem menCli;
    private javax.swing.JMenu menCliente;
    private javax.swing.JMenuItem menENF;
    private javax.swing.JMenu menInicio;
    private javax.swing.JMenu menNF;
    private javax.swing.JMenuItem menPrd;
    private javax.swing.JMenuItem menPrincipal;
    private javax.swing.JMenu menProduto;
    private javax.swing.JTable tblFornecedor;
    private javax.swing.JTextField txtBairro;
    private javax.swing.JFormattedTextField txtCEP;
    private javax.swing.JTextField txtCidade;
    private javax.swing.JFormattedTextField txtCnpj;
    private javax.swing.JTextField txtCod;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtNome;
    private javax.swing.JTextField txtNomeFt;
    private javax.swing.JTextField txtNum;
    private javax.swing.JTextField txtPais;
    private javax.swing.JTextField txtRua;
    private javax.swing.JFormattedTextField txtTelefone;
    // End of variables declaration//GEN-END:variables
}
