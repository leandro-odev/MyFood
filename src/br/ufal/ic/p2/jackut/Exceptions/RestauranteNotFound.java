package br.ufal.ic.p2.jackut.Exceptions;

public class RestauranteNotFound extends Exception {
    public RestauranteNotFound() {
        super("Não foi possivel encontrar restaurante");
    }

    public RestauranteNotFound(String message) {
        super(message);
    }
}
