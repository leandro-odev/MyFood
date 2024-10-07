package br.ufal.ic.p2.jackut;

// nome=<String> email=<String> senha=<String> endereco=<String> veiculo=<String> endereco=<placa>
public class Entregador extends Usuario {
    private String veiculo;
    private String placa;

    public Entregador(String nome, String email, String senha, String endereco, String veiculo, String placa) {
        super(nome, email, senha, endereco);
        this.veiculo = veiculo;
        this.placa = placa;
    }

    public String getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(String veiculo) {
        this.veiculo = veiculo;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

}
