package br.ufal.ic.p2.jackut;

public class Dono extends User {
    public String cpf;
    public Dono(String nome, String email, String senha, String cpf, String endereco) {
        super(nome, email, senha, endereco);
        this.cpf = cpf;
    }

    @Override
    public boolean isDono() {
        return true;
    }
}
