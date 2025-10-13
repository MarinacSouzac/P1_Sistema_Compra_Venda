package DAO;

import Beans.Cliente;
import Beans.Fornecedor;
import Beans.NotaFiscal;
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
    
    // Inserir nota fiscal
    public int inserirNotaFiscal(NotaFiscal nota) {
        String sql = "INSERT INTO notaFiscal (ntf_data_venda, ntf_tipo, ntf_quant_vend, cli_id, fnc_id, ntf_valor_total) "
                   + "VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement stmt = this.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            // 1. Data
            stmt.setDate(1, java.sql.Date.valueOf(nota.getDataVenda().toLocalDate()));
            // 2. Tipo: true=saída(1), false=entrada(0)
            stmt.setInt(2, nota.isTipo() ? 1 : 0);
            // 3. Quantidade total
            stmt.setInt(3, nota.getQtdTotal());

            // 4 e 5: cliente ou fornecedor
            if (nota.isTipo()) { // saída
                if (nota.getCliente() == null) {
                    throw new IllegalArgumentException("Cliente não pode ser nulo para nota de saída!");
                }
                stmt.setInt(4, nota.getCliente().getId());
                stmt.setNull(5, java.sql.Types.INTEGER);
            } else { // entrada
                if (nota.getFornecedor() == null) {
                    throw new IllegalArgumentException("Fornecedor não pode ser nulo para nota de entrada!");
                }
                stmt.setNull(4, java.sql.Types.INTEGER);
                stmt.setInt(5, nota.getFornecedor().getId());
            }

            // 6. Valor total
            stmt.setDouble(6, nota.getValorTotal());

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

    // Próximo ID (opcional)
    public int getNextId() {
        int nextId = 1;
        String sql = "SELECT COALESCE(MAX(ntf_id), 0) + 1 AS nextId FROM notaFiscal";
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

    // Listar notas fiscais
    public List<NotaFiscal> getNotaFiscal() {
        List<NotaFiscal> listaNotaFiscal = new ArrayList<>();
        String sql = "SELECT n.*, c.cli_id, c.cli_nome, f.fnc_id, f.fnc_nome "
                   + "FROM notaFiscal n "
                   + "LEFT JOIN cliente c ON n.cli_id = c.cli_id "
                   + "LEFT JOIN fornecedor f ON n.fnc_id = f.fnc_id";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql,
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                NotaFiscal nota = new NotaFiscal();
                nota.setId(rs.getInt("ntf_id"));
                nota.setDataVenda(rs.getTimestamp("ntf_data_venda").toLocalDateTime());
                nota.setTipo(rs.getInt("ntf_tipo") == 1);
                nota.setValorTotal(rs.getDouble("ntf_valor_total"));
                nota.setQtdTotal(rs.getInt("ntf_quant_vend"));

                // Cliente
                int cliId = rs.getInt("cli_id");
                if (!rs.wasNull()) {
                    Cliente c = new Cliente();
                    c.setId(cliId);
                    c.setNome(rs.getString("cli_nome"));
                    nota.setCliente(c);
                } else {
                    nota.setCliente(null);
                }

                // Fornecedor
                int fncId = rs.getInt("fnc_id");
                if (!rs.wasNull()) {
                    Fornecedor f = new Fornecedor();
                    f.setId(fncId);
                    f.setNome(rs.getString("fnc_nome"));
                    nota.setFornecedor(f);
                } else {
                    nota.setFornecedor(null);
                }

                listaNotaFiscal.add(nota);
            }

        } catch (SQLException ex) {
            System.out.println("Erro ao consultar notas fiscais: " + ex.getMessage());
        }

        return listaNotaFiscal;
    }
}
