package br.ufal.ic.p2.jackut.Exceptions.products;

public class ProductNameAtEnterprise extends Exception {
    public ProductNameAtEnterprise() {
        super("Ja existe um produto com esse nome para essa empresa");
    }

    public ProductNameAtEnterprise(String message) {
        super(message);
    }
}
