package br.ufal.ic.p2.jackut.Exceptions.products;

public class ProductNotFound extends Exception {
    public ProductNotFound() {
        super("Produto nao encontrado");
    }

    public ProductNotFound(String message) {
        super(message);
    }
}
