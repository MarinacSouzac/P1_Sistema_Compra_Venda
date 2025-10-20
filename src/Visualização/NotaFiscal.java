package Visualização;

import DAO.NotaFiscalDAO;
import DAO.ProdutoDAO;
import DAO.FornecedorDAO;
import DAO.ClienteDAO;
import DAO.ItemNotaFiscalDAO;
import Beans.ItemNotaFiscal;
import Beans.Produto;
import Beans.Fornecedor;
import Beans.Cliente;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class NotaFiscal extends javax.swing.JFrame {

    // Guarda o cliente ou fornecedor fixado após adicionar o primeiro item
    private Cliente clienteFixado = null;
    private Fornecedor fornecedorFixado = null;

    public NotaFiscal() {
        initComponents();
        setLocationRelativeTo(null);
        configurarTipoNota();
        txtCod.setFocusable(false);
        txtData.setFocusable(false);
        atualizarProximoId();
        cmbTipo.setSelectedIndex(0);

        // Listeners de botões e combos
        cmbTipo.addActionListener(e -> configurarTipoNota());
        btnAdicionarItem.addActionListener(e -> adicionarItem());
        btnSalvar.addActionListener(e -> salvarNota());
        btnLimpar.addActionListener(e -> limparCampos());
        cmbFornecedor.addActionListener(e -> carregarProdutosPorFornecedor());
        btnExcluir.addActionListener(e -> excluirItemSelecionado());

        // Habilita o botão de excluir quando uma linha é selecionada
        tblNotaFiscal.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tblNotaFiscal.getSelectedRow() != -1) {
                btnExcluir.setEnabled(true);
            }
        });

        ((DefaultTableModel) tblNotaFiscal.getModel()).setRowCount(0);

        // Define a data atual automaticamente
        txtData.setText(
            java.time.LocalDate.now().format(
                java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")
            )
        );
    }

    // Atualiza o próximo ID da nota fiscal
    private void atualizarProximoId() {
        NotaFiscalDAO dao = new NotaFiscalDAO();
        int proximoId = dao.getNextId();
        txtCod.setText(String.valueOf(proximoId));
    }

    // Carrega fornecedores no combo
    private void carregarFornecedor() {
        cmbFornecedor.removeAllItems();
        FornecedorDAO dao = new FornecedorDAO();
        for (Fornecedor f : dao.getFornecedor()) {
            cmbFornecedor.addItem(f);
        }
        cmbFornecedor.setSelectedIndex(-1);
    }

    // Carrega clientes no combo
    private void carregarCliente() {
        cmbCliente.removeAllItems();
        ClienteDAO dao = new ClienteDAO();
        for (Cliente c : dao.getCliente()) {
            cmbCliente.addItem(c);
        }
        cmbCliente.setSelectedIndex(-1);
    }

    // Configura o tipo da nota (Entrada ou Saída)
    private void configurarTipoNota() {
        DefaultTableModel model = (DefaultTableModel) tblNotaFiscal.getModel();
        boolean temItens = false;

        // Verifica se já existem itens na nota
        for (int i = 0; i < model.getRowCount(); i++) {
            Object obj = model.getValueAt(i, 0);
            if (obj instanceof Produto) {
                temItens = true;
                break;
            }
        }

        // Impede mudar o tipo da nota se já houver itens
        if (temItens) {
            JOptionPane.showMessageDialog(this, 
                "Não é possível alterar o tipo da nota após adicionar itens.");
            return;
        }

        int tipo = cmbTipo.getSelectedIndex(); // 0 = Entrada, 1 = Saída

        if (tipo == 0) { // Entrada
            cmbFornecedor.setEnabled(true);
            cmbCliente.setEnabled(false);
            carregarFornecedor();
            carregarProdutosPorFornecedor();
        } else if (tipo == 1) { // Saída
            cmbFornecedor.setEnabled(false);
            cmbCliente.setEnabled(true);
            carregarCliente();
            carregarProdutosPorCliente();
        }

        btnAdicionarItem.setEnabled(tipo != -1);
    }

    // Carrega produtos do fornecedor selecionado
    private void carregarProdutosPorFornecedor() {
        cmbProduto.removeAllItems();
        Fornecedor atual = (Fornecedor) cmbFornecedor.getSelectedItem();

        // Impede troca de fornecedor após adicionar itens
        if (fornecedorFixado != null && atual != null && atual.getId() != fornecedorFixado.getId()) {
            JOptionPane.showMessageDialog(this, 
                "Não é permitido trocar de fornecedor após adicionar itens.");
            cmbFornecedor.setSelectedItem(fornecedorFixado);
            return;
        }

        Object selected = cmbFornecedor.getSelectedItem();
        if (!(selected instanceof Fornecedor)) return;

        Fornecedor f = (Fornecedor) selected;
        ProdutoDAO dao = new ProdutoDAO();
        List<Produto> produtos = dao.getProdutosPorFornecedor(f.getId());

        for (Produto p : produtos) {
            cmbProduto.addItem(p);
        }

        cmbProduto.setSelectedIndex(-1);
    }

    // Carrega produtos (geral) para cliente
    private void carregarProdutosPorCliente() {
        cmbProduto.removeAllItems();
        //Cliente atual = (Cliente) cmbCliente.getSelectedItem();

        // Impede troca de cliente após adicionar itens
        

        //Object selected = cmbCliente.getSelectedItem();
        //if (!(selected instanceof Cliente)) return;

       // Cliente c = (Cliente) selected;
        ProdutoDAO dao = new ProdutoDAO();
        List<Produto> produtos = dao.getProduto();

        for (Produto p : produtos) {
            cmbProduto.addItem(p);
        }

        cmbProduto.setSelectedIndex(-1);
    }

    // Adiciona item à tabela
    private void adicionarItem() {
        try {
            Produto p = (Produto) cmbProduto.getSelectedItem();
            if (p == null) {
                JOptionPane.showMessageDialog(this, "Selecione um produto!");
                return;
            }

            int qtd = (Integer) spnQtd.getValue();
            if (qtd <= 0) {
                JOptionPane.showMessageDialog(this, "Quantidade deve ser maior que zero!");
                return;
            }

            DefaultTableModel model = (DefaultTableModel) tblNotaFiscal.getModel();

            // Verifica se o produto já foi adicionado
            for (int i = 0; i < model.getRowCount(); i++) {
                Object obj = model.getValueAt(i, 0);
                if (obj instanceof Produto) {
                    Produto existente = (Produto) obj;
                    if (existente.getId() == p.getId()) {
                        JOptionPane.showMessageDialog(this, "Produto já adicionado!");
                        return;
                    }
                }
            }

            double preco = p.getPrecoVenda();
            double subtotal = qtd * preco;

            model.addRow(new Object[]{p, qtd, preco, subtotal});
            atualizarTotais();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao adicionar item: " + ex.getMessage());
            ex.printStackTrace();
        }

        // Fixa cliente ou fornecedor
        if (cmbTipo.getSelectedIndex() == 1 && clienteFixado == null) {
            clienteFixado = (Cliente) cmbCliente.getSelectedItem();
        } else if (cmbTipo.getSelectedIndex() == 0 && fornecedorFixado == null) {
            fornecedorFixado = (Fornecedor) cmbFornecedor.getSelectedItem();
        }
    }

    // Atualiza total e quantidade total
    private void atualizarTotais() {
        double total = 0;
        int qtdTotal = 0;
        DefaultTableModel model = (DefaultTableModel) tblNotaFiscal.getModel();

        for (int i = 0; i < model.getRowCount(); i++) {
            int qtd = ((Number) model.getValueAt(i, 1)).intValue();
            double preco = ((Number) model.getValueAt(i, 2)).doubleValue();
            total += qtd * preco;
            qtdTotal += qtd;
        }

        txtTotal.setText(String.format("%.2f", total));
        txtQtdt.setText(String.valueOf(qtdTotal));
    }

    // Exclui item selecionado
    private void excluirItemSelecionado() {
        int linha = tblNotaFiscal.getSelectedRow();
        if (linha != -1) {
            DefaultTableModel model = (DefaultTableModel) tblNotaFiscal.getModel();
            model.removeRow(linha);
            atualizarTotais();
        }
    }

    // Limpa todos os campos e tabela
    private void limparCampos() {
        txtTotal.setText("");
        txtQtdt.setText("");
        spnQtd.setValue(1);
        cmbCliente.setSelectedIndex(-1);
        cmbFornecedor.setSelectedIndex(-1);
        cmbProduto.setSelectedIndex(-1);
        ((DefaultTableModel) tblNotaFiscal.getModel()).setRowCount(0);
        clienteFixado = null;
        fornecedorFixado = null;
    }

    // Salva a nota fiscal e seus itens
    private void salvarNota() {
        try {
            int tipo = cmbTipo.getSelectedIndex(); // 0 = Entrada, 1 = Saída
            if (tipo == -1) {
                JOptionPane.showMessageDialog(this, "Selecione o tipo da nota!");
                return;
            }

            Beans.NotaFiscal nota = new Beans.NotaFiscal();
            nota.setTipo(tipo == 1); // true = Saída, false = Entrada

            // Define cliente ou fornecedor
            if (tipo == 1) { // Saída
                Cliente cliente = (Cliente) cmbCliente.getSelectedItem();
                if (cliente == null) {
                    JOptionPane.showMessageDialog(this, "Selecione um cliente!");
                    return;
                }
                nota.setCliente(cliente);
            } else { // Entrada
                Fornecedor fornecedor = (Fornecedor) cmbFornecedor.getSelectedItem();
                if (fornecedor == null) {
                    JOptionPane.showMessageDialog(this, "Selecione um fornecedor!");
                    return;
                }
                nota.setFornecedor(fornecedor);
            }

            nota.setDataVenda(java.time.LocalDate.now());

            // Cria lista de itens
            List<ItemNotaFiscal> itens = new ArrayList<>();
            DefaultTableModel model = (DefaultTableModel) tblNotaFiscal.getModel();

            for (int i = 0; i < model.getRowCount(); i++) {
                Produto p = (Produto) model.getValueAt(i, 0);
                int qtd = ((Number) model.getValueAt(i, 1)).intValue();
                double preco = ((Number) model.getValueAt(i, 2)).doubleValue();

                ItemNotaFiscal item = new ItemNotaFiscal();
                item.setPrd(p);
                item.setQuantidade(qtd);
                item.setPrecoUnidade(preco);
                item.setSubtotal(preco * qtd);

                itens.add(item);
            }

            if (itens.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Adicione pelo menos um item à nota!");
                return;
            }

            // Verifica estoque se for saída
            ProdutoDAO produtoDAO = new ProdutoDAO();
            if (tipo == 1) {
                for (ItemNotaFiscal item : itens) {
                    int estoqueAtual = produtoDAO.getEstoqueAtual(item.getPrd().getId());
                    if (item.getQuantidade() > estoqueAtual) {
                        JOptionPane.showMessageDialog(this,
                            "Produto '" + item.getPrd().getNome() + 
                            "' possui apenas " + estoqueAtual + " unidades em estoque.");
                        return;
                    }
                }
            }

            // Define totais
            nota.setItens(itens);
            nota.setQtdTotal(itens.stream().mapToInt(ItemNotaFiscal::getQuantidade).sum());
            nota.setValorTotal(itens.stream().mapToDouble(ItemNotaFiscal::getSubtotal).sum());

            // Salva nota e itens
            NotaFiscalDAO dao = new NotaFiscalDAO();
            int idGerado = dao.inserirNotaFiscal(nota);

            if (idGerado > 0) {
                nota.setId(idGerado);
                ItemNotaFiscalDAO itemDao = new ItemNotaFiscalDAO();

                for (ItemNotaFiscal item : itens) {
                    item.setNtf(nota);
                    itemDao.inserirItem(item);

                    int ajuste = tipo == 1 ? -item.getQuantidade() : item.getQuantidade();
                    produtoDAO.atualizarEstoque(item.getPrd().getId(), ajuste);
                }

                JOptionPane.showMessageDialog(this, "Nota salva com sucesso! ID: " + idGerado);
                limparCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao salvar a nota!");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage());
            e.printStackTrace();
        }
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
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setLocation(new java.awt.Point(0, 0));
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

        txtData.setEditable(false);
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
        cmbProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbProdutoActionPerformed(evt);
            }
        });

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

        jMenu1.setText("Nota Fiscal");

        jMenuItem1.setText("Listar Notas Fiscais");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

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
                        .addComponent(lblQtdTotal, javax.swing.GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtQtdt, javax.swing.GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE)
                        .addGap(78, 78, 78))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnSalvar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnLimpar)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(lblTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTotal, javax.swing.GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE)
                        .addGap(12, 12, 12))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnListar, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(60, 60, 60))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addGap(12, 12, 12))
            .addGroup(layout.createSequentialGroup()
                .addGap(233, 233, 233)
                .addComponent(lblTitulo)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnLimpar, btnListar, btnSalvar});

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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSalvar)
                    .addComponent(btnListar)
                    .addComponent(btnLimpar))
                .addGap(12, 12, 12))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {lblQtdTotal, lblTotal, txtQtdt, txtTotal});

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtCodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodActionPerformed

    private void btnAdicionarItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicionarItemActionPerformed
         
    }//GEN-LAST:event_btnAdicionarItemActionPerformed

    private void btnListarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnListarActionPerformed
         ConsultaNotaFiscal tela= new ConsultaNotaFiscal();
         tela.setVisible(true);
         this.dispose();        
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
 
    }//GEN-LAST:event_btnExcluirActionPerformed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed

        

    }//GEN-LAST:event_btnSalvarActionPerformed

    private void btnLimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimparActionPerformed
        
    }//GEN-LAST:event_btnLimparActionPerformed

    private void cmbTipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbTipoActionPerformed
      
    }//GEN-LAST:event_cmbTipoActionPerformed

    private void cmbFornecedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbFornecedorActionPerformed
         
    }//GEN-LAST:event_cmbFornecedorActionPerformed

    private void cmbClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbClienteActionPerformed
  
    }//GEN-LAST:event_cmbClienteActionPerformed

    private void cmbProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbProdutoActionPerformed
      
    }//GEN-LAST:event_cmbProdutoActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        ConsultaNotaFiscal tela= new ConsultaNotaFiscal();
        tela.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jMenuItem1ActionPerformed


        public static void main(String args[]) {
        

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new NotaFiscal().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdicionarItem;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnLimpar;
    private javax.swing.JButton btnListar;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JComboBox cmbCliente;
    private javax.swing.JComboBox cmbFornecedor;
    private javax.swing.JComboBox cmbProduto;
    private javax.swing.JComboBox<String> cmbTipo;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
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
