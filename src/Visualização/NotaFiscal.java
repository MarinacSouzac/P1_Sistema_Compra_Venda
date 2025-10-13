
package Visualização;

import DAO.NotaFiscalDAO;
import Beans.Produto;
import Beans.Fornecedor;
import Beans.Cliente;
import Beans.ItemNotaFiscal;
import DAO.FornecedorDAO;
import DAO.ClienteDAO;
import DAO.ItemNotaFiscalDAO;
import DAO.ProdutoDAO;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Marina Souza
 */
public class NotaFiscal extends javax.swing.JFrame {
     // DAO
    private NotaFiscalDAO notaDAO = new NotaFiscalDAO();
    private FornecedorDAO fornecedorDAO = new FornecedorDAO();
    private ClienteDAO clienteDAO = new ClienteDAO();
    private ProdutoDAO produtoDAO = new ProdutoDAO();
    private Fornecedor fornecedorAtual = null;
    private Cliente clienteAtual = null;

   
    private DefaultTableModel tabelaModel;
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(NotaFiscal.class.getName());
  
    public NotaFiscal() {
        initComponents();
        inicializarFormulario();
    }
    private void inicializarFormulario() {
     cmbCliente.setEnabled(false);
        cmbFornecedor.setEnabled(false);
        cmbProduto.setEnabled(false);
        spnQtd.setEnabled(false);
        btnAdicionarItem.setEnabled(false);
        btnExcluir.setEnabled(false);

        txtCod.setText(String.valueOf(notaDAO.getNextId()));
        txtData.setText(""); 

        preencherComboFornecedores();
        preencherComboClientes();
        preencherComboProdutos();

        tabelaModel = (DefaultTableModel) tblNotaFiscal.getModel();
        tabelaModel.setRowCount(0);

        cmbTipo.addActionListener(e -> atualizarCamposPorTipo());
        cmbFornecedor.addActionListener(e -> atualizarProdutosPorFornecedor());
        cmbCliente.addActionListener(e -> atualizarProdutosParaSaida());

        tblNotaFiscal.getSelectionModel().addListSelectionListener(e -> {
            btnExcluir.setEnabled(tblNotaFiscal.getSelectedRow() >= 0);
        });
}
    private void atualizarCamposPorTipo() {
    String tipo = (String) cmbTipo.getSelectedItem();
        limparTabelaSeNaoVazia();

        fornecedorAtual = null;
        clienteAtual = null;

        if ("0-Entrada".equals(tipo)) {
            cmbFornecedor.setEnabled(true);
            cmbCliente.setEnabled(false);
            cmbCliente.setSelectedIndex(-1);
        } else if ("1-Saída".equals(tipo)) {
            cmbCliente.setEnabled(true);
            cmbFornecedor.setEnabled(false);
            cmbFornecedor.setSelectedIndex(-1);
        }

        cmbProduto.setEnabled(false);
        spnQtd.setEnabled(false);
        btnAdicionarItem.setEnabled(false);
        btnExcluir.setEnabled(false);
    }
     private void limparTabelaSeNaoVazia() {
        if (tabelaModel.getRowCount() > 0) {
            int opcao = JOptionPane.showConfirmDialog(this, 
                "Alterar cliente/fornecedor irá limpar os itens adicionados. Continuar?",
                "Confirmação", JOptionPane.YES_NO_OPTION);
            if (opcao == JOptionPane.YES_OPTION) {
                tabelaModel.setRowCount(0);
                atualizarTotais();
            }
        }
    }

    private void atualizarProdutosPorFornecedor() {
        Fornecedor fornecedor = (Fornecedor) cmbFornecedor.getSelectedItem();
        cmbProduto.removeAllItems();
        if (fornecedor != null) {
            List<Produto> produtos = produtoDAO.getProdutosPorFornecedor(fornecedor.getId());
            for (Produto p : produtos) {
                cmbProduto.addItem(p);
            }
            boolean habilitar = !produtos.isEmpty();
            cmbProduto.setEnabled(habilitar);
            spnQtd.setEnabled(habilitar);
            btnAdicionarItem.setEnabled(habilitar);
        }
    }

    private void atualizarProdutosParaSaida() {
        cmbProduto.removeAllItems();
        List<Produto> produtos = produtoDAO.getProduto();
        for (Produto p : produtos) {
            cmbProduto.addItem(p);
        }
        boolean habilitar = !produtos.isEmpty();
        cmbProduto.setEnabled(habilitar);
        spnQtd.setEnabled(habilitar);
        btnAdicionarItem.setEnabled(habilitar);
    }

      private void preencherComboFornecedores() {
        cmbFornecedor.removeAllItems();
        for (Fornecedor f : fornecedorDAO.getFornecedor()) {
            cmbFornecedor.addItem(f);
        }
    }

    private void preencherComboClientes() {
        cmbCliente.removeAllItems();
        for (Cliente c : clienteDAO.getCliente()) {
            cmbCliente.addItem(c);
        }
    }
    private void preencherComboProdutos() {
        cmbProduto.removeAllItems();
        for (Produto p : produtoDAO.getProduto()) {
            cmbProduto.addItem(p);
        }
    }
    
    private void atualizarTotais() {
        double total = 0;
        int qtdTotal = 0;

        for (int i = 0; i < tabelaModel.getRowCount(); i++) {
            Produto p = (Produto) tabelaModel.getValueAt(i, 0);
            int qtd = (int) tabelaModel.getValueAt(i, 1);
            double subtotal = (double) tabelaModel.getValueAt(i, 3);
            total += subtotal;
            qtdTotal += qtd;
        }

        txtTotal.setText(String.format("%.2f", total));
        txtQtdt.setText(String.valueOf(qtdTotal));
    }

     private void limparFormulario() {
        cmbTipo.setSelectedIndex(0);
        txtData.setText("");
        cmbCliente.setSelectedIndex(-1);
        cmbFornecedor.setSelectedIndex(-1);
        cmbProduto.setSelectedIndex(-1);
        spnQtd.setValue(1);
        tabelaModel.setRowCount(0);
        atualizarTotais();
        btnAdicionarItem.setEnabled(false);
        btnExcluir.setEnabled(false);
    }
     
     private void adicionarItem() {
        Produto produto = (Produto) cmbProduto.getSelectedItem();
        int quantidade = (int) spnQtd.getValue();

        if (produto == null || quantidade <= 0) {
            JOptionPane.showMessageDialog(this, "Selecione um produto e quantidade válida!");
            return;
        }

        double subtotal = produto.getPrecoVenda() * quantidade;
        tabelaModel.addRow(new Object[]{produto, quantidade, produto.getPrecoVenda(), subtotal});
        atualizarTotais();
        btnExcluir.setEnabled(true);
    }
     
     public void preencherTabela(int idNota){
        ItemNotaFiscalDAO iDAO = new ItemNotaFiscalDAO();
        List<ItemNotaFiscal> listaItensNotaFiscal = iDAO.getItensPorNota(idNota);

        tabelaModel.setRowCount(0); 
        for (ItemNotaFiscal item : listaItensNotaFiscal) {
            tabelaModel.addRow(new Object[]{
                item.getPrd(),
                item.getQuantidade(),
                item.getPrd().getPrecoVenda(),
                item.getSubtotal()
            });
        }

        // Renderiza só o nome do produto
        tblNotaFiscal.getColumnModel().getColumn(0).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public void setValue(Object value) {
                if (value instanceof Produto) {
                    setText(((Produto) value).getNome());
                } else {
                    super.setValue(value);
                }
            }
        });

        atualizarTotais();
    }
     
     private void salvarNota() {
        boolean tipoSaida = cmbTipo.getSelectedItem().toString().equalsIgnoreCase("1-Saída");

        if (tipoSaida && clienteAtual == null) {
            JOptionPane.showMessageDialog(this, "Selecione um cliente para saída.");
            return;
        }
        if (!tipoSaida && fornecedorAtual == null) {
            JOptionPane.showMessageDialog(this, "Selecione um fornecedor para entrada.");
            return;
        }
         Beans.NotaFiscal nota = new Beans.NotaFiscal();
        nota.setTipo(tipoSaida);
        nota.setDataVenda(LocalDateTime.now());
        nota.setQtdTotal(Integer.parseInt(txtQtdt.getText()));
        nota.setValorTotal(Double.parseDouble(txtTotal.getText()));

        if (tipoSaida) {
            nota.setCliente(clienteAtual);
        } else {
            nota.setFornecedor(fornecedorAtual);
        }

        int idNota = notaDAO.inserirNotaFiscal(nota);
        if (idNota <= 0) {
            JOptionPane.showMessageDialog(this, "Erro ao inserir nota fiscal.");
            return;
        }
        
      ItemNotaFiscalDAO daoItem = new ItemNotaFiscalDAO();
        for (int i = 0; i < tabelaModel.getRowCount(); i++) {
            Produto produto = (Produto) tabelaModel.getValueAt(i, 0);
            if (produto == null) continue;

            int qtd = (int) tabelaModel.getValueAt(i, 1);
            double precoUnit = (double) tabelaModel.getValueAt(i, 2);
            double subtotal = (double) tabelaModel.getValueAt(i, 3);
            
            ItemNotaFiscal item = new ItemNotaFiscal();
            item.setPrd(produto);
            item.setQuantidade(qtd);
            item.setPrecoUnidade(precoUnit);
            item.setSubtotal(subtotal);
            item.setNtf(nota);

            int idItem = daoItem.inserirItemNotaFiscal(item);
            if (idItem <= 0) {
                JOptionPane.showMessageDialog(this, "Erro ao inserir um item da nota.");
           }
        }

        JOptionPane.showMessageDialog(this, "Nota fiscal e itens inseridos com sucesso! ID: " + idNota);
        limparFormulario();
        preencherTabela(idNota);
    }
     
     
     
    
  

    private void atualizarProximoId() {
    NotaFiscalDAO dao = new NotaFiscalDAO();
    int proximoId = dao.getNextId();
    txtCod.setText(String.valueOf(proximoId));
     }
    
   
  


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTitulo = new javax.swing.JLabel();
        lblCod = new javax.swing.JLabel();
        txtCod = new javax.swing.JTextField();
        lblData = new javax.swing.JLabel();
        txtData = new javax.swing.JFormattedTextField();
        lblTipo = new javax.swing.JLabel();
        cmbTipo = new javax.swing.JComboBox<>();
        lblCliente = new javax.swing.JLabel();
        cmbCliente = new javax.swing.JComboBox();
        lblFornecedor = new javax.swing.JLabel();
        cmbFornecedor = new javax.swing.JComboBox();
        lblQtd = new javax.swing.JLabel();
        cmbProduto = new javax.swing.JComboBox();
        lblProduto = new javax.swing.JLabel();
        spnQtd = new javax.swing.JSpinner(new javax.swing.SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1))
        ;
        btnAdicionarItem = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblNotaFiscal = new javax.swing.JTable();
        btnListar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnSalvar = new javax.swing.JButton();
        btnLimpar = new javax.swing.JButton();
        lblTotal = new javax.swing.JLabel();
        txtTotal = new javax.swing.JTextField();
        txtQtdt = new javax.swing.JTextField();
        lblQtdTotal = new javax.swing.JLabel();
        btnExcluir = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        menInicio = new javax.swing.JMenu();
        menPrincipal = new javax.swing.JMenuItem();
        menCliente = new javax.swing.JMenu();
        menCli = new javax.swing.JMenuItem();
        menFornecedor = new javax.swing.JMenu();
        menFrn = new javax.swing.JMenuItem();
        menProduto = new javax.swing.JMenu();
        menPrd = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        lblTitulo.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblTitulo.setText("Emissão de Nota Fiscal");

        lblCod.setText("Código da Nota");

        txtCod.setEditable(false);
        txtCod.setBackground(new java.awt.Color(204, 204, 204));
        txtCod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodActionPerformed(evt);
            }
        });

        lblData.setText("Data");

        txtData.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(java.text.DateFormat.getDateInstance(java.text.DateFormat.SHORT))));

        lblTipo.setText("Tipo");

        cmbTipo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0-Entrada", "1-Saída" }));
        cmbTipo.setSelectedIndex(-1);
        cmbTipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbTipoActionPerformed(evt);
            }
        });

        lblCliente.setText("Cliente");

        cmbCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbClienteActionPerformed(evt);
            }
        });

        lblFornecedor.setText("Fornecedor");

        cmbFornecedor.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " " }));
        cmbFornecedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbFornecedorActionPerformed(evt);
            }
        });

        lblQtd.setText("Quantidade");

        cmbProduto.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " " }));

        lblProduto.setText("Produto");

        btnAdicionarItem.setText("Adicionar Item");
        btnAdicionarItem.setEnabled(false);
        btnAdicionarItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdicionarItemActionPerformed(evt);
            }
        });

        tblNotaFiscal.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Produto", "Quantidade", "Preço Unitário", "Subtotal"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Integer.class, java.lang.Double.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(tblNotaFiscal);

        jScrollPane2.setViewportView(jScrollPane3);

        btnListar.setText("Listar Notas Fiscais");
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

        lblTotal.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTotal.setText("Valor total");

        txtTotal.setEditable(false);
        txtTotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTotalActionPerformed(evt);
            }
        });

        txtQtdt.setEditable(false);

        lblQtdTotal.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblQtdTotal.setText("Quantidade total");

        btnExcluir.setText("Excluir Item");
        btnExcluir.setEnabled(false);
        btnExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirActionPerformed(evt);
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

        menProduto.setText("Produto");

        menPrd.setText("Gerenciar Produtos");
        menPrd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menPrdActionPerformed(evt);
            }
        });
        menProduto.add(menPrd);

        jMenuBar1.add(menProduto);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblProduto)
                    .addComponent(lblCliente)
                    .addComponent(lblTipo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(cmbTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(lblCod)
                        .addGap(18, 18, 18)
                        .addComponent(txtCod, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)
                        .addComponent(lblData)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtData, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cmbProduto, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cmbCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(41, 41, 41)
                                .addComponent(lblFornecedor)
                                .addGap(18, 18, 18)
                                .addComponent(cmbFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addComponent(lblQtd)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(spnQtd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(63, 63, 63)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnAdicionarItem, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnSalvar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnLimpar))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblQtdTotal, javax.swing.GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtQtdt, javax.swing.GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE)
                        .addGap(12, 12, 12)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnListar, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGap(93, 93, 93)
                        .addComponent(lblTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTotal, javax.swing.GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE)
                        .addGap(12, 12, 12))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addGap(12, 12, 12))
            .addGroup(layout.createSequentialGroup()
                .addGap(233, 233, 233)
                .addComponent(lblTitulo)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnCancelar, btnLimpar, btnListar, btnSalvar});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(lblTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCod)
                    .addComponent(txtCod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblData)
                    .addComponent(txtData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTipo)
                    .addComponent(cmbTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCliente)
                    .addComponent(cmbCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblFornecedor)
                    .addComponent(cmbFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbProduto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(spnQtd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblProduto)
                    .addComponent(btnAdicionarItem)
                    .addComponent(lblQtd))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnExcluir)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTotal)
                    .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtQtdt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblQtdTotal))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCancelar)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnSalvar)
                        .addComponent(btnListar)
                        .addComponent(btnLimpar)))
                .addGap(12, 12, 12))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {lblQtdTotal, lblTotal, txtQtdt, txtTotal});

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtCodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodActionPerformed

    private void btnAdicionarItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicionarItemActionPerformed
      adicionarItem();
    }//GEN-LAST:event_btnAdicionarItemActionPerformed

    private void btnListarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnListarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnListarActionPerformed

    private void menPrincipalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menPrincipalActionPerformed
        Menu tela= new Menu();
        tela.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_menPrincipalActionPerformed

    private void menCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menCliActionPerformed
        Visualização.Cliente tela= new Visualização.Cliente();
        tela.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_menCliActionPerformed

    private void menFrnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menFrnActionPerformed
        Visualização.Fornecedor tela= new Visualização.Fornecedor();
        tela.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_menFrnActionPerformed

    private void menPrdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menPrdActionPerformed
        Visualização.Produto tela= new Visualização.Produto();
        tela.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_menPrdActionPerformed

    private void txtTotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTotalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTotalActionPerformed

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        int linha = tblNotaFiscal.getSelectedRow();
        if (linha >= 0) {
            tabelaModel.removeRow(linha);
            atualizarTotais();
        }
        btnExcluir.setEnabled(tabelaModel.getRowCount() > 0);
    }//GEN-LAST:event_btnExcluirActionPerformed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
            salvarNota();
  
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void btnLimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimparActionPerformed
        limparFormulario();
    }//GEN-LAST:event_btnLimparActionPerformed

    private void cmbTipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbTipoActionPerformed
      atualizarCamposPorTipo();
    }//GEN-LAST:event_cmbTipoActionPerformed

    private void cmbFornecedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbFornecedorActionPerformed
    Fornecedor selecionado = (Fornecedor) cmbFornecedor.getSelectedItem();

    if (selecionado != null && fornecedorAtual != null && !fornecedorAtual.equals(selecionado)) {
        // Limpa a tabela se mudou de fornecedor
        tabelaModel.setRowCount(0);
        atualizarTotais();
    }
    fornecedorAtual = selecionado;
    atualizarProdutosPorFornecedor();
    }//GEN-LAST:event_cmbFornecedorActionPerformed

    private void cmbClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbClienteActionPerformed
      Cliente selecionado = (Cliente) cmbCliente.getSelectedItem();

    if (selecionado != null && clienteAtual != null && !clienteAtual.equals(selecionado)) {

    }//GEN-LAST:event_cmbClienteActionPerformed

    /**
     * @param args the command line arguments
     */
    }    public static void main(String args[]) {
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
        java.awt.EventQueue.invokeLater(() -> new NotaFiscal().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdicionarItem;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnLimpar;
    private javax.swing.JButton btnListar;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JComboBox cmbCliente;
    private javax.swing.JComboBox cmbFornecedor;
    private javax.swing.JComboBox cmbProduto;
    private javax.swing.JComboBox<String> cmbTipo;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblCliente;
    private javax.swing.JLabel lblCod;
    private javax.swing.JLabel lblData;
    private javax.swing.JLabel lblFornecedor;
    private javax.swing.JLabel lblProduto;
    private javax.swing.JLabel lblQtd;
    private javax.swing.JLabel lblQtdTotal;
    private javax.swing.JLabel lblTipo;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JMenuItem menCli;
    private javax.swing.JMenu menCliente;
    private javax.swing.JMenu menFornecedor;
    private javax.swing.JMenuItem menFrn;
    private javax.swing.JMenu menInicio;
    private javax.swing.JMenuItem menPrd;
    private javax.swing.JMenuItem menPrincipal;
    private javax.swing.JMenu menProduto;
    private javax.swing.JSpinner spnQtd;
    private javax.swing.JTable tblNotaFiscal;
    private javax.swing.JTextField txtCod;
    private javax.swing.JFormattedTextField txtData;
    private javax.swing.JTextField txtQtdt;
    private javax.swing.JTextField txtTotal;
    // End of variables declaration//GEN-END:variables
}
