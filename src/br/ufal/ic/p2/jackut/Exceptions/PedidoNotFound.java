package br.ufal.ic.p2.jackut.Exceptions;

public class PedidoNotFound extends Exception {
    public PedidoNotFound() {
        super("Nao existe pedido para entrega");
    }

    public PedidoNotFound(String message) {
        super(message);
    }
}
