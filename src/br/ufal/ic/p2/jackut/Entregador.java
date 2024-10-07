package br.ufal.ic.p2.jackut;

// nome=<String> email=<String> senha=<String> endereco=<String> veiculo=<String> endereco=<placa>
public class Entregador {
    private static int contador = 0;
    public int id;
    public String nome;
    public String email;
    public String senha;
    public String endereco;
    public String veiculo;
    public String placa;

    public Entregador(String nome, String email, String senha, String endereco, String veiculo, String placa) {
        this.id = contador++;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.endereco = endereco;
        this.veiculo = veiculo;
        this.placa = placa;
    }
}
