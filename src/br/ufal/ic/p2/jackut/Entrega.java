package br.ufal.ic.p2.jackut;

import java.util.ArrayList;

public class Entrega {
    private static int contador = 0;
    public int id;
    public String cliente;
    public String empresa;
    public Integer pedido;
    public Integer entregador;
    public String destino;
    public ArrayList<String> produtos;

    public Entrega(String cliente, String empresa, Integer pedido, Integer entregador, String destino) {
        this.id = contador++;
        this.cliente = cliente;
        this.empresa = empresa;
        this.pedido = pedido;
        this.entregador = entregador;
        this.destino = destino;
        this.produtos = new ArrayList<>();
    }
}
