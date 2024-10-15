package br.ufal.ic.p2.jackut.Exceptions;

public class PedidoAlreadySent extends Exception {
    public PedidoAlreadySent() {
        super("Pedido ja liberado");
    }

    public PedidoAlreadySent(String message) {
        super(message);
    }
}