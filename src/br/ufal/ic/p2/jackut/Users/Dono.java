package br.ufal.ic.p2.jackut.Users;

import br.ufal.ic.p2.jackut.Users.User;

public class Dono extends User {
    public String cpf;
    public Dono(String nome, String email, String senha, String cpf, String endereco) {
        super(nome, email, senha, endereco);
        this.cpf = cpf;
    }

    @Override
    public String isWhatType() {
        return "Dono";
    }
}
