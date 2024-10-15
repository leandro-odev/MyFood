package br.ufal.ic.p2.jackut.Exceptions;

public class EntregaNotFound extends Exception {
    public EntregaNotFound() {
        super("Não foi possivel encontrar entrega");
    }

    public EntregaNotFound(String message) {
        super(message);
    }
}
