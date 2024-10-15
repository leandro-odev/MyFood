package br.ufal.ic.p2.jackut.Exceptions;

public class RequestNotPreparing extends Exception {
    public RequestNotPreparing() {
        super("Nao e possivel liberar um produto que nao esta sendo preparado");
    }

    public RequestNotPreparing(String message) {
        super(message);
    }
}
