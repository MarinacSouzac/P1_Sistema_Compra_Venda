package DAO;

import Beans.NotaFiscal;
import Beans.Cliente;
import Beans.Fornecedor;
import Beans.ItemNotaFiscal;
import Conexao.Conexao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NotaFiscalDAO {

    private Conexao conexao;
    private Connection conn;

    public NotaFiscalDAO() {
        this.conexao = new Conexao();
        this.conn = this.conexao.getConexao();
    }

    public int inserirNotaFiscal(NotaFiscal nota) {
        String sql = "INSERT INTO notaFiscal (ntf_data_venda, ntf_tipo, ntf_quant_vend, ntf_valor_total, cli_id, fnc_id) "
                   + "VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setDate(1, Date.valueOf(nota.getDataVenda()));
            stmt.setInt(2, nota.isTipo() ? 1 : 0);
            stmt.setInt(3, nota.getQtdTotal());
            stmt.setDouble(4, nota.getValorTotal());

            if (nota.isTipo()) {
                stmt.setInt(5, nota.getCliente() != null ? nota.getCliente().getId() : Types.NULL);
                stmt.setNull(6, Types.INTEGER);
            } else {
                stmt.setNull(5, Types.INTEGER);
                stmt.setInt(6, nota.getFornecedor() != null ? nota.getFornecedor().getId() : Types.NULL);
            }


            int linhas = stmt.executeUpdate();
            if (linhas == 0) throw new SQLException("Falha ao inserir nota fiscal.");

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
                else throw new SQLException("Falha ao obter ID da nota fiscal.");
            }

        } catch (SQLException ex) {
            System.out.println("Erro ao inserir nota fiscal: " + ex.getMessage());
            return -1;
        }
    }
    
     public int getNextId() {
        int nextId = 1;
        String sql = "SELECT COALESCE(MAX(ntf_id), 0) + "
                + "1 AS nextId FROM notaFiscal";

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
    public boolean cancelarNotaFiscal(int idNota) {
        String sql = "UPDATE notaFiscal SET ntf_status = 0 WHERE ntf_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idNota);
            return stmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.out.println("Erro ao cancelar nota: " + ex.getMessage());
            return false;
        }
    }

    public List<NotaFiscal> getNotas() {
        List<NotaFiscal> lista = new ArrayList<>();
        String sql = "SELECT n.*, c.cli_nome, f.fnc_nome FROM notaFiscal n "
                   + "LEFT JOIN cliente c ON n.cli_id = c.cli_id "
                   + "LEFT JOIN fornecedor f ON n.fnc_id = f.fnc_id";

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                NotaFiscal n = new NotaFiscal();
                n.setId(rs.getInt("ntf_id"));
                n.setDataVenda(rs.getDate("ntf_data_venda").toLocalDate());
                n.setTipo(rs.getInt("ntf_tipo") == 1);
                n.setQtdTotal(rs.getInt("ntf_quant_vend"));
                n.setValorTotal(rs.getDouble("ntf_valor_total"));


                Cliente c = new Cliente();
                c.setId(rs.getInt("cli_id"));
                c.setNome(rs.getString("cli_nome"));
                n.setCliente(c);

                Fornecedor f = new Fornecedor();
                f.setId(rs.getInt("fnc_id"));
                f.setNome(rs.getString("fnc_nome"));
                n.setFornecedor(f);

                lista.add(n);
            }

        } catch (SQLException ex) {
            System.out.println("Erro ao consultar notas fiscais: " + ex.getMessage());
        }

        return lista;
    }
    
    public List<NotaFiscal> buscarPorTipo(int tipo) {
    List<NotaFiscal> lista = new ArrayList<>();
    String sql = "SELECT n.*, c.cli_nome, f.fnc_nome FROM notaFiscal n "
               + "LEFT JOIN cliente c ON n.cli_id = c.cli_id "
               + "LEFT JOIN fornecedor f ON n.fnc_id = f.fnc_id "
               + "WHERE n.ntf_tipo = ?"; // 

    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, tipo);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            NotaFiscal n = new NotaFiscal();
            n.setId(rs.getInt("ntf_id"));
            n.setDataVenda(rs.getDate("ntf_data_venda").toLocalDate());
            n.setTipo(rs.getInt("ntf_tipo") == 1);
            n.setQtdTotal(rs.getInt("ntf_quant_vend"));
            n.setValorTotal(rs.getDouble("ntf_valor_total"));
    

            Cliente c = new Cliente();
            c.setId(rs.getInt("cli_id"));
            c.setNome(rs.getString("cli_nome"));
            n.setCliente(c);

            Fornecedor f = new Fornecedor();
            f.setId(rs.getInt("fnc_id"));
            f.setNome(rs.getString("fnc_nome"));
            n.setFornecedor(f);

            lista.add(n);
        }

    } catch (SQLException ex) {
        System.out.println("Erro ao buscar notas fiscais por tipo: " + ex.getMessage());
    }

    return lista;
}
public NotaFiscal buscarPorId(int id) {
    String sql = "SELECT n.*, c.cli_nome, f.fnc_nome FROM notaFiscal n " +
                 "LEFT JOIN cliente c ON n.cli_id = c.cli_id " +
                 "LEFT JOIN fornecedor f ON n.fnc_id = f.fnc_id " +
                 "WHERE n.ntf_id = ?";

    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            NotaFiscal nf = new NotaFiscal();
            nf.setId(rs.getInt("ntf_id"));
            nf.setDataVenda(rs.getDate("ntf_data_venda").toLocalDate());
            nf.setTipo(rs.getInt("ntf_tipo") == 1);
            nf.setQtdTotal(rs.getInt("ntf_quant_vend"));
            nf.setValorTotal(rs.getDouble("ntf_valor_total"));

            Cliente c = new Cliente();
            c.setId(rs.getInt("cli_id"));
            c.setNome(rs.getString("cli_nome"));
            nf.setCliente(c);

            Fornecedor f = new Fornecedor();
            f.setId(rs.getInt("fnc_id"));
            f.setNome(rs.getString("fnc_nome"));
            nf.setFornecedor(f);

            return nf;
        }

    } catch (SQLException ex) {
        System.out.println("Erro ao buscar nota por ID: " + ex.getMessage());
    }

    return null;
}

}