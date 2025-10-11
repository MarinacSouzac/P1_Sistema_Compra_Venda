package DAO;

import Conexao.Conexao;
import Beans.Cliente;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    private Conexao conexao;
    private Connection conn;

    public ClienteDAO() {
        this.conexao = new Conexao();
        this.conn = this.conexao.getConexao();
    }

    public int inserirCliente(Cliente cliente) {
        String sql = "INSERT INTO cliente (cli_nome, cli_tipo, "
                + "cli_email, cli_telefone, cli_rua, cli_numero, cli_bairro, "
                + "cli_cidade, cli_estado, cli_CEP, cli_pais, cli_cpf, cli_cnpj) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement stmt = this.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, cliente.getNome());
            stmt.setInt(2, cliente.isTipo() ? 1 : 0);
            stmt.setString(3, cliente.getEmail());
            stmt.setString(4, cliente.getTelefone());
            stmt.setString(5, cliente.getRua());
            stmt.setString(6, cliente.getNumero());
            stmt.setString(7, cliente.getBairro());
            stmt.setString(8, cliente.getCidade());
            stmt.setString(9, cliente.getEstado());
            stmt.setString(10, cliente.getCep());
            stmt.setString(11, cliente.getPais());
            stmt.setString(12, cliente.getCpf());
            stmt.setString(13, cliente.getCnpj());

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas == 0) {
                throw new SQLException("Falha ao inserir cliente, nenhuma linha afetada.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Falha ao obter o ID do cliente inserido.");
                }
            }

        } catch (SQLException ex) {
            System.out.println("Erro ao inserir cliente: " + ex.getMessage());
            return -1;
        }
    }

    public int getNextId() {
        int nextId = 1;
        String sql = "SELECT COALESCE(MAX(cli_id), 0) + 1 AS nextId FROM cliente";

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

    public List<Cliente> getCliente() {
        List<Cliente> listaClientes = new ArrayList<>();
        String sql = "SELECT * FROM cliente";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql,
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setId(rs.getInt("cli_id"));
                cliente.setNome(rs.getString("cli_nome"));
                cliente.setTipo(rs.getInt("cli_tipo") == 1);
                cliente.setCpf(rs.getString("cli_cpf"));
                cliente.setCnpj(rs.getString("cli_cnpj"));
                cliente.setEmail(rs.getString("cli_email"));
                cliente.setTelefone(rs.getString("cli_telefone"));
                cliente.setRua(rs.getString("cli_rua"));
                cliente.setNumero(rs.getString("cli_numero"));
                cliente.setBairro(rs.getString("cli_bairro"));
                cliente.setCidade(rs.getString("cli_cidade"));
                cliente.setEstado(rs.getString("cli_estado"));
                cliente.setCep(rs.getString("cli_CEP"));
                cliente.setPais(rs.getString("cli_pais"));

                listaClientes.add(cliente);
            }

        } catch (SQLException ex) {
            System.out.println("Erro ao consultar todas as pessoas: " + ex.getMessage());
        }

        return listaClientes;
    }
}

