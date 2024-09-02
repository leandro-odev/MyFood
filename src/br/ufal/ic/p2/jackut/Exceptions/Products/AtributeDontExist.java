package br.ufal.ic.p2.jackut.Exceptions.Products;

public class AtributeDontExist extends Exception {
    public AtributeDontExist() {
        super("Atributo nao existe");
    }

    public AtributeDontExist(String message) {
        super(message);
    }
}
