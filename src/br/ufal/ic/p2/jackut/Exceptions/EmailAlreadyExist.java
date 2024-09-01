package br.ufal.ic.p2.jackut.Exceptions;

public class EmailAlreadyExist extends Exception {
    public EmailAlreadyExist() {
        super("Conta com esse email ja existe");
    }

    public EmailAlreadyExist (String message) {
        super(message);
    }
}
