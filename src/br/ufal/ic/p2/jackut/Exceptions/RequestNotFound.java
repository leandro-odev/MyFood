package br.ufal.ic.p2.jackut.Exceptions;

public class RequestNotFound extends Exception {
    public RequestNotFound() {
        super("Pedido nao encontrado");
    }
}
