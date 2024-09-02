package br.ufal.ic.p2.jackut.Exceptions.Products;

public class InvalidPrice extends Exception {
    public InvalidPrice() {
        super("Valor invalido");
    }

    public InvalidPrice(String message) {
        super(message);
    }
}
