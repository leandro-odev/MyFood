package br.ufal.ic.p2.jackut;

import java.util.ArrayList;

public class Pedido {
    private static int contador = 0;
    public int numero;
    public int cliente;
    public int empresa;
    public String estado;
    public ArrayList<Produto> produtos;

    public float valor() {
        float total = 0;
        for (Produto produto : produtos) {
            total += produto.valor;
        }
        return total;
    }
    
    public Pedido(int cliente, int empresa) {
        this.numero = ++contador;
        this.cliente = cliente;
        this.empresa = empresa;
        this.estado = "aberto";
        this.produtos = new ArrayList<>();
    }
}
