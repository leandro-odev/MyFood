package br.ufal.ic.p2.jackut.Exceptions.Enterprise;

public class InvalidAttribute extends Exception {
    public InvalidAttribute() {
        super("Atributo invalido");
    }
    public InvalidAttribute(String message) {
        super(message);
    }
}
