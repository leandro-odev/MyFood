package br.ufal.ic.p2.jackut.Enterprises;

public class Restaurante extends Enterprise {
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
