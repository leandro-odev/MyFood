package br.ufal.ic.p2.jackut;

import java.util.ArrayList;
import java.util.List;

public class Mercado extends Enterprise {
    private static int contador = 0;

    public String abre; // Hora em HH:MM.
    public String fecha; // Hora em HH:MM.
    public String tipoMercado; // supermercado, minimercado ou atacadista.

    public Mercado(int idDono, String nome, String endereco, String abre, String fecha, String tipoMercado) {
        super(idDono, nome, endereco);
        this.id = contador++;
        this.abre = abre;
        this.fecha = fecha;
        this.tipoMercado = tipoMercado;
    }

    @Override
    public String isWhatType() {
        return "Mercado";
    }
}
