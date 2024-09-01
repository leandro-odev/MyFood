package br.ufal.ic.p2.jackut.Exceptions.products;

public class ProductDoesntBelongEnterprise extends Exception {
    public ProductDoesntBelongEnterprise() {
        super("O produto nao pertence a essa empresa");
    }
    public ProductDoesntBelongEnterprise(String message) {
        super(message);
    }
}
