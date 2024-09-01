package br.ufal.ic.p2.jackut.Exceptions.Orders;

public class OrderNotFound extends Exception {
    public OrderNotFound() {
        super("Pedido nao encontrado");
    }
    public OrderNotFound(String message) {
        super(message);
    }
}
