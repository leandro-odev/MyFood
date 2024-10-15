package br.ufal.ic.p2.jackut.Exceptions;

public class NoEnterprises extends Exception {
    public NoEnterprises() {
        super("Entregador nao estar em nenhuma empresa.");
    }

    public NoEnterprises(String message) {
        super(message);
    }
}
