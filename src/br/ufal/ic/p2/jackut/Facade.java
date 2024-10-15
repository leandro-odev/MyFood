package br.ufal.ic.p2.jackut;

import br.ufal.ic.p2.jackut.Enterprises.Enterprise;
import br.ufal.ic.p2.jackut.Exceptions.*;
import br.ufal.ic.p2.jackut.Exceptions.Enterprise.*;
import br.ufal.ic.p2.jackut.Exceptions.Invalid.*;
import br.ufal.ic.p2.jackut.Exceptions.Orders.*;
import br.ufal.ic.p2.jackut.Exceptions.Products.*;
import br.ufal.ic.p2.jackut.Users.User;


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

    public Enterprise getEmpresa(int id) throws RestauranteNotFound {
        return sistema.getEmpresa(id);
    }

    public Pedido getPedido(int id) throws PedidoNotFound {
        return sistema.getPedido(id);
    }

    public String getAtributoUsuario(int id, String atributo) throws UserNotRegistered {
        return sistema.getAtributoUsuario(id, atributo);
    }

    public void criarUsuario(String nome, String email, String senha, String endereco) throws EmailAlreadyExist, InvalidName, InvalidEmail, InvalidAddress, InvalidPassword {
        sistema.criarUsuario(nome, email, senha, endereco);
    }

    public void criarUsuario(String nome, String email, String senha, String endereco, String cpf) throws EmailAlreadyExist, InvalidName, InvalidEmail, InvalidAddress, InvalidPassword {
        sistema.criarUsuario(nome, email, senha, endereco, cpf);
    }

    // Other methods from Sistema class

    public int login(String email, String senha) throws InvalidLoginData {
        return sistema.login(email, senha);
    }

    // Restaurante
    public int criarEmpresa(String tipoEmpresa, int dono, String nome, String endereco, String tipoCozinha) throws NameAlreadyExist, AddresAlreadyExist, NameAndAddresAlreadyExist, UserCantCreate {
        return sistema.criarEmpresa(tipoEmpresa, dono, nome, endereco, tipoCozinha);
    }

    public String getEmpresasDoUsuario(int idDono) throws UserCantCreate {
        return sistema.getEmpresasDoUsuario(idDono);
    }

    public int getIdEmpresa(int idDono, String nome, int indice) throws EnterpriseNameNotRegistered, InvalidName, InvalidIndex, BigIndex {
        return sistema.getIdEmpresa(idDono, nome, indice);
    }

    public String getAtributoEmpresa(int empresa, String atributo) throws InvalidAttribute, EnterpriseNotRegistered {
        return sistema.getAtributoEmpresa(empresa, atributo);
    }

    public int criarProduto(int empresa, String nome, float valor, String categoria) throws ProductNameAtEnterprise, InvalidName, InvalidPrice, WrongCategory, EnterpriseNotRegistered {
        return sistema.criarProduto(empresa, nome, valor, categoria);
    }

    public void editarProduto(int produto, String nome, float valor, String categoria) throws InvalidName, InvalidPrice, WrongCategory, ProductNotRegistered, RequestNotFound2 {
        sistema.editarProduto(produto, nome, valor, categoria);
    }

    public String getProduto(String nome, int empresa, String atributo) throws ProductNotFound, AtributeDontExist, RestauranteNotFound {
        return sistema.getProduto(nome, empresa, atributo);
    }

    public String listarProdutos(int empresa) throws EnterpriseNotFound, RestauranteNotFound {
        return sistema.listarProdutos(empresa);
    }

    public int criarPedido(int cliente, int empresa) throws DonoCannotCreateOrder, CannotHaveMoreThanOneOrderSameEnterprise {
        return sistema.criarPedido(cliente, empresa);
    }

    public int getNumeroPedido(int cliente, int empresa, int indice) throws RequestNotFound {
        return sistema.getNumeroPedido(cliente, empresa, indice);
    }

    public void adicionarProduto(int numero, int produto) throws NoOpenedOrder, ProductDoesntBelongEnterprise, CannotAddProductOrderClosed, RestauranteNotFound, PedidoNotFound {
        sistema.adicionarProduto(numero, produto);
    }

    public String getPedidos(int numero, String atributo) throws InvalidAttribute, AtributeDontExist, OrderNotFound, PedidoNotFound {
        return sistema.getPedidos(numero, atributo);
    }

    public void fecharPedido(int numero) throws OrderNotFound {
        sistema.fecharPedido(numero);
    }

    public void removerPedido(int numero) throws PedidoNotFound {
        sistema.removerPedido(numero);
    }

    public void removerProduto(int pedido, String produto) throws OrderNotFound, InvalidProduct, ProductNotFound, CannotRemoveProductOrderClosed, PedidoNotFound {
        sistema.removerProduto(pedido, produto);
    }

    // Mercado
    public int criarEmpresa(String tipoEmpresa, int dono, String nome, String endereco, String abre, String fecha, String tipoMercado) throws NameAlreadyExist, AddresAlreadyExist, NameAndAddresAlreadyExist, UserCantCreate, InvalidTimeFormat, InvalidTime, EnterpriseNotAMarket, InvalidMarketType, InvalidEnterpriseAddress, InvalidName, InvalidEnterpriseType {
        return sistema.criarEmpresa(tipoEmpresa, dono, nome, endereco, abre, fecha, tipoMercado);
    }

    public void alterarFuncionamento(int mercado, String abre, String fecha) throws EnterpriseNotRegistered, InvalidTimeFormat, InvalidTime, InvalidMarket {
        sistema.alterarFuncionamento(mercado, abre, fecha);
    }

    // Farmácia

    public int criarEmpresa(String tipoEmpresa, int dono, String nome, String endereco, Boolean aberto24Horas, int numeroFuncionarios) throws NameAndAddresAlreadyExist, NameAlreadyExist, UserCantCreate, InvalidName, InvalidEnterpriseAddress, InvalidEnterpriseType {
        return sistema.criarEmpresa(tipoEmpresa, dono, nome, endereco, aberto24Horas, numeroFuncionarios);
    }

        // Entregador

    public void criarUsuario(String nome, String email, String senha, String endereco, String veiculo, String placa) throws EmailAlreadyExist, InvalidName, PlacaAlreadyExist, InvalidVehicle, InvalidPlaque, InvalidEmail, InvalidAddress, InvalidPassword {
        sistema.criarUsuario(nome, email, senha, endereco, veiculo, placa);
    }

    public void cadastrarEntregador(Integer empresaId, Integer entregadorId) throws UserNotRegistered, RestauranteNotFound, UserNotDelivery {
        sistema.cadastrarEntregador(empresaId, entregadorId);
    }

    public String getEntregadores(Integer empresaId) throws RestauranteNotFound {
        return sistema.getEntregadores(empresaId);
    }

    public String getEmpresas(Integer entregadorId) throws UserNotRegistered, UserNotDelivery {
        return sistema.getEmpresas(entregadorId);
    }

    public void liberarPedido(Integer numero) throws PedidoNotFound, RequestAlreadyDone, RequestNotPreparing {
        sistema.liberarPedido(numero);
    }

    public Integer obterPedido(Integer entregadorId) throws PedidoNotFound, UserNotRegistered, NoEnterprises, UserNotDelivery {
        return sistema.obterPedido(entregadorId);
    }

    public Integer criarEntrega(Integer pedidoId, Integer entregadorId, String destino) throws PedidoNotFound, PedidoAlreadySent, UserNotRegistered, UserNotDelivery, RestauranteNotFound, BusyDelivery, InvalidDelivery, PedidoNotReady {
        return sistema.criarEntrega(pedidoId, entregadorId, destino);
    }

    public String getEntrega(Integer id, String atributo) throws EntregaNotFound, InvalidAttribute, AttributeNotFound {
        return sistema.getEntrega(id, atributo);
    }

    public Integer getIdEntrega(Integer pedidoId) throws EntregaIdNotFound {
        return sistema.getIdEntrega(pedidoId);
    }

    public void entregar(Integer entregaId) throws EntregaNotFound {
        sistema.entregar(entregaId);
    }

}
