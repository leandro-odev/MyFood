package br.ufal.ic.p2.jackut.Exceptions.products;

public class WrongCategory extends Exception {
    public WrongCategory() {
        super("Categoria invalido");
    }

    public WrongCategory(String message) {
        super(message);
    }
}
