package br.ufal.ic.p2.jackut.Enterprises;

public class Farmacia extends Enterprise {
    public Boolean aberto24horas;
    public Integer numeroFuncionarios;

    public Farmacia(int idDono, String nome, String endereco, Boolean aberto24horas, Integer numeroFuncionarios) {
        super(idDono, nome, endereco);
        this.aberto24horas = aberto24horas;
        this.numeroFuncionarios = numeroFuncionarios;
    }

    @Override
    public String isWhatType() { return "Farmacia"; }
}
