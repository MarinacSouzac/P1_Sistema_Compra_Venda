/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Beans.Fornecedor;
import Conexao.Conexao;
import Beans.Produto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
        
        

/**
 *
 * @author Marina Souza
 */
public class ProdutoDAO {
    
    private Conexao conexao;
    private Connection conn;

    public ProdutoDAO() {
        this.conexao = new Conexao();
        this.conn = this.conexao.getConexao();
    }
    
    
    public int inserirProduto(Produto produto){
        String sql = "INSERT INTO produto (prd_nome,prd_cod_barras,"
                + "prd_descricao,prd_preco_venda,"
                + "prd_qtd_estoque,fnc_id) VALUES(?,?,?,?,?,?)";
       
    if (produto.getFornecedor() == null) {
        System.err.println("Erro: Fornecedor do produto não pode ser nulo.");
        
        return -1; 
    }

        try{
            PreparedStatement stmt = this.conn.prepareStatement
            (sql,Statement.RETURN_GENERATED_KEYS);
             stmt.setString(1, produto.getNome());
             stmt.setString(2, produto.getCodBarras());
             stmt.setString(3, produto.getDescricao());
             stmt.setDouble(4, produto.getPrecoVenda());
             stmt.setInt(5, produto.getQtdEstoque());
             stmt.setInt(6, produto.getFornecedor().getId());
             
              int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas == 0) {
                throw new SQLException("Falha ao inserir cliente, "
                        + "nenhuma linha afetada.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Falha ao obter o ID do "
                            + "cliente inserido.");
                }
            }

        } catch (SQLException ex) {
            System.out.println("Erro ao inserir cliente: " + ex.getMessage());
            return -1;
        } }
         
        public int getNextId(){
        int nextId = 1;
        String sql = "SELECT COALESCE(MAX(prd_id), 0) + 1 AS nextId FROM produto";

        try {
            PreparedStatement ps = this.conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                nextId = rs.getInt("nextId");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return nextId;
    }
        
        public List<Produto> getProduto() {
        List<Produto> listaProdutos = new ArrayList<>();
        String sql = "SELECT * FROM produto INNER JOIN fornecedor "
                + "USING(fnc_id)";

        try{ 
            PreparedStatement stmt = conn.prepareStatement(sql,
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Produto produto = new Produto();
                produto.setId(rs.getInt("prd_id"));
                produto.setNome(rs.getString("prd_nome"));
                produto.setCodBarras(rs.getString("prd_cod_barras"));
                produto.setDescricao(rs.getString("prd_descricao"));
                produto.setPrecoVenda(rs.getDouble("prd_preco_venda"));
                produto.setQtdEstoque(rs.getInt("prd_qtd_estoque"));
                Fornecedor f= new Fornecedor();
                
                f.setId(rs.getInt("fnc_id"));
                f.setNome(rs.getString("fnc_nome")); 
                produto.setFornecedor(f);
               
                listaProdutos.add(produto);
                    
                }}catch(SQLException ex){
                       System.out.println("Erro ao consultar "
                               + "todos os produtos: "
                        + ex.getMessage()); 
                        }
                
                return listaProdutos;
                
        }
        
        
        public boolean editarProduto(Produto produto){
            String sql = "UPDATE produto SET "
               + "prd_nome = ?, "
               + "prd_cod_barras = ?, "
               + "prd_descricao = ?, "
               + "prd_preco_venda = ?, "
               + "prd_qtd_estoque = ?, "
               + "fnc_id = ? "
               + "WHERE prd_id = ?";
            
             // NOVO BLOCO: VERIFICAÇÃO DE NULIDADE
    if (produto.getFornecedor() == null) {
        System.err.println("Erro ao editar: Fornecedor do produto "
                + "não pode ser nulo.");
        return false;
    }
    // FIM NOVO BLOCO

    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, produto.getNome());
        stmt.setString(2, produto.getCodBarras());
        stmt.setString(3, produto.getDescricao());
        stmt.setDouble(4, produto.getPrecoVenda());
        stmt.setInt(5, produto.getQtdEstoque());
        stmt.setInt(6, produto.getFornecedor().getId()); 
        stmt.setInt(7, produto.getId()); 

        int linhasAfetadas = stmt.executeUpdate();
        return linhasAfetadas > 0;

    } catch (SQLException ex) {
        System.out.println("Erro ao editar produto: " + ex.getMessage());
        return false;
    }
    }
    
    public void excluirProduto(Produto produto) {
    String sql = "DELETE FROM produto WHERE prd_id = ?";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, produto.getId());
        int linhasAfetadas = stmt.executeUpdate();
        if (linhasAfetadas > 0) {
            System.out.println("Produto excluído com sucesso!");
        } else {
            System.out.println("Nenhum produto encontrado com esse ID.");
        }
    } catch (SQLException ex) {
        System.out.println("Erro ao excluir produto: " + ex.getMessage());
    }}
    
    public List<Produto> getProdutosPorFornecedor(int idFornecedor) {
    List<Produto> listaProdutos = new ArrayList<>();

    String sql = "SELECT * FROM produto WHERE fnc_id = ?";

    try {
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, idFornecedor);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Beans.Produto p = new Beans.Produto();
            p.setId(rs.getInt("prd_id"));
            p.setNome(rs.getString("prd_nome"));
            p.setPrecoVenda(rs.getDouble("prd_preco_venda"));
            p.setQtdEstoque(rs.getInt("prd_qtd_estoque"));
 
            listaProdutos.add(p);
        }

    } catch (SQLException e) {
        System.out.println("Erro ao buscar produtos do fornecedor: " + 
                e.getMessage());
    }
    
    
    return listaProdutos;
}
public Produto buscarPorNome(String nome) {
    String sql = "SELECT * FROM produto WHERE prd_nome = ?";
    try (Connection conn = new Conexao().getConexao();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, nome);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            Produto p = new Produto();
            p.setId(rs.getInt("prd_id"));
            p.setNome(rs.getString("prd_nome"));
            p.setPrecoVenda(rs.getDouble("prd_preco_venda"));
            return p;
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null;
}

public boolean temNotaFiscalVinculada(int idProduto) {
    String sql = "SELECT COUNT(*) FROM itemNotaFiscal WHERE prd_id = ?";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, idProduto);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return rs.getInt(1) > 0;
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
}

public int getEstoqueAtual(int idProduto) {
    String sql = "SELECT prd_qtd_estoque FROM produto WHERE prd_id = ?";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, idProduto);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return rs.getInt("prd_qtd_estoque");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return 0;
}

public void atualizarEstoque(int idProduto, int ajuste) {
    String sql = "UPDATE produto SET prd_qtd_estoque = prd_qtd_estoque + ? WHERE prd_id = ?";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, ajuste);
        stmt.setInt(2, idProduto);
        stmt.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

}

        
                
                
                
                

                

        
    
    

