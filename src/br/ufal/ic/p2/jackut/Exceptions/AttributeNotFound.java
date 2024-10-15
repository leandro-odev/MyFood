package br.ufal.ic.p2.jackut.Exceptions;

public class AttributeNotFound extends Exception {
    public AttributeNotFound() {
        super("Atributo nao existe");
    }

    public AttributeNotFound(String message) {
        super(message);
    }
}
