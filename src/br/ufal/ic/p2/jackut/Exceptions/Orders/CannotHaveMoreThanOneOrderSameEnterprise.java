package br.ufal.ic.p2.jackut.Exceptions.Orders;

public class CannotHaveMoreThanOneOrderSameEnterprise extends Exception {
    public CannotHaveMoreThanOneOrderSameEnterprise() {
        super("Nao e permitido ter dois pedidos em aberto para a mesma empresa");
    }
    public CannotHaveMoreThanOneOrderSameEnterprise(String message) {
        super(message);
    }
}