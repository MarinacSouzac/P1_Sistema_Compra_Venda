

package Beans;
import java.util.List;
import java.time.LocalDate;

/**
 * Classe criada para o objeto Nota Fiscal
 * @author Marina Souza
 */
public class NotaFiscal {
  
    
    private int id;
    private LocalDate dataVenda;
    private boolean tipo;
    private int qtdTotal;
    private double valorTotal;
    private Cliente cliente;
    private Fornecedor fornecedor;
    private List<ItemNotaFiscal> itens; 
    private boolean status = true; 

    public List<ItemNotaFiscal> getItens() {
        return itens;
    }

    public void setItens(List<ItemNotaFiscal> itens) {
        this.itens = itens;
    }
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDataVenda() {
        return dataVenda;
    }

   public void setDataVenda(LocalDate dataVenda) { 
    this.dataVenda = dataVenda;
}

    public boolean isTipo() {
        return tipo;
    }

    public void setTipo(boolean tipo) {
        this.tipo = tipo;
    }

  

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    public int getQtdTotal() {
        return qtdTotal;
    }

    public void setQtdTotal(int qtdTotal) {
        this.qtdTotal = qtdTotal;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }
    
    public boolean isStatus() {
        return status;
    }
    public void setStatus(boolean status) {
        this.status = status;
    }
    
    
}
