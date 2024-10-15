package br.ufal.ic.p2.jackut.Exceptions;

public class RequestAlreadyDone extends Exception {
    public RequestAlreadyDone() {
        super("Pedido ja liberado");
    }

    public RequestAlreadyDone(String message) {
        super(message);
    }
}
