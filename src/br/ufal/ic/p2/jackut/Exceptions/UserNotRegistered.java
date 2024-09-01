package br.ufal.ic.p2.jackut.Exceptions;

public class UserNotRegistered extends Exception {
    public UserNotRegistered() {
        super("Usuario nao cadastrado.");
    }

    public UserNotRegistered(String message) {
        super(message);
    }
}
