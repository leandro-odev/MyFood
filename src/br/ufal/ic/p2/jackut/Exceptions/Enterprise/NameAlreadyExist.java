package br.ufal.ic.p2.jackut.Exceptions.Enterprise;

public class NameAlreadyExist extends Exception {
    public NameAlreadyExist() {
        super("Empresa com esse nome ja existe");
    }
    public NameAlreadyExist(String message) {
        super(message);
    }
}