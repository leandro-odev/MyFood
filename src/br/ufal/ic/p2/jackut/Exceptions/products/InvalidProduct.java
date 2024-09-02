package br.ufal.ic.p2.jackut.Exceptions.Products;

public class InvalidProduct extends Exception {
    public InvalidProduct() {
        super("Produto invalido");
    }
    public InvalidProduct(String message) {
        super(message);
    }
}
