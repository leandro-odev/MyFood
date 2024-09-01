package br.ufal.ic.p2.jackut;

public class Cliente extends User {
    public Cliente(String nome, String email, String senha, String endereco) {
        super(nome, email, senha, endereco);
    }

    @Override
    public boolean isDono() {
        return false;
    }
}
