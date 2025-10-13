package DAO;

import Beans.ItemNotaFiscal;
import Beans.Produto;
import Beans.NotaFiscal;
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

    // Inserir item da nota fiscal
    public int inserirItemNotaFiscal(ItemNotaFiscal item) {
        // Garantir que o Produto e a Nota não sejam nulos antes de inserir
        if (item.getPrd() == null || item.getNtf() == null) {
            throw new IllegalArgumentException("Produto ou NotaFiscal não pode ser nulo ao inserir item.");
        }

        String sql = "INSERT INTO itemNotaFiscal (ntf_id, prod_id, inf_quantidade, inf_preco_unidade, inf_subtotal) "
                   + "VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = this.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, item.getNtf().getId());   // NotaFiscal associada
            stmt.setInt(2, item.getPrd().getId());   // Produto associado
            stmt.setInt(3, item.getQuantidade());
            stmt.setDouble(4, item.getPrecoUnidade());
            stmt.setDouble(5, item.getSubtotal());

            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas == 0) {
                throw new SQLException("Falha ao inserir item da nota fiscal, nenhuma linha afetada.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Falha ao obter o ID do item da nota fiscal inserido.");
                }
            }

        } catch (SQLException ex) {
            System.out.println("Erro ao inserir item da nota fiscal: " + ex.getMessage());
            return -1;
        }
    }

    // Listar itens por nota fiscal
    public List<ItemNotaFiscal> getItensPorNota(int idNota) {
        List<ItemNotaFiscal> listaItens = new ArrayList<>();
        String sql = "SELECT i.*, p.prod_nome, p.prod_preco_venda "
                   + "FROM itemNotaFiscal i "
                   + "INNER JOIN produto p ON i.prod_id = p.prod_id "
                   + "WHERE i.ntf_id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idNota);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ItemNotaFiscal item = new ItemNotaFiscal();
                Produto p = new Produto();

                p.setId(rs.getInt("prod_id"));
                p.setNome(rs.getString("prod_nome"));
                p.setPrecoVenda(rs.getDouble("prod_preco_venda"));

                item.setPrd(p);
                item.setQuantidade(rs.getInt("inf_quantidade"));
                item.setPrecoUnidade(rs.getDouble("inf_preco_unidade"));
                item.setSubtotal(rs.getDouble("inf_subtotal"));

                // Se desejar associar a NotaFiscal também:
                NotaFiscal nota = new NotaFiscal();
                nota.setId(idNota);
                item.setNtf(nota);

                listaItens.add(item);
            }

        } catch (SQLException ex) {
            System.out.println("Erro ao consultar itens da nota: " + ex.getMessage());
        }

        return listaItens;
    }
}
