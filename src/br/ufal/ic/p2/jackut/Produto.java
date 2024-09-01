package br.ufal.ic.p2.jackut;

public class Produto {
    private static int contador = 0;
    public int numero;
    public String nome;
    public float valor;
    public String categoria;

    public Produto(String nome, float valor, String categoria) {
        this.numero = contador++;
        this.nome = nome;
        this.valor = valor;
        this.categoria = categoria;
    }
}
