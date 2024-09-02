package br.ufal.ic.p2.jackut.Exceptions.Products;

public class InvalidName extends Exception {
    public InvalidName() {
        super("Nome invalido");
    }

    public InvalidName(String message) {
        super(message);
    }
}
