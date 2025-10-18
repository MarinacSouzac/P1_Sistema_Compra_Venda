package DAO;

import Beans.ItemNotaFiscal;
import Beans.NotaFiscal;
import Beans.Produto;
import Conexao.Conexao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemNotaFiscalDAO {

    private Conexao conexao;
    private Connection conn;

    public ItemNotaFiscalDAO() {
        this.conexao = new Conexao();
        this.conn = this.conexao.getConexao();
    }

    public int inserirItem(ItemNotaFiscal item) {
        String sql = "INSERT INTO itemNotaFiscal (ntf_id, prd_id, inf_quantidade, inf_preco_unidade, inf_subtotal) "
                   + "VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, item.getNtf().getId());
            stmt.setInt(2, item.getPrd().getId());
            stmt.setInt(3, item.getQuantidade());
            stmt.setDouble(4, item.getPrecoUnidade());
            stmt.setDouble(5, item.getSubtotal());

            int linhas = stmt.executeUpdate();
            if (linhas == 0) throw new SQLException("Falha ao inserir item.");

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
                else throw new SQLException("Falha ao obter ID do item.");
            }

        } catch (SQLException ex) {
            System.out.println("Erro ao inserir item: " + ex.getMessage());
            return -1;
        }
    }

    public List<ItemNotaFiscal> getItensPorNota(int idNota) {
        List<ItemNotaFiscal> lista = new ArrayList<>();
        String sql = "SELECT i.*, p.prd_nome, p.prd_preco_venda FROM itemNotaFiscal i "
                   + "LEFT JOIN produto p ON i.prd_id = p.prd_id WHERE i.ntf_id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idNota);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ItemNotaFiscal item = new ItemNotaFiscal();

                NotaFiscal n = new NotaFiscal();
                n.setId(rs.getInt("ntf_id"));
                item.setNtf(n);

                Produto p = new Produto();
                p.setId(rs.getInt("prd_id"));
                p.setNome(rs.getString("prd_nome"));
                p.setPrecoVenda(rs.getDouble("prd_preco_venda"));
                item.setPrd(p);

                item.setQuantidade(rs.getInt("inf_quantidade"));
                item.setPrecoUnidade(rs.getDouble("inf_preco_unidade"));
                item.setSubtotal(rs.getDouble("inf_subtotal"));

                lista.add(item);
            }

        } catch (SQLException ex) {
            System.out.println("Erro ao consultar itens: " + ex.getMessage());
        }

        return lista;
    }
}
