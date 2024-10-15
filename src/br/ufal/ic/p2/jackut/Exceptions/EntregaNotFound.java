package br.ufal.ic.p2.jackut.Exceptions;

public class EntregaNotFound extends Exception {
    public EntregaNotFound() {
        super("Nao existe nada para ser entregue com esse id");
    }

    public EntregaNotFound(String message) {
        super(message);
    }
}
