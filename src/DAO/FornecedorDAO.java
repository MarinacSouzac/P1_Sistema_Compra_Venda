/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Conexao.Conexao;
import Beans.Fornecedor;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Marina Souza
 */
public class FornecedorDAO {
    private Conexao conexao;
    private Connection conn;

    public FornecedorDAO() {
        this.conexao = new Conexao();
        this.conn = this.conexao.getConexao();
    }
    
    
    public int inserirFornecedor(Fornecedor fornecedor) {
    String sql = "INSERT INTO fornecedor (fnc_nome, fnc_nome_fantasia, "
               + "fnc_cnpj, fnc_email, "
               + "fnc_telefone, fnc_rua, fnc_numero, fnc_bairro, fnc_cidade, "
               + "fnc_estado, fnc_CEP, fnc_pais) "
               + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    try {
        PreparedStatement stmt = this.conn.prepareStatement
        (sql, Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, fornecedor.getNome());
        stmt.setString(2, fornecedor.getNome_fantasia());
        stmt.setString(3, fornecedor.getCnpj());
        stmt.setString(4, fornecedor.getEmail());
        stmt.setString(5, fornecedor.getTelefone());
        stmt.setString(6, fornecedor.getRua());
        stmt.setString(7, fornecedor.getNumero());
        stmt.setString(8, fornecedor.getBairro());
        stmt.setString(9, fornecedor.getCidade());
        stmt.setString(10, fornecedor.getEstado());
        stmt.setString(11, fornecedor.getCep());
        stmt.setString(12, fornecedor.getPais());

        int linhasAfetadas = stmt.executeUpdate();

        if (linhasAfetadas == 0) {
            throw new SQLException("Falha ao inserir fornecedor, "
                    + "nenhuma linha afetada.");
        }

        try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            } else {
                throw new SQLException("Falha ao obter o "
                        + "ID do fornecedor inserido.");
            }
        }

    } catch (SQLException ex) {
        System.out.println("Erro ao inserir fornecedor: " + ex.getMessage());
        return -1;
    }
}
     public int getNextId() {
        int nextId = 1;
        String sql = "SELECT COALESCE(MAX(fnc_id), 0) + 1 AS nextId FROM"
                + " fornecedor";

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
    public List<Fornecedor> getFornecedor() {
            List<Fornecedor> listaFornecedor = new ArrayList<>();
            String sql = "SELECT * FROM fornecedor";

            try {
                PreparedStatement stmt = conn.prepareStatement(sql,
                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_UPDATABLE);

                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    Fornecedor fornecedor = new Fornecedor();
                    fornecedor.setId(rs.getInt("fnc_id"));
                    fornecedor.setNome(rs.getString("fnc_nome"));
                    fornecedor.setNome_fantasia(rs.getString(
                            "fnc_nome_fantasia"));
                    fornecedor.setCnpj(rs.getString("fnc_cnpj"));
                    fornecedor.setEmail(rs.getString("fnc_email"));
                    fornecedor.setTelefone(rs.getString("fnc_telefone"));
                    fornecedor.setRua(rs.getString("fnc_rua"));
                    fornecedor.setNumero(rs.getString("fnc_numero"));
                    fornecedor.setBairro(rs.getString("fnc_bairro"));
                    fornecedor.setCidade(rs.getString("fnc_cidade"));
                    fornecedor.setEstado(rs.getString("fnc_estado"));
                    fornecedor.setCep(rs.getString("fnc_CEP"));
                    fornecedor.setPais(rs.getString("fnc_pais"));

                    listaFornecedor.add(fornecedor);
                }

            } catch (SQLException ex) {
                System.out.println("Erro ao consultar fornecedores: " + 
                        ex.getMessage());
            }

        return listaFornecedor;
}

    public boolean editarFornecedor(Fornecedor fornecedor) {
            String sql = "UPDATE fornecedor SET "
                    + "fnc_nome = ?, "
                    + "fnc_nome_fantasia = ?, "
                    + "fnc_cnpj = ?, "
                    + "fnc_email = ?, "
                    + "fnc_telefone = ?, "
                    + "fnc_rua = ?, "
                    + "fnc_numero = ?, "
                    + "fnc_bairro = ?, "
                    + "fnc_cidade = ?, "
                    + "fnc_estado = ?, "
                    + "fnc_CEP = ?, "
                    + "fnc_pais = ? "
                    + "WHERE fnc_id = ?";

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, fornecedor.getNome());
                stmt.setString(2, fornecedor.getNome_fantasia());
                stmt.setString(3, fornecedor.getCnpj());
                stmt.setString(4, fornecedor.getEmail());
                stmt.setString(5, fornecedor.getTelefone());
                stmt.setString(6, fornecedor.getRua());
                stmt.setString(7, fornecedor.getNumero());
                stmt.setString(8, fornecedor.getBairro());
                stmt.setString(9, fornecedor.getCidade());
                stmt.setString(10, fornecedor.getEstado());
                stmt.setString(11, fornecedor.getCep());
                stmt.setString(12, fornecedor.getPais());
                stmt.setInt(13, fornecedor.getId());

                int linhasAfetadas = stmt.executeUpdate();
                return linhasAfetadas > 0;

            } catch (SQLException ex) {
                System.out.println("Erro ao editar fornecedor: " + 
                        ex.getMessage());
                return false;
            }
        }

    public void excluirFornecedor(Fornecedor fornecedor) {
            String sql = "DELETE FROM fornecedor WHERE fnc_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, fornecedor.getId());
                int linhasAfetadas = stmt.executeUpdate();
                if (linhasAfetadas > 0) {
                    System.out.println("Fornecedor excluído com sucesso!");
                } else {
                    System.out.println("Nenhum fornecedor encontrado com "
                            + "esse ID.");
                }
            } catch (SQLException ex) {
                System.out.println("Erro ao excluir fornecedor: " + 
                        ex.getMessage());
            }
        }
    
    public boolean temNotaFiscalVinculada(int idFornecedor) {
    String sql = "SELECT COUNT(*) FROM notaFiscal WHERE fnc_id = ?";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, idFornecedor);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return rs.getInt(1) > 0;
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
}

    }//fim da classe
