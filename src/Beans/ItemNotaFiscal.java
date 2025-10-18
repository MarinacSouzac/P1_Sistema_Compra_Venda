
package Beans;

/**
 * 
 * Classe Criada para Criar Objeto Item da Nota Fiscak
 * @author Marina Souza
 */
public class ItemNotaFiscal {
    
    private int id;
    private NotaFiscal notaFiscal;
    private Produto produto;
    private int quantidade;
    private double precoUnidade;
    private double subtotal;
    
    //Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

   public NotaFiscal getNtf() {
    return notaFiscal;
}

public void setNtf(NotaFiscal notaFiscal) {
    this.notaFiscal = notaFiscal;
}

   public Produto getPrd() {  // estava ok
   return produto;
    }

    public void setPrd(Produto produto) {  // aqui tinha idPrd e atribuía produto errado
        this.produto = produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getPrecoUnidade() {
        return precoUnidade;
    }

    public void setPrecoUnidade(double precoUnidade) {
        this.precoUnidade = precoUnidade;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }
    
    
    
}
