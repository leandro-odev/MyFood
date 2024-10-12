package br.ufal.ic.p2.jackut.Exceptions;

public class PlacaAlreadyExist extends Exception {
    public PlacaAlreadyExist() {
        super("Entregador com essa placa ja existe");
    }

    public PlacaAlreadyExist (String message) {
        super(message);
    }
}