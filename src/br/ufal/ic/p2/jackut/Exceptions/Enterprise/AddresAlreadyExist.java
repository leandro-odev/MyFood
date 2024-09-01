package br.ufal.ic.p2.jackut.Exceptions.Enterprise;

public class AddresAlreadyExist extends Exception {
    public AddresAlreadyExist() {
        super("Empresa com esse endereco ja existe");
    }
    public AddresAlreadyExist(String message) {
        super(message);
    }
}
