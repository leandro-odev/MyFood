package br.ufal.ic.p2.jackut.Exceptions.Orders;

public class CannotRemoveProductOrderClosed extends Exception {
    public CannotRemoveProductOrderClosed() {
        super("Nao e possivel remover produtos de um pedido fechado");
    }
    public CannotRemoveProductOrderClosed(String message) {
        super(message);
    }
}
