package br.ufal.ic.p2.jackut;

import java.util.ArrayList;
import java.util.List;

public class Restaurante extends Enterprise {
    private static int contador = 0;

    public String tipoCozinha;

    public Restaurante(String nome, String endereco, String tipoCozinha, int idDono) {
        super(idDono, nome, endereco);
        this.tipoCozinha = tipoCozinha;
    }

    @Override
    public String isWhatType() {
        return "Restaurante";
    }
}
