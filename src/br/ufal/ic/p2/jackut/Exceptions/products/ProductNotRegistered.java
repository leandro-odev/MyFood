package br.ufal.ic.p2.jackut.Exceptions.Products;

public class ProductNotRegistered extends Exception {
    public ProductNotRegistered() {
        super("Produto nao cadastrado");
    }

    public ProductNotRegistered(String message) {
        super(message);
    }
}
