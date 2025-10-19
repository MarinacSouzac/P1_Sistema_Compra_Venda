
package Visualização;

import DAO.ClienteDAO;
import java.text.ParseException;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


public class Cliente extends javax.swing.JFrame {
   private boolean edicaoAtiva = false;
   private static final java.util.logging.Logger logger = 
        java.util.logging.Logger.getLogger(Cliente.class.getName());

    public Cliente() {
        initComponents();
        setLocationRelativeTo(null);
        limparFormulario();
        btnEditar.setEnabled(false);
        bnExcluir.setEnabled(false);
        
            txtNome.requestFocusInWindow();
            txtCod.setFocusable(false);
            atualizarProximoId();
            
            tblCli.getSelectionModel().addListSelectionListener(e -> {
    if (!e.getValueIsAdjusting()) {
        int linha = tblCli.getSelectedRow();
        btnEditar.setEnabled(linha >= 0);
        bnExcluir.setEnabled(linha >= 0);
     }
     });
       ((DefaultTableModel) tblCli.getModel()).setRowCount(0);
        getContentPane().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                // Limpa seleção da tabela
                tblCli.clearSelection();
                btnEditar.setEnabled(false);
                bnExcluir.setEnabled(false);
            }
        });
      
    }
    
    private void atualizarProximoId() {
    ClienteDAO dao = new ClienteDAO();
    int proximoId = dao.getNextId();
    txtCod.setText(String.valueOf(proximoId));
     }
    
    private void limparFormulario(){
        txtNome.setText("");
        txtCpfCnpj.setText("");
        txtEmail.setText("");
        txtTelefone.setText("");
        txtRua.setText("");
        txtNum.setText("");
        txtBairro.setText("");
        txtCidade.setText("");
        txtCEP.setText("");
        txtPais.setText("");
        buttonGroup1.clearSelection();
        btnSalvar.setText("Salvar");
    }
    public void preencherTabela(){
        ClienteDAO cDAO=new ClienteDAO();
        List<Beans.Cliente> listaClientes=cDAO.getCliente();
        
        DefaultTableModel tabelaClientes=(DefaultTableModel) tblCli.getModel();
        tabelaClientes.setRowCount(0); 
        
        for(Beans.Cliente c: listaClientes){
            Object[] obj= new Object[]{
                c.getId(),
                c.getNome(),
                c.isTipo() ? "Física" : "Jurídica",
                c.getCpf(),
                c.getCnpj(),
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
             tabelaClientes.addRow(obj);
        }
        
    }
    private void setCpfMask() {
    try {
        MaskFormatter cpfMask = new MaskFormatter("###.###.###-##");
        cpfMask.setPlaceholderCharacter('_');
        txtCpfCnpj.setFormatterFactory(new DefaultFormatterFactory(cpfMask));
    } catch (ParseException ex) {
        ex.printStackTrace();
    }
}

private void setCnpjMask() {
    try {
        MaskFormatter cnpjMask = new MaskFormatter("##.###.###/####-##");
        cnpjMask.setPlaceholderCharacter('_');
        txtCpfCnpj.setFormatterFactory(new DefaultFormatterFactory(cnpjMask));
    } catch (ParseException ex) {
        ex.printStackTrace();
    }
}

   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblNome4 = new javax.swing.JLabel();
        lblNome3 = new javax.swing.JLabel();
        jRadioButtonMenuItem1 = new javax.swing.JRadioButtonMenuItem();
        buttonGroup1 = new javax.swing.ButtonGroup();
        jMenuItem1 = new javax.swing.JMenuItem();
        lblTitulo = new javax.swing.JLabel();
        lblCod = new javax.swing.JLabel();
        txtCod = new javax.swing.JTextField();
        lblNome = new javax.swing.JLabel();
        radPF = new javax.swing.JRadioButton();
        lblTipo = new javax.swing.JLabel();
        radPJ = new javax.swing.JRadioButton();
        lblCpfCnpj = new javax.swing.JLabel();
        txtNome = new javax.swing.JTextField();
        lblEmail = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        lblTelefone = new javax.swing.JLabel();
        txtTelefone = new javax.swing.JFormattedTextField();
        lblCEP = new javax.swing.JLabel();
        txtBairro = new javax.swing.JTextField();
        lblRua = new javax.swing.JLabel();
        txtNum = new javax.swing.JTextField();
        lblBairro = new javax.swing.JLabel();
        txtRua = new javax.swing.JTextField();
        txtCEP = new javax.swing.JFormattedTextField();
        lblNumero = new javax.swing.JLabel();
        lblPais = new javax.swing.JLabel();
        txtPais = new javax.swing.JTextField();
        lblUF = new javax.swing.JLabel();
        cmbUF = new javax.swing.JComboBox<>();
        lblCidade = new javax.swing.JLabel();
        txtCidade = new javax.swing.JTextField();
        btnSalvar = new javax.swing.JButton();
        btnLimpar = new javax.swing.JButton();
        btnListar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblCli = new javax.swing.JTable();
        btnEditar = new javax.swing.JButton();
        bnExcluir = new javax.swing.JButton();
        txtCpfCnpj = new javax.swing.JFormattedTextField();
        jMenuBar1 = new javax.swing.JMenuBar();
        menInicio = new javax.swing.JMenu();
        menPrincipal = new javax.swing.JMenuItem();
        menFornecedor = new javax.swing.JMenu();
        menFrn = new javax.swing.JMenuItem();
        menProduto = new javax.swing.JMenu();
        menPrd = new javax.swing.JMenuItem();
        menNF = new javax.swing.JMenu();
        menENF = new javax.swing.JMenuItem();
        menLNF = new javax.swing.JMenuItem();

        lblNome4.setText("CNPJ:");

        lblNome3.setText("CPF:");

        jRadioButtonMenuItem1.setSelected(true);
        jRadioButtonMenuItem1.setText("jRadioButtonMenuItem1");

        jMenuItem1.setText("jMenuItem1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(204, 204, 255));
        setName("cliente"); // NOI18N
        setResizable(false);

        lblTitulo.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblTitulo.setText("Cadastro do Cliente");

        lblCod.setText("Código do cliente");

        txtCod.setEditable(false);
        txtCod.setBackground(new java.awt.Color(153, 153, 153));
        txtCod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodActionPerformed(evt);
            }
        });

        lblNome.setText("Nome do Cliente");

        buttonGroup1.add(radPF);
        radPF.setText("Física");
        radPF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radPFActionPerformed(evt);
            }
        });

        lblTipo.setText("Tipo de Pessoa:");

        buttonGroup1.add(radPJ);
        radPJ.setText("Jurídica");
        radPJ.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radPJActionPerformed(evt);
            }
        });

        lblCpfCnpj.setText("CPF/CNPJ:");

        lblEmail.setText("Email");

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

        lblNumero.setText("Número");

        lblPais.setText("País");

        lblUF.setText("UF");

        cmbUF.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO" }));

        lblCidade.setText("Cidade");

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

        btnListar.setText("Listar Clientes");
        btnListar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnListarActionPerformed(evt);
            }
        });

        tblCli.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Nome", "Tipo", "CPF", "CNPJ", "Email", "Telefone", "País", "UF", "Cidade", "CEP", "Rua", "Número", "Bairro"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, true, true, true, true, true, true, true, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblCli);

        btnEditar.setText("Editar");
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        bnExcluir.setText("Excluir");
        bnExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bnExcluirActionPerformed(evt);
            }
        });

        menInicio.setText("Início");
        menInicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menInicioActionPerformed(evt);
            }
        });

        menPrincipal.setText("Menu principal");
        menPrincipal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menPrincipalActionPerformed(evt);
            }
        });
        menInicio.add(menPrincipal);

        jMenuBar1.add(menInicio);

        menFornecedor.setText("Fornecedor");
        menFornecedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menFornecedorActionPerformed(evt);
            }
        });

        menFrn.setText("Gerenciar Fornecedores");
        menFrn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menFrnActionPerformed(evt);
            }
        });
        menFornecedor.add(menFrn);

        jMenuBar1.add(menFornecedor);

        menProduto.setText("Produto");
        menProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menProdutoActionPerformed(evt);
            }
        });

        menPrd.setText("Gerenciar Produtos");
        menPrd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menPrdActionPerformed(evt);
            }
        });
        menProduto.add(menPrd);

        jMenuBar1.add(menProduto);

        menNF.setText("Nota Fiscal");
        menNF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menNFActionPerformed(evt);
            }
        });

        menENF.setText("Emitir Nota Fiscal");
        menENF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menENFActionPerformed(evt);
            }
        });
        menNF.add(menENF);

        menLNF.setText("Listar Notas Fiscais");
        menLNF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menLNFActionPerformed(evt);
            }
        });
        menNF.add(menLNF);

        jMenuBar1.add(menNF);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblPais)
                            .addComponent(txtPais, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addComponent(cmbUF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(36, 36, 36)
                                .addComponent(lblUF)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCidade, javax.swing.GroupLayout.PREFERRED_SIZE, 339, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblCidade, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(206, 206, 206)
                        .addComponent(lblTitulo))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(radPF)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(radPJ))
                            .addComponent(lblTipo)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(lblCod, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtCod))
                            .addComponent(lblEmail)
                            .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblCpfCnpj)
                                    .addComponent(lblNome)
                                    .addComponent(lblTelefone))
                                .addGap(250, 250, 250))
                            .addComponent(txtTelefone)
                            .addComponent(txtNome)
                            .addComponent(txtCpfCnpj)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblCEP)
                                    .addComponent(txtCEP, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(32, 32, 32)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtRua, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblRua))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnSalvar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnLimpar)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblNumero)
                                    .addComponent(txtNum, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lblBairro)
                                        .addGap(139, 139, 139))
                                    .addComponent(txtBairro)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(btnListar)
                                .addGap(39, 39, 39))))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(16, 16, 16))
            .addGroup(layout.createSequentialGroup()
                .addGap(163, 163, 163)
                .addComponent(btnEditar)
                .addGap(126, 126, 126)
                .addComponent(bnExcluir)
                .addGap(0, 0, Short.MAX_VALUE))
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
                    .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTipo)
                    .addComponent(lblCpfCnpj))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(radPJ)
                        .addComponent(radPF))
                    .addComponent(txtCpfCnpj, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEmail)
                    .addComponent(lblTelefone))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPais)
                    .addComponent(lblUF)
                    .addComponent(lblCidade))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPais, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbUF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCEP)
                    .addComponent(lblRua)
                    .addComponent(lblBairro)
                    .addComponent(lblNumero))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCEP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtRua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtBairro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnListar)
                    .addComponent(btnLimpar)
                    .addComponent(btnSalvar))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEditar)
                    .addComponent(bnExcluir))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEmailActionPerformed

    private void txtNumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNumActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNumActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
     int linhaSelecionada = tblCli.getSelectedRow();
    
    if (linhaSelecionada >= 0) {
        edicaoAtiva=true;
        DefaultTableModel modelo = (DefaultTableModel) tblCli.getModel();

        // Pegando os valores da tabela
        int id = (int) modelo.getValueAt(linhaSelecionada, 0);
        String nome = (String) modelo.getValueAt(linhaSelecionada, 1);
        String tipo = (String) modelo.getValueAt(linhaSelecionada, 2);
        String cpf = (String) modelo.getValueAt(linhaSelecionada, 3);
        String cnpj = (String) modelo.getValueAt(linhaSelecionada, 4);
        String email = (String) modelo.getValueAt(linhaSelecionada, 5);
        String telefone = (String) modelo.getValueAt(linhaSelecionada, 6);
        String pais = (String) modelo.getValueAt(linhaSelecionada, 7);
        String estado = (String) modelo.getValueAt(linhaSelecionada, 8);
        String cidade = (String) modelo.getValueAt(linhaSelecionada, 9);
        String cep = (String) modelo.getValueAt(linhaSelecionada, 10);
        String rua = (String) modelo.getValueAt(linhaSelecionada, 11);
        String numero = (String) modelo.getValueAt(linhaSelecionada, 12);
        String bairro = (String) modelo.getValueAt(linhaSelecionada, 13);

        txtCod.setText(String.valueOf(id));
        txtNome.setText(nome);
        txtCpfCnpj.setText(tipo.equalsIgnoreCase("Física") ? cpf : cnpj);
        txtEmail.setText(email);
        txtTelefone.setText(telefone);
        txtPais.setText(pais);
        cmbUF.setSelectedItem(estado);
        txtCidade.setText(cidade);
        txtCEP.setText(cep);
        txtRua.setText(rua);
        txtNum.setText(numero);
        txtBairro.setText(bairro);

        // Tipo de pessoa
        radPF.setSelected(tipo.equalsIgnoreCase("Física"));
        radPJ.setSelected(tipo.equalsIgnoreCase("Jurídica"));

        // Muda o texto do botão salvar para indicar edição
        btnSalvar.setText("Atualizar");

        JOptionPane.showMessageDialog(this, 
            "Modo de edição ativado. Faça as alterações e clique "
                    + "em 'Atualizar'.");
    } else {
        JOptionPane.showMessageDialog(this, 
            "Selecione um cliente na tabela para editar!");
    }
    }//GEN-LAST:event_btnEditarActionPerformed

    private void menInicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menInicioActionPerformed
      
    }//GEN-LAST:event_menInicioActionPerformed

    private void menFornecedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menFornecedorActionPerformed
      
    }//GEN-LAST:event_menFornecedorActionPerformed

    private void menProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menProdutoActionPerformed
      
    }//GEN-LAST:event_menProdutoActionPerformed

    private void menNFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menNFActionPerformed

    }//GEN-LAST:event_menNFActionPerformed

    private void menPrincipalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menPrincipalActionPerformed
        Menu tela= new Menu();
        tela.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_menPrincipalActionPerformed

    private void menFrnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menFrnActionPerformed
        Fornecedor tela= new Fornecedor();
        tela.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_menFrnActionPerformed

    private void menPrdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menPrdActionPerformed
        Produto tela= new Produto();
        tela.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_menPrdActionPerformed

    private void menENFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menENFActionPerformed
        NotaFiscal tela1= new NotaFiscal();
        tela1.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_menENFActionPerformed
 
    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
                                         
    // Pegando valores do formulário
    String nome = txtNome.getText().trim();
    String cpfCnpj = txtCpfCnpj.getText().replaceAll("\\D", "");
    
    // Verifica se algum campo obrigatório está vazio
    if (nome.isEmpty() ||
        (radPF.isSelected() && cpfCnpj.isEmpty()) ||
        (radPJ.isSelected() && cpfCnpj.isEmpty()) ||
        txtEmail.getText().trim().isEmpty() ||
        txtTelefone.getText().replaceAll("\\D","").isEmpty() ||
        txtRua.getText().trim().isEmpty() ||
        txtNum.getText().trim().isEmpty() ||
        txtBairro.getText().trim().isEmpty() ||
        txtCidade.getText().trim().isEmpty() ||
        txtCEP.getText().trim().isEmpty() ||
        txtPais.getText().trim().isEmpty() ||
        (!radPF.isSelected() && !radPJ.isSelected())) 
    {
        JOptionPane.showMessageDialog(this, "Todos os campos são obrigatórios!");
        return;
    }
    
    // Criando o objeto Cliente
    Beans.Cliente cliente = new Beans.Cliente();
    ClienteDAO dao = new ClienteDAO();
    
    cliente.setNome(nome);
    cliente.setTipo(radPF.isSelected()); // true = PF, false = PJ
    cliente.setCpf(radPF.isSelected() ? cpfCnpj : null);
    cliente.setCnpj(radPJ.isSelected() ? cpfCnpj : null);
    cliente.setEmail(txtEmail.getText().trim());
    cliente.setTelefone(txtTelefone.getText().replaceAll("\\D",""));
    cliente.setRua(txtRua.getText().trim());
    cliente.setNumero(txtNum.getText().trim());
    cliente.setBairro(txtBairro.getText().trim());
    cliente.setCidade(txtCidade.getText().trim());
    cliente.setEstado((String) cmbUF.getSelectedItem());
    cliente.setCep(txtCEP.getText().trim());
    cliente.setPais(txtPais.getText().trim());
    
    boolean sucesso = false;


    

    if (!edicaoAtiva) {
    
        int idGerado = dao.inserirCliente(cliente);
        sucesso = (idGerado != -1);

        if (sucesso) {
            JOptionPane.showMessageDialog(this, "Cliente inserido com sucesso! "
                    + "ID: " + idGerado);
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao inserir cliente!");
        }
    } else {
        // ATUALIZAR
        int id = Integer.parseInt(txtCod.getText().trim());
        cliente.setId(id);
        sucesso = dao.editarCliente(cliente);

        if (sucesso) {
            JOptionPane.showMessageDialog(this, 
                    "Cliente atualizado com sucesso!");
        } else {
            JOptionPane.showMessageDialog(this, 
                    "Erro ao atualizar cliente!");
        }
        edicaoAtiva= false;
    }

    
    if (sucesso) {
        limparFormulario();         
        atualizarProximoId();       
        DefaultTableModel modelo = (DefaultTableModel) tblCli.getModel();
        modelo.setRowCount(0);     
        txtNome.requestFocusInWindow();
    }
    }//GEN-LAST:event_btnSalvarActionPerformed
    
    private void radPFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radPFActionPerformed
        txtCpfCnpj.setText(""); 
        setCpfMask();
    }//GEN-LAST:event_radPFActionPerformed

    private void radPJActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radPJActionPerformed
        txtCpfCnpj.setText(""); 
        setCnpjMask();
    }//GEN-LAST:event_radPJActionPerformed

    private void btnLimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimparActionPerformed
        limparFormulario();
    }//GEN-LAST:event_btnLimparActionPerformed

    private void btnListarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnListarActionPerformed
        preencherTabela();
    }//GEN-LAST:event_btnListarActionPerformed

    private void bnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bnExcluirActionPerformed
    int linhaSelecionada = tblCli.getSelectedRow();

    if (linhaSelecionada >= 0) {
    DefaultTableModel modelo = (DefaultTableModel) tblCli.getModel();
    int id = (int) modelo.getValueAt(linhaSelecionada, 0);

    ClienteDAO dao = new ClienteDAO();

    
    if (dao.temNotaFiscalVinculada(id)) {
        JOptionPane.showMessageDialog(this, "Este cliente está vinculado "
                + "a uma nota fiscal e não pode ser excluído.");
        return;
    }

    int opcao = JOptionPane.showConfirmDialog(this, 
        "Deseja realmente excluir este cliente?", 
        "Confirmação", 
        JOptionPane.YES_NO_OPTION);

    if (opcao == JOptionPane.YES_OPTION) {
        Beans.Cliente cliente = new Beans.Cliente();
        cliente.setId(id);

        dao.excluirCliente(cliente); 
        modelo.removeRow(linhaSelecionada);

        JOptionPane.showMessageDialog(this, "Cliente excluído com sucesso!");
    }
    } else {
        JOptionPane.showMessageDialog(this, "Selecione um cliente na tabela "
                + "para excluir!");
    }

    }//GEN-LAST:event_bnExcluirActionPerformed

    private void txtCodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodActionPerformed
        
    }//GEN-LAST:event_txtCodActionPerformed

    private void menLNFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menLNFActionPerformed
        ConsultaNotaFiscal tela= new ConsultaNotaFiscal();
        tela.setVisible(true);
        this.dispose();
       
    }//GEN-LAST:event_menLNFActionPerformed
    
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
        java.awt.EventQueue.invokeLater(() -> new Cliente().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bnExcluir;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnLimpar;
    private javax.swing.JButton btnListar;
    private javax.swing.JButton btnSalvar;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cmbUF;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JRadioButtonMenuItem jRadioButtonMenuItem1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblBairro;
    private javax.swing.JLabel lblCEP;
    private javax.swing.JLabel lblCidade;
    private javax.swing.JLabel lblCod;
    private javax.swing.JLabel lblCpfCnpj;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblNome;
    private javax.swing.JLabel lblNome3;
    private javax.swing.JLabel lblNome4;
    private javax.swing.JLabel lblNumero;
    private javax.swing.JLabel lblPais;
    private javax.swing.JLabel lblRua;
    private javax.swing.JLabel lblTelefone;
    private javax.swing.JLabel lblTipo;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblUF;
    private javax.swing.JMenuItem menENF;
    private javax.swing.JMenu menFornecedor;
    private javax.swing.JMenuItem menFrn;
    private javax.swing.JMenu menInicio;
    private javax.swing.JMenuItem menLNF;
    private javax.swing.JMenu menNF;
    private javax.swing.JMenuItem menPrd;
    private javax.swing.JMenuItem menPrincipal;
    private javax.swing.JMenu menProduto;
    private javax.swing.JRadioButton radPF;
    private javax.swing.JRadioButton radPJ;
    private javax.swing.JTable tblCli;
    private javax.swing.JTextField txtBairro;
    private javax.swing.JFormattedTextField txtCEP;
    private javax.swing.JTextField txtCidade;
    private javax.swing.JTextField txtCod;
    private javax.swing.JFormattedTextField txtCpfCnpj;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtNome;
    private javax.swing.JTextField txtNum;
    private javax.swing.JTextField txtPais;
    private javax.swing.JTextField txtRua;
    private javax.swing.JFormattedTextField txtTelefone;
    // End of variables declaration//GEN-END:variables
}
