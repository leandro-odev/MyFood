package br.ufal.ic.p2.jackut.Exceptions.Products;

public class ProductNotFound extends Exception {
    public ProductNotFound() {
        super("Produto nao encontrado");
    }

    public ProductNotFound(String message) {
        super(message);
    }
}
