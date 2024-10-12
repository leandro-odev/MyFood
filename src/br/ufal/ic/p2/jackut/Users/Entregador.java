package br.ufal.ic.p2.jackut.Users;

import br.ufal.ic.p2.jackut.Users.User;

import java.util.ArrayList;

// nome=<String> email=<String> senha=<String> endereco=<String> veiculo=<String> endereco=<placa>
public class Entregador extends User {
    public String veiculo;
    public String placa;
    public int[] empresas;
    public Boolean ocupado;

    public Entregador(String nome, String email, String senha, String endereco, String veiculo, String placa) {
        super(nome, email, senha, endereco);
        this.veiculo = veiculo;
        this.placa = placa;
        this.empresas = new ArrayList<Integer>();
        this.ocupado = false;
    }

    @Override
    public String isWhatType() {
        return "Entregador";
    }

    @Override
    public void alterarCampoEspecifico(String campo) {

    };
}
