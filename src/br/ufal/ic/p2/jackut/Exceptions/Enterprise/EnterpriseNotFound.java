package br.ufal.ic.p2.jackut.Exceptions.Enterprise;

public class EnterpriseNotFound extends Exception {
    public EnterpriseNotFound() {
        super("Empresa nao encontrada");
    }

    public EnterpriseNotFound(String message) {
        super(message);
    }
}
