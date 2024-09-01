package br.ufal.ic.p2.jackut;

public class User {
    private static int contador = 0;
    public int id;
    public String nome;
    public String email;
    public String senha;
    public String endereco;

    public User(String nome, String email, String senha, String endereco) {
        this.id = contador++;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.endereco = endereco;
    }

    public boolean isDono() {
        return false;
    }
}


