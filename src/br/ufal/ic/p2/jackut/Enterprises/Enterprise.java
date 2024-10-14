package br.ufal.ic.p2.jackut.Enterprises;

import br.ufal.ic.p2.jackut.Produto;

import java.util.ArrayList;
import java.util.List;

public class Enterprise {
    private static int contador = 0;

    public int id;
    public int idDono;
    public String nome;
    public String endereco;
    public List<Produto> produtos;
    public List<Integer> entregadores;

    public Enterprise(int idDono, String nome, String endereco) {
        this.id = contador++;
        this.idDono = idDono;
        this.nome = nome;
        this.endereco = endereco;
        this.produtos = new ArrayList<Produto>();
        this.entregadores = new ArrayList<Integer>();
    }

    public String isWhatType() {
        return "Empresa";
    }
}
