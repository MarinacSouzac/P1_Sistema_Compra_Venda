/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Beans;

/**
 *
 * @author Marina Souza
 */
public class ItemNotaFiscal {
    
    private int id;
    private NotaFiscal idNtf;
    private Produto idPrd;
    private int quantidade;
    private double precoUnidade;
    private double subtotal;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public NotaFiscal getIdNtf() {
        return idNtf;
    }

    public void setIdNtf(NotaFiscal idNtf) {
        this.idNtf = idNtf;
    }

    public Produto getIdPrd() {
        return idPrd;
    }

    public void setIdPrd(Produto idPrd) {
        this.idPrd = idPrd;
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
