package br.ufal.ic.p2.jackut.Exceptions;

public class BusyDelivery extends Exception {
    public BusyDelivery() {
        super("Entregador ocupado");
    }

    public BusyDelivery(String message) {
        super(message);
    }
}
