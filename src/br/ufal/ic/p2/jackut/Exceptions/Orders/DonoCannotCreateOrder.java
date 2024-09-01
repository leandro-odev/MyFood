package br.ufal.ic.p2.jackut.Exceptions.Orders;

public class DonoCannotCreateOrder extends Exception {
    public DonoCannotCreateOrder() {
        super("Dono de empresa nao pode fazer um pedido");
    }
    public DonoCannotCreateOrder(String message) {
        super(message);
    }
}
