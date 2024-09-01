package br.ufal.ic.p2.jackut.Exceptions.Enterprise;

public class UserCantCreate extends Exception {
    public UserCantCreate() {
        super("Usuario nao pode criar uma empresa");
    }
    public UserCantCreate(String message) {
        super(message);
    }
}
