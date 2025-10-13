/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Beans.Cliente;
import Beans.Fornecedor;
import Conexao.Conexao;
import Beans.NotaFiscal;
import java.sql.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Marina Souza
 */
public class NotaFiscalDAO {
    
    private Conexao conexao;
    private Connection conn;

    public NotaFiscalDAO() {
        this.conexao = new Conexao();
        this.conn = this.conexao.getConexao();
    }
    
    public int inserirNotaFiscal(NotaFiscal nota) {
    String sql = "INSERT INTO notaFiscal (ntf_data_venda, ntf_tipo, "
            + "ntf_quant_vend, cli_id, fnc_id, ntf_valor_total) "
            + "VALUES (?, ?, ?, ?, ?, ?)";

    try {
        PreparedStatement stmt = this.conn.prepareStatement(sql, 
                Statement.RETURN_GENERATED_KEYS);

        // Data e tipo
        stmt.setDate(1, java.sql.Date.valueOf(nota.getDataVenda().toLocalDate()));
        stmt.setInt(2, nota.isTipo() ? 1 : 0); // true = saída, false = entrada
        stmt.setInt(3, nota.getQtdTotal());
        stmt.setDouble(6, nota.getValorTotal());

        // Preenchimento exclusivo de cliente ou fornecedor
        if (nota.isTipo()) { // Saída: cliente obrigatório, fornecedor nulo
            if (nota.getCliente() == null) {
                throw new IllegalArgumentException("Cliente não pode ser nulo para nota de saída!");
            }
            stmt.setInt(4, nota.getCliente().getId());
            stmt.setNull(5, java.sql.Types.INTEGER);
        } else { // Entrada: fornecedor obrigatório, cliente nulo
            if (nota.getFornecedor() == null) {
                throw new IllegalArgumentException("Fornecedor não pode ser nulo para nota de entrada!");
            }
            stmt.setNull(4, java.sql.Types.INTEGER);
            stmt.setInt(5, nota.getFornecedor().getId());
        }

        // Executa inserção
        int linhasAfetadas = stmt.executeUpdate();

        if (linhasAfetadas == 0) {
            throw new SQLException("Falha ao inserir nota fiscal, nenhuma linha afetada.");
        }

        // Recupera ID gerado
        try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1); 
            } else {
                throw new SQLException("Falha ao obter o ID da nota fiscal inserida.");
            }
        }

    } catch (SQLException ex) {
        System.out.println("Erro ao inserir nota fiscal: " + ex.getMessage());
        return -1;
    }
}
    
    public int getNextId(){
        int nextId = 1;
        String sql = "SELECT COALESCE(MAX(ntf_id), 0) + 1 AS nextId FROM "
                + "notaFiscal";

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
    
    public List<NotaFiscal> getNotaFiscal() {
        List<NotaFiscal> listaNotaFiscal = new ArrayList<>();
        String sql = "SELECT * FROM notaFiscal INNER JOIN cliente"
                + "USING(cli_id) INNER JOIN fornecedor USING(fnc_id)";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql,
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                NotaFiscal nota = new NotaFiscal();
                nota.setId(rs.getInt("ntf_id"));
                nota.setDataVenda(rs.getTimestamp
                ("ntf_data_venda").toLocalDateTime());
                nota.setTipo(rs.getInt("ntf_tipo") == 1);
                nota.setValorTotal(rs.getDouble("ntf_valor_total"));
                nota.setQtdTotal(rs.getInt("ntf_quant_vend"));
                Cliente c= new Cliente();
                
                c.setId(rs.getInt("cli_id"));
                c.setNome(rs.getString("cli_nome")); 
                nota.setCliente(c);
                Fornecedor f= new Fornecedor();
                
                f.setId(rs.getInt("fnc_id"));
                f.setNome(rs.getString("fnc_nome")); 
                nota.setFornecedor(f);
               
                listaNotaFiscal.add(nota);

                
            }

        } catch (SQLException ex) {
            System.out.println("Erro ao consultar fornecedores: " + 
                    ex.getMessage());
        }

        return listaNotaFiscal;
}

    
    
    
    
    
    
}
