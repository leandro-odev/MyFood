package br.ufal.ic.p2.jackut.Exceptions.Enterprise;

public class NameAndAddresAlreadyExist extends Exception {
    public NameAndAddresAlreadyExist() {
        super("Proibido cadastrar duas empresas com o mesmo nome e local");
    }
    public NameAndAddresAlreadyExist(String message) {
        super(message);
    }
}
