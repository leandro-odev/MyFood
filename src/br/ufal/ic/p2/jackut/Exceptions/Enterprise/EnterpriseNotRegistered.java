package br.ufal.ic.p2.jackut.Exceptions.Enterprise;

public class EnterpriseNotRegistered extends Exception {
    public EnterpriseNotRegistered() {
        super("Empresa nao cadastrada");
    }
    public EnterpriseNotRegistered(String message) {
        super(message);
    }
}
