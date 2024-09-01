package br.ufal.ic.p2.jackut.Exceptions.Orders;

public class NoOpenedOrder extends Exception {
    public NoOpenedOrder() {
        super("Nao existe pedido em aberto");
    }
    public NoOpenedOrder(String message) {
        super(message);
    }
}
