package br.ufal.ic.p2.jackut.Exceptions;

public class InvalidDelivery extends Exception {
    public InvalidDelivery() { super("Nao e um entregador valido"); }
}
