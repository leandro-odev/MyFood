package br.ufal.ic.p2.jackut.Exceptions.Orders;

public class CannotAddProductOrderClosed extends Exception {
    public CannotAddProductOrderClosed() {
        super("Nao e possivel adcionar produtos a um pedido fechado");
    }
    public CannotAddProductOrderClosed(String message) {
        super(message);
    }
}
