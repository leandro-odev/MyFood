package br.ufal.ic.p2.jackut;

import br.ufal.ic.p2.jackut.Exceptions.*;
import br.ufal.ic.p2.jackut.Exceptions.Enterprise.*;
import br.ufal.ic.p2.jackut.Exceptions.Invalid.InvalidAttribute;
import br.ufal.ic.p2.jackut.Exceptions.Invalid.InvalidName;
import br.ufal.ic.p2.jackut.Exceptions.Invalid.InvalidPrice;
import br.ufal.ic.p2.jackut.Exceptions.Invalid.InvalidProduct;
import br.ufal.ic.p2.jackut.Exceptions.Orders.*;
import br.ufal.ic.p2.jackut.Exceptions.Products.*;

import java.util.List;


public class Facade {


    private Sistema sistema = Sistema.getInstance();

    public void encerrarSistema() {
        sistema.encerrarSistema();
    }

    public void zerarSistema() {
        sistema.zerarSistema();
    }

    public User getUser(int id) throws UserNotRegistered {
        return sistema.getUser(id);
    }

    public Restaurante getRestaurante(int id) {
        return sistema.getRestaurante(id);
    }

    public Pedido getPedido(int id) {
        return sistema.getPedido(id);
    }

    public String getAtributoUsuario(int id, String atributo) throws UserNotRegistered {
        return sistema.getAtributoUsuario(id, atributo);
    }

    public void criarUsuario(String nome, String email, String senha, String endereco) throws EmailAlreadyExist, InvalidName {
        sistema.criarUsuario(nome, email, senha, endereco);
    }

    public void criarUsuario(String nome, String email, String senha, String endereco, String cpf) throws EmailAlreadyExist, InvalidName {
        sistema.criarUsuario(nome, email, senha, endereco, cpf);
    }

    // Other methods from Sistema class

    public int login(String email, String senha) {
        return sistema.login(email, senha);
    }

    // Restaurante
    public int criarEmpresa(String tipoEmpresa, int dono, String nome, String endereco, String tipoCozinha) throws NameAlreadyExist, AddresAlreadyExist, NameAndAddresAlreadyExist, UserCantCreate {
        return sistema.criarEmpresa(tipoEmpresa, dono, nome, endereco, tipoCozinha);
    }

    public String getEmpresasDoUsuario(int idDono) throws UserCantCreate {
        return sistema.getEmpresasDoUsuario(idDono);
    }

    public int getIdEmpresa(int idDono, String nome, int indice) throws EnterpriseNameNotRegistered, InvalidName {
        return sistema.getIdEmpresa(idDono, nome, indice);
    }

    public String getAtributoEmpresa(int empresa, String atributo) throws InvalidAttribute, EnterpriseNotRegistered {
        return sistema.getAtributoEmpresa(empresa, atributo);
    }

    public int criarProduto(int empresa, String nome, float valor, String categoria) throws ProductNameAtEnterprise, InvalidName, InvalidPrice, WrongCategory, EnterpriseNotRegistered {
        return sistema.criarProduto(empresa, nome, valor, categoria);
    }

    public void editarProduto(int produto, String nome, float valor, String categoria) throws InvalidName, InvalidPrice, WrongCategory, ProductNotRegistered {
        sistema.editarProduto(produto, nome, valor, categoria);
    }

    public String getProduto(String nome, int empresa, String atributo) throws ProductNotFound, AtributeDontExist {
        return sistema.getProduto(nome, empresa, atributo);
    }

    public String listarProdutos(int empresa) throws EnterpriseNotFound {
        return sistema.listarProdutos(empresa);
    }

    public int criarPedido(int cliente, int empresa) throws DonoCannotCreateOrder, CannotHaveMoreThanOneOrderSameEnterprise {
        return sistema.criarPedido(cliente, empresa);
    }

    public int getNumeroPedido(int cliente, int empresa, int indice) {
        return sistema.getNumeroPedido(cliente, empresa, indice);
    }

    public void adicionarProduto(int numero, int produto) throws NoOpenedOrder, ProductDoesntBelongEnterprise, CannotAddProductOrderClosed {
        sistema.adicionarProduto(numero, produto);
    }

    public String getPedidos(int numero, String atributo) throws InvalidAttribute, AtributeDontExist, OrderNotFound {
        return sistema.getPedidos(numero, atributo);
    }

    public void fecharPedido(int numero) throws OrderNotFound {
        sistema.fecharPedido(numero);
    }

    public void removerPedido(int numero) {
        sistema.removerPedido(numero);
    }

    public void removerProduto(int pedido, String produto) throws OrderNotFound, InvalidProduct, ProductNotFound, CannotRemoveProductOrderClosed {
        sistema.removerProduto(pedido, produto);
    }

    // Mercado
    public int criarEmpresa(String tipoEmpresa, int dono, String nome, String endereco, String abre, String fecha, String tipoMercado) throws NameAlreadyExist, AddresAlreadyExist, NameAndAddresAlreadyExist, UserCantCreate {
        return sistema.criarEmpresa(tipoEmpresa, dono, nome, endereco, abre, fecha, tipoMercado);
    }

    public void alterarFuncionamento(int mercado, String abre, String fecha) throws EnterpriseNotRegistered {
        sistema.alterarFuncionamento(mercado, abre, fecha);
    }

    // Farmácia

//    public int criarEmpresa(String tipoEmpresa, int dono, String nome, boolean aberto24Horas, int numeroFuncionarios) throws NameAlreadyExist, UserCantCreate {
//        return sistema.criarEmpresa(tipoEmpresa, dono, nome, aberto24Horas, numeroFuncionarios);
//    }

    // Entregador

    public void criarUsuario(String nome, String email, String senha, String endereco, String veiculo, String placa) throws EmailAlreadyExist, InvalidName {
        sistema.criarUsuario(nome, email, senha, endereco, veiculo, placa);
    }

    public List<Entregador> getEntregadores(int empresa) {
        return sistema.getEntregadores(empresa);
    }

}
