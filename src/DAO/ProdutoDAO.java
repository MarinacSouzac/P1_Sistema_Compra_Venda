/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DAO;

import Conexao.Conexao;
import java.sql.Connection;
import Beans.Produto;
import java.sql.CallableStatement;
import javax.swing.JOptionPane;
import java.sql.SQLException;



/**
 * Classe criada para .....
 * @author Marina Cruz de Souza
 * @since Classe criada em 06/10/2025
 */
public class ProdutoDAO {
    private Conexao conexao;
    private Connection conn;

    public ProdutoDAO() {
        this.conexao = new Conexao();
        this.conn = this.conexao.getConexao();
    }
    
    public void inserir (Produto produto){
        
        String sql ="{CALL inserirProduto(?,?,?,?,?)}";
        try(Connection conn=new Conexao().getConexao();
            CallableStatement stmt=conn.prepareCall(sql)){
         
            stmt.setString(1,produto.getNome());
            stmt.setString(2,produto.getDescricao());
            stmt.setDouble(3,produto.getPrecoVenda());
            stmt.setInt(4,produto.getQtdEstoque());
            stmt.setInt(5,produto.getFornecedor().getId());
            
            stmt.execute();
            JOptionPane.showMessageDialog(null,"Produto inserido com sucesso!");
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null,"Erro ao inserir produto: "+
                    e.getMessage());
        }
    }
    
    
}//fim da classe
