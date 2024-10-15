package br.ufal.ic.p2.jackut.Exceptions;

public class PedidoNotReady extends Exception {
    public PedidoNotReady() {
        super("Pedido nao esta pronto para entrega");
    }

    public PedidoNotReady(String message) {
        super(message);
    }
}