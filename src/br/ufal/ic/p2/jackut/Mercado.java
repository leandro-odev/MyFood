package br.ufal.ic.p2.jackut;

public class Mercado {

    private static int contador = 0;
    public int id;
    public int dono;
    public String nome;
    public String endereco;
    public String abre; // Hora em HH:MM.
    public String fecha; // Hora em HH:MM.
    public String tipoMercado; // supermercado, minimercado ou atacadista.

    public Mercado(int dono, String nome, String endereco, String abre, String fecha, String tipoMercado) {
        this.id = contador++;
        this.dono = dono;
        this.nome = nome;
        this.endereco = endereco;
        this.abre = abre;
        this.fecha = fecha;
        this.tipoMercado = tipoMercado;
    }

}
