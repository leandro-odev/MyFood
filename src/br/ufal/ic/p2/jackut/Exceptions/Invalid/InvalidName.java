package br.ufal.ic.p2.jackut.Exceptions.Invalid;

public class InvalidName extends Exception {
    public InvalidName() {
        super("Nome invalido");
    }

    public InvalidName(String message) {
        super(message);
    }
}
