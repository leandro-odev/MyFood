package br.ufal.ic.p2.jackut.Exceptions;

public class BusyDelivery extends Exception {
    public BusyDelivery() {
        super("Entregador ainda em entrega");
    }

    public BusyDelivery(String message) {
        super(message);
    }
}
