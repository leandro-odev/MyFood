package br.ufal.ic.p2.jackut;

import java.util.ArrayList;
import java.util.List;

public class Restaurante {
    private static int contador = 0;
    public int id;
    public String nome;
    public String endereco;
    public String tipoCozinha;
    public int idDono;
    List<Produto> produtos;

    public Restaurante(String nome, String endereco, String tipoCozinha, int idDono) {
        this.id = contador++;
        this.nome = nome;
        this.endereco = endereco;
        this.tipoCozinha = tipoCozinha;
        this.idDono = idDono;
        this.produtos = new ArrayList<Produto>();
    }
}
