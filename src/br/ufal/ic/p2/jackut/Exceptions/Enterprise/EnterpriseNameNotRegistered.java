package br.ufal.ic.p2.jackut.Exceptions.Enterprise;

public class EnterpriseNameNotRegistered extends Exception {
    public EnterpriseNameNotRegistered() {
        super("Nao existe empresa com esse nome");
    }
    public EnterpriseNameNotRegistered(String message) {
        super(message);
    }
}