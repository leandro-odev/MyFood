package br.ufal.ic.p2.jackut.Exceptions;

public class PedidoNotFound extends Exception {
    public PedidoNotFound() {
        super("Não foi possivel encontrar pedido");
    }

    public PedidoNotFound(String message) {
        super(message);
    }
}
