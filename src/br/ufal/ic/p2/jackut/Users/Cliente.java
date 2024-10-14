package br.ufal.ic.p2.jackut.Users;

import br.ufal.ic.p2.jackut.Users.User;

public class Cliente extends User {
    public Cliente(String nome, String email, String senha, String endereco) {
        super(nome, email, senha, endereco);
    }

    @Override
    public String isWhatType() {
        return "Cliente";
    }
}
