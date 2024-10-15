package br.ufal.ic.p2.jackut.Exceptions;

public class EntregaIdNotFound extends Exception {
    public EntregaIdNotFound() {
        super("Nao existe entrega com esse id");
    }

    public EntregaIdNotFound(String message) {
        super(message);
    }
}
