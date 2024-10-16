package br.ufal.ic.p2.jackut;

import br.ufal.ic.p2.jackut.Enterprises.Enterprise;
import br.ufal.ic.p2.jackut.Enterprises.Farmacia;
import br.ufal.ic.p2.jackut.Enterprises.Mercado;
import br.ufal.ic.p2.jackut.Enterprises.Restaurante;
import br.ufal.ic.p2.jackut.Exceptions.*;
import br.ufal.ic.p2.jackut.Exceptions.Enterprise.*;
import br.ufal.ic.p2.jackut.Exceptions.Invalid.*;
import br.ufal.ic.p2.jackut.Exceptions.Orders.*;
import br.ufal.ic.p2.jackut.Exceptions.Products.*;
import br.ufal.ic.p2.jackut.Users.Cliente;
import br.ufal.ic.p2.jackut.Users.Dono;
import br.ufal.ic.p2.jackut.Users.Entregador;
import br.ufal.ic.p2.jackut.Users.User;

import java.io.File;
import java.util.*;

public class Sistema {
    private static Sistema instance;
    List<User> users;
    List<Enterprise> empresas;
    List<Pedido> pedidos;
    List<Entrega> entregas;

    private Sistema() {
        File usersFile = new File("users.xml");
        File empresasFile = new File("empresas.xml");
        File pedidosFile = new File("pedidos.xml");
        File entregasFile = new File("entregas.xml");

        if (usersFile.exists()) {
            users = XMLUtils.lerUsuarios("users.xml");
        } else {
            users = new ArrayList<>();
        }

        if (empresasFile.exists()) {
            empresas = XMLUtils.lerEmpresas("empresas.xml");
        } else {
            empresas = new ArrayList<>();
        }

        if (pedidosFile.exists()) {
            pedidos = XMLUtils.lerPedidos("pedidos.xml");
        } else {
            pedidos = new ArrayList<>();
        }

        if (entregasFile.exists()) {
            entregas = XMLUtils.lerEntregas("entregas.xml");
        } else {
            entregas = new ArrayList<>();
        }
    }

    static Sistema getInstance() {
        if (instance == null) {
            instance = new Sistema();
        }
        return instance;
    }

    public void encerrarSistema() {
        XMLUtils.salvarUsuarios(users, "users.xml");
        XMLUtils.salvarPedidos(pedidos, "pedidos.xml");
        XMLUtils.salvarEmpresas(empresas, "empresas.xml");
        XMLUtils.salvarEntregas(entregas, "entregas.xml");
    }

    public void zerarSistema() {
        File usersFile = new File("users.xml");
        File empresasFile = new File("empresas.xml");
        File pedidosFile = new File("pedidos.xml");
        File entregasFile = new File("entregas.xml");
        users.clear();
        empresas.clear();
        pedidos.clear();
        entregas.clear();
        usersFile.delete();
        empresasFile.delete();
        pedidosFile.delete();
        entregasFile.delete();
    }

    public User getUser(int id) throws UserNotRegistered {
        try {
            return users.stream().filter(u -> u.id == id).findFirst().orElseThrow();
        } catch (NoSuchElementException e) {
            throw new UserNotRegistered();
        }
    }

    public Enterprise getEmpresa(int id) throws RestauranteNotFound {
        try {
            return empresas.stream().filter(r -> r.id == id).findFirst().get();
        } catch (Error e) {
            throw new RestauranteNotFound();
        }
    }

    public Pedido getPedido(int id) throws PedidoNotFound {
        try {
            return pedidos.stream().filter(p -> p.numero == id).findFirst().get();
        } catch (Error e) {
            throw new PedidoNotFound();
        }
    }

    public String getAtributoUsuario(int id, String atributo) throws UserNotRegistered {
        User user = getUser(id);
        if (user == null) {
            throw new UserNotRegistered();
        }
        if ((user.isWhatType().equals("Dono")) && atributo.equals("cpf")) {
            return ((Dono) user).cpf;
        }
        if (user.isWhatType().equals("Entregador")) {
            Entregador entregador = (Entregador) user;
            return switch (atributo.toLowerCase()) {
                case "placa" -> entregador.placa;
                case "veiculo" -> entregador.veiculo;
                case "nome" -> entregador.nome;
                case "email" -> entregador.email;
                case "senha" -> entregador.senha;
                case "endereco" -> entregador.endereco;
                case "id", "numero" -> entregador.id + "";

                default -> throw new UserNotRegistered();
            };
        }
        return switch (atributo.toLowerCase()) {
            case "nome" -> user.nome;
            case "email" -> user.email;
            case "senha" -> user.senha;
            case "endereco" -> user.endereco;
            case "id", "numero" -> user.id + "";

            default -> throw new UserNotRegistered();
        };
    }

    //Verificar dados do Cliente
    private void verifyData(String nome, String email, String senha, String endereco) throws InvalidName, InvalidEmail, InvalidPassword, InvalidAddress {
        if (nome == null || nome.isEmpty()) {
            throw new InvalidName();
        } else if (email == null || email.isEmpty() || !email.contains("@")) {
            throw new InvalidEmail();
        } else if (senha == null || senha.isEmpty()) {
            throw new InvalidPassword();
        } else if (endereco == null || endereco.isEmpty()) {
            throw new InvalidAddress();
        }
    }

    //Verificar dados do Dono
    private void verifyData(String nome, String email, String senha, String endereco, String cpf) throws InvalidName, InvalidEmail, InvalidAddress, InvalidPassword {
        verifyData(nome, email, senha, endereco);
        if (cpf == null || cpf.contains("/") || cpf.length() != 14) {
            throw new Error("CPF invalido");
        }
    }

    //Cliente
    public void criarUsuario(String nome, String email, String senha, String endereco) throws EmailAlreadyExist, InvalidName, InvalidEmail, InvalidAddress, InvalidPassword {
        verifyData(nome, email, senha, endereco);

        if (users.stream().anyMatch(u -> u.email.equals(email))) {
            throw new EmailAlreadyExist();
        }
        User newUser = new Cliente(nome, email, senha, endereco);
        users.add(newUser);
    }

    //Dono
    public void criarUsuario(String nome, String email, String senha, String endereco, String cpf) throws EmailAlreadyExist, InvalidName, InvalidEmail, InvalidAddress, InvalidPassword {
        verifyData(nome, email, senha, endereco, cpf);

        if (users.stream().anyMatch(u -> u.email.equals(email))) {
            throw new EmailAlreadyExist();
        }
        User newUser = new Dono(nome, email, senha, cpf, endereco);
        users.add(newUser);
    }

    public int login(String email, String senha) throws InvalidLoginData {
        if (email == null || email.isEmpty() || !email.contains("@") ||senha == null || senha.isEmpty() || senha.length() < 4) {
            throw new InvalidLoginData();
        }
        return users.stream().filter(u -> u.email.equals(email) && u.senha.equals(senha)).findFirst().get().id;
    }

    //Restaurante
    public int criarEmpresa(String tipoEmpresa, int dono, String nome, String endereco, String tipoCozinha) throws NameAlreadyExist, AddresAlreadyExist, NameAndAddresAlreadyExist, UserCantCreate {
        if(users.stream().noneMatch(u -> u.id == dono && (u.isWhatType() == "Dono"))) {
            throw new UserCantCreate();
        }

        if (tipoEmpresa.equals("restaurante")) {

            if (empresas.stream().anyMatch(r -> r.nome.equals(nome) && r.idDono != dono) ) {
                throw new NameAlreadyExist();
            }

            if (empresas.stream().anyMatch(r -> r.nome.equals(nome) && r.endereco.equals(endereco) && r.idDono == dono) ) {
                throw new NameAndAddresAlreadyExist();
            }

            Restaurante novoRestaurante = new Restaurante(nome, endereco, tipoCozinha, dono);
            empresas.add(novoRestaurante);
            return novoRestaurante.id;
        } else {
            throw new Error("Empresa não é um restaurante");
        }
    }

    public String getEmpresasDoUsuario(int idDono) throws UserCantCreate {
        if (!users.stream().anyMatch(r -> r.id == idDono && (r.isWhatType() == "Dono"))) {
            throw new UserCantCreate();
        }

        StringBuilder stringRestaurantes = new StringBuilder("{[");
        for (Enterprise empresa : empresas) {
            if (empresa.idDono == idDono) {
                stringRestaurantes.append("[").append(empresa.nome).append(", ").append(empresa.endereco).append("], ");
            }
        }

        if (stringRestaurantes.length() == 2) {
            for (Enterprise empresa : empresas) {
                if (empresa.idDono == idDono) {
                    stringRestaurantes.append("[").append(empresa.nome).append(", ").append(empresa.endereco).append("], ");
                }
            }
        }

        if (stringRestaurantes.length() == 2) { // Se não houver restaurantes
            return "{[]}";
        } else {
            // Remover a vírgula e o espaço extras antes de fechar
            stringRestaurantes.setLength(stringRestaurantes.length() - 2);
            stringRestaurantes.append("]}");
            return stringRestaurantes.toString();
        }
    }

    public int getIdEmpresa (int idDono, String nome, int indice) throws EnterpriseNameNotRegistered, InvalidName, InvalidIndex, BigIndex {
        if (nome == null || nome.isEmpty()) {
            throw new InvalidName();
        } else if (indice < 0 || indice >= users.size()) {
            throw new InvalidIndex();
        }

        List<Enterprise> empresasComMesmoNome = empresas.stream().filter(r -> r.nome.equals(nome)).toList();

        if (indice < empresasComMesmoNome.size() && empresasComMesmoNome.size() != 0) {
            return empresasComMesmoNome.get(indice).id;
        } else if (empresasComMesmoNome.size() != 0 && indice >= empresasComMesmoNome.size()) {
            throw new BigIndex();
        }

        throw new EnterpriseNameNotRegistered();
    }


    // Get Atributo Empresa
    public String getAtributoEmpresa (int empresa, String atributo) throws InvalidAttribute, EnterpriseNotRegistered {

        Optional<Enterprise> empresaOpt = empresas.stream().filter(r -> r.id == empresa).findFirst();
        if (empresaOpt.isPresent()) {
            Enterprise emp = empresaOpt.get();
            if (emp.isWhatType().equals("Restaurante")){
                Restaurante restaurante = (Restaurante) emp;
                return getAtributoRestaurante(restaurante, atributo);
            } else if(emp.isWhatType().equals("Mercado")) {
                Mercado mercado = (Mercado) emp;
                return getAtributoMercado(mercado, atributo);
            } else if(emp.isWhatType().equals("Farmacia")) {
                Farmacia farmacia = (Farmacia) emp;
                return getAtributoFarmacia(farmacia, atributo);
            }
        }
        throw new EnterpriseNotRegistered();

    }

    private String getAtributoRestaurante(Restaurante restaurante, String atributo) throws InvalidAttribute {

        if (atributo == null || atributo.isEmpty()) {
            throw new InvalidAttribute();
        }

        switch (atributo.toLowerCase()) {
            case "id":
                return restaurante.id + "";
            case "nome":
                return restaurante.nome;
            case "endereco":
                return restaurante.endereco;
            case "tipocozinha":
                return restaurante.tipoCozinha;
            case "dono":
                String nomeDono = users.stream().filter(r -> r.id == restaurante.idDono).map(r -> r.nome).findFirst().orElse("Dono não encontrado");
                return nomeDono;
        }

        throw new InvalidAttribute();
    }

    private String getAtributoMercado(Mercado mercado, String atributo) throws InvalidAttribute {

            if (atributo == null || atributo.isEmpty()) {
                throw new InvalidAttribute();
            }

            switch (atributo.toLowerCase()) {
                case "id":
                    return mercado.id + "";
                case "nome":
                    return mercado.nome;
                case "endereco":
                    return mercado.endereco;
                case "tipomercado":
                    return mercado.tipoMercado;
                case "abre":
                    return mercado.abre;
                case "fecha":
                    return mercado.fecha;
                case "dono":
                    String nomeDono = users.stream().filter(r -> r.id == mercado.idDono).map(r -> r.nome).findFirst().orElse("Dono não encontrado");
                    return nomeDono;
            }

            throw new InvalidAttribute();
    }

    private String getAtributoFarmacia(Farmacia farmacia, String atributo) throws InvalidAttribute {
        if (atributo == null || atributo.isEmpty()) {
            throw new InvalidAttribute();
        }

        return switch (atributo.toLowerCase()) {
            case "id" -> farmacia.id + "";
            case "nome" -> farmacia.nome;
            case "endereco" -> farmacia.endereco;
            case "aberto24horas" -> farmacia.aberto24horas + "";
            case "numerofuncionarios" -> farmacia.numeroFuncionarios + "";
            case "dono" -> users.stream().filter(r -> r.id == farmacia.idDono).map(r -> r.nome).findFirst().orElse("Dono não encontrado");
            default -> throw new InvalidAttribute();
        };
    }



    public int criarProduto(int empresa, String nome, float valor, String categoria) throws ProductNameAtEnterprise, InvalidName, InvalidPrice, WrongCategory, EnterpriseNotRegistered {
        if (nome == null || nome.equals("")) {
            throw new InvalidName();
        }
        else if (valor < 0) {
            throw new InvalidPrice();
        }
        else if (categoria == null || categoria.equals("")) {
            throw new WrongCategory();
        }

        Optional<Enterprise> empresaOpt = empresas.stream().filter(r -> r.id == empresa).findFirst();

        if (empresaOpt.isPresent()) {
            Enterprise emp = empresaOpt.get();

            if (emp.isWhatType().equals("Restaurante")) {
                Restaurante r = (Restaurante) emp;
                if (r.produtos.stream().anyMatch(p -> p.nome.equals(nome))) {
                    throw new ProductNameAtEnterprise();
                }
                Produto p = new Produto(nome, valor, categoria);
                r.produtos.add(p);
                return p.numero;
            } else if (emp.isWhatType().equals("Mercado")) {
                Mercado m = (Mercado) emp;
                if (m.produtos.stream().anyMatch(p -> p.nome.equals(nome))) {
                    throw new ProductNameAtEnterprise();
                }
                Produto p = new Produto(nome, valor, categoria);
                m.produtos.add(p);
                return p.numero;
            } else if (emp.isWhatType().equals("Farmacia")) {
                Farmacia f = (Farmacia) emp;
                if (f.produtos.stream().anyMatch(p -> p.nome.equals(nome))) {
                    throw new ProductNameAtEnterprise();
                }
                Produto p = new Produto(nome, valor, categoria);
                f.produtos.add(p);
                return p.numero;
            }
        }
        throw new EnterpriseNotRegistered();

    }

    public void editarProduto(int produto, String nome, float valor, String categoria) throws InvalidName, InvalidPrice, WrongCategory, ProductNotRegistered, RequestNotFound2 {

        if (nome == null || nome.equals("")) {
            throw new InvalidName();
        } else if (valor < 0) {
            throw new InvalidPrice();
        } else if (categoria == null || categoria.equals("")) {
            throw new WrongCategory();
        } else if (produto < 0 || produto >= empresas.size() || empresas.stream().noneMatch(r -> r.produtos.stream().anyMatch(p -> p.numero == produto))) {
            throw new ProductNotRegistered();
        }

        for (Enterprise empresa: empresas) {
            for (Produto p : empresa.produtos) {
                if (p.numero == produto) {
                    p.nome = nome;
                    p.valor = valor;
                    p.categoria = categoria;
                    return;
                }
            }
        }
        throw new RequestNotFound2();
    }

    public String getProduto(String nome, int empresaId, String atributo) throws ProductNotFound, AtributeDontExist, RestauranteNotFound {
        Enterprise empresa = getEmpresa(empresaId);
        for (Produto p : empresa.produtos) {
            if (p.nome.equals(nome)) {
                switch (atributo.toLowerCase()) {
                    case "id", "produto":
                        return p.numero + "";
                    case "nome":
                        return p.nome;
                    case "valor":
                        String valor = String.format("%.2f", p.valor);
                        if (valor.contains(",")) {
                            valor = valor.replace(",", ".");
                        }
                        return valor;
                    case "categoria":
                        return p.categoria;
                    case "empresa":
                        return empresa.nome;
                }
                throw new AtributeDontExist();
            }
        }
        throw new ProductNotFound();
    }

    public String listarProdutos(int empresaId) throws EnterpriseNotFound, RestauranteNotFound {

        if (empresas.stream().noneMatch(r -> r.id == empresaId)) {
            throw new EnterpriseNotFound();
        }

        Enterprise r = getEmpresa(empresaId);
        StringBuilder stringProdutos = new StringBuilder("{[");;
        // Se não achar a empresa, retorna uma string vazia

        for (Produto p : r.produtos) {
            stringProdutos.append(p.nome).append(", ");
        }
        if (stringProdutos.length() > 2) {
            stringProdutos.setLength(stringProdutos.length() - 2);
        }
        stringProdutos.append("]}");
        return stringProdutos.toString();

    }

    public int criarPedido(int cliente, int empresa) throws DonoCannotCreateOrder, CannotHaveMoreThanOneOrderSameEnterprise {
        if (users.stream().anyMatch(u -> u.id == cliente && (u.isWhatType() == "Dono"))) {
            throw new DonoCannotCreateOrder();
        } else if (pedidos.stream().anyMatch(p -> p.cliente == cliente && p.empresa == empresa && p.estado.equals("aberto"))) {
            throw new CannotHaveMoreThanOneOrderSameEnterprise();
        }



        Pedido p = new Pedido(cliente, empresa);
        pedidos.add(p);
        return p.numero;
    }

    public int getNumeroPedido(int cliente, int empresa, int indice) throws RequestNotFound {
        try {
            List<Pedido> pedidosCliente = pedidos.stream().filter(p -> p.empresa == empresa && p.cliente == cliente).toList();
            return pedidosCliente.get(indice).numero;
        } catch (Error e) {
            throw new RequestNotFound();
        }
    }

    public void adicionarProduto(int numero, int produto) throws NoOpenedOrder, ProductDoesntBelongEnterprise, CannotAddProductOrderClosed, RestauranteNotFound, PedidoNotFound {

        if (pedidos.stream().noneMatch(p -> p.numero == numero)) {
            throw new NoOpenedOrder();
        } else if(pedidos.stream().anyMatch(p -> p.numero == numero && p.estado.equals("preparando"))) {
            throw new CannotAddProductOrderClosed();
        }

        Pedido p = getPedido(numero);
        Enterprise ent = empresas.stream().filter(r -> r.id == p.empresa).findFirst().orElseThrow(RestauranteNotFound::new);

        if (!ent.produtos.stream().anyMatch(rest -> rest.numero == produto)) {
            throw new ProductDoesntBelongEnterprise();
        }

        for (Produto prod : ent.produtos) {
            if (prod.numero == produto) {
                p.produtos.add(prod);
                break;
            }
        }
    }

    public String getPedidos(int numero, String atributo) throws InvalidAttribute, AtributeDontExist, OrderNotFound, PedidoNotFound {
        if (atributo == null || atributo.isEmpty()) {
            throw new InvalidAttribute();
        } else if (pedidos.stream().noneMatch(p -> p.numero == numero)) {
            throw new OrderNotFound();
        }

        Pedido p = getPedido(numero);
        switch (atributo.toLowerCase()) {
            case "id", "numero":
                return p.numero + "";
            case "cliente":
                int idCliente = p.cliente;
                String nomeCliente = users.stream().filter(u -> u.id == idCliente).map(u -> u.nome).findFirst().orElse("Cliente não encontrado");
                return nomeCliente;
            case "produtos":
                StringBuilder produtos = new StringBuilder("{[");
                for (Produto prod : p.produtos) {
                    produtos.append(prod.nome).append(", ");
                }
                if (produtos.length() > 2) {
                    // Remover a última vírgula e espaço extra
                    produtos.setLength(produtos.length() - 2);
                }
                produtos.append("]}");
                return produtos.toString();
            case "estado":
                return p.estado;
            case "valor", "preço", "preco":
                String valor = String.format("%.2f", p.valor());
                valor = valor.replace(",", ".");
                return valor;
            case "empresa":
                int idEmpresa = p.empresa;
                String nomeEmpresa = empresas.stream().filter(r -> r.id == idEmpresa).map(r -> r.nome).findFirst().orElse("Empresa não encontrada");
                return nomeEmpresa;
            default:
                throw new AtributeDontExist();
        }
    }

    public void fecharPedido(int numero) throws OrderNotFound {
        Pedido pedido = pedidos.stream().filter(p -> p.numero == numero).findFirst().orElseThrow(OrderNotFound::new);
        pedidos.remove(pedido);
        pedido.estado = "preparando";
        pedidos.add(pedido);
        System.out.println(pedido);
    }

    public void removerPedido(int numero) throws PedidoNotFound {
        Pedido p = getPedido(numero);
        pedidos.remove(p);
    }

    public void removerProduto(int pedido, String produto) throws OrderNotFound, InvalidProduct, ProductNotFound, CannotRemoveProductOrderClosed, PedidoNotFound {
        if (pedidos.stream().anyMatch(p -> p.numero == pedido && p.estado.equals("preparando"))) {
            throw new CannotRemoveProductOrderClosed();
        } else if(produto == null || produto.isEmpty()) {
            throw new InvalidProduct();
        } else if (pedidos.stream().noneMatch(p -> p.numero == pedido && p.produtos.stream().anyMatch(prod -> prod.nome.equals(produto)))) {
            throw new ProductNotFound();
        } else if (pedidos.stream().anyMatch(p -> p.numero == pedido && p.estado.equals("preparando"))) {
            throw new CannotRemoveProductOrderClosed();
        } else if (pedidos.stream().noneMatch(p -> p.numero == pedido)) {
            throw new OrderNotFound();
        }


        Pedido p = getPedido(pedido);
        p.produtos.stream().filter(prod -> prod.nome.equals(produto)).findFirst().ifPresent(p.produtos::remove);
    }

    //Mercado
    public int criarEmpresa(String tipoEmpresa, int dono, String nome, String endereco, String abre, String fecha, String tipoMercado) throws NameAlreadyExist, NameAndAddresAlreadyExist, UserCantCreate, InvalidTimeFormat, InvalidTime, EnterpriseNotAMarket, InvalidMarketType, InvalidName, InvalidEnterpriseAddress, InvalidEnterpriseType {
        if (tipoEmpresa == null || tipoEmpresa.isEmpty()) {
            throw new InvalidEnterpriseType();
        }

        if (endereco == null || endereco.isEmpty()) {
            throw new InvalidEnterpriseAddress();
        }

        if (tipoEmpresa.equals("mercado")) {
            clock(abre, fecha);

            if (users.stream().noneMatch(u -> u.id == dono && (u.isWhatType() == "Dono"))) {
                throw new UserCantCreate();
            }

            if (nome == null || nome.isEmpty()) {
                throw new InvalidName();
            }

            if (tipoMercado == null || tipoMercado.isEmpty()) {
                throw new InvalidMarketType();
            }

            if (empresas.stream().anyMatch(r -> r.nome.equals(nome) && r.idDono != dono)) {
                throw new NameAlreadyExist();
            }

            if (empresas.stream().anyMatch(r -> r.nome.equals(nome) && r.endereco.equals(endereco) && r.idDono == dono)) {
                throw new NameAndAddresAlreadyExist();
            }

            Mercado novoMercado = new Mercado(dono, nome, endereco, abre, fecha, tipoMercado);
            empresas.add(novoMercado);
            return novoMercado.id;
        } else {
            throw new EnterpriseNotAMarket();
        }
    }

    public void alterarFuncionamento(int mercado, String abre, String fecha) throws EnterpriseNotRegistered, InvalidTimeFormat, InvalidTime, InvalidMarket {
        if (empresas.stream().noneMatch(m -> m.id == mercado)) {
            throw new EnterpriseNotRegistered();
        }

        clock(abre, fecha);

        Enterprise empresa = empresas.stream().filter(n -> n.id == mercado).findFirst().get();
        if (empresa.isWhatType().equals("Mercado")) {
            Mercado m = (Mercado) empresa;
            m.abre = abre;
            m.fecha = fecha;
        } else {
            throw new InvalidMarket();
        }
    }

    // Farmácia
    public int criarEmpresa(String tipoEmpresa, int dono, String nome, String endereco, Boolean aberto24Horas, int numeroFuncionarios) throws NameAndAddresAlreadyExist, NameAlreadyExist, UserCantCreate, InvalidName, InvalidEnterpriseType, InvalidEnterpriseAddress {
        if (tipoEmpresa == null || tipoEmpresa.isEmpty()) {
            throw new InvalidEnterpriseType();
        }

        if (endereco == null || endereco.isEmpty()) {
            throw new InvalidEnterpriseAddress();
        }
        if (tipoEmpresa.equals("farmacia")) {
            if (users.stream().noneMatch(u -> u.id == dono && (u.isWhatType().equals("Dono")))) {
                throw new UserCantCreate();
            }

            if (nome == null || nome.isEmpty()) {
                throw new InvalidName();
            }

            if (empresas.stream().anyMatch(r -> r.nome.equals(nome) && r.idDono != dono)) {
                throw new NameAlreadyExist();
            }

            if (empresas.stream().anyMatch(r -> r.nome.equals(nome) && r.endereco.equals(endereco) && r.idDono == dono)) {
                throw new NameAndAddresAlreadyExist();
            }

            Farmacia novaFarmacia = new Farmacia(dono, nome, endereco, aberto24Horas, numeroFuncionarios);
            empresas.add(novaFarmacia);
            return novaFarmacia.id;
        } else {
            throw new Error("Empresa não é uma farmacia");
        }
    }

    // Entregador
    public void criarUsuario(String nome, String email, String senha, String endereco, String veiculo, String placa) throws EmailAlreadyExist, InvalidName, PlacaAlreadyExist, InvalidPlaque, InvalidVehicle, InvalidEmail, InvalidAddress, InvalidPassword {
        verifyData(nome, email, senha, endereco);

        if (veiculo == null || veiculo.isEmpty()) {
            throw new InvalidVehicle();
        }

        if (placa == null || placa.isEmpty() || users.stream().anyMatch(u -> u.isWhatType().equals("Entregador") && ((Entregador) u).placa.equals(placa))) {
            throw new InvalidPlaque();
        }

        if (users.stream().anyMatch(u -> u.email.equals(email))) {
            throw new EmailAlreadyExist();
        }

        User newUser = new Entregador(nome, email, senha, endereco, veiculo, placa);
        users.add(newUser);
    }

    public void cadastrarEntregador(Integer empresaId, Integer entregadorId) throws UserNotRegistered, RestauranteNotFound, UserNotDelivery {
        Enterprise empresa = getEmpresa(empresaId);
        User entregador = getUser(entregadorId);

        if (entregador.isWhatType().equals("Entregador")) {
            empresas.stream().filter(e -> e.id == empresaId).findFirst().get().entregadores.add(entregadorId);
            ((Entregador) users.stream().filter(u -> u.id == entregadorId).findFirst().get()).empresas.add(empresaId);
        } else {
            throw new UserNotDelivery();
        }
    }

    public String getEntregadores(Integer empresaId) throws RestauranteNotFound {
        ArrayList<String> emails = new ArrayList<String>();
        Enterprise empresa = getEmpresa(empresaId);
        for (Integer e: empresa.entregadores) {
            for (User u: users) {
                if (u.id == e) {
                    emails.add(u.email);
                }
            }
        }
        return "{" + emails.toString() + "}";
    }

    public String getEmpresas(Integer entregadorId) throws UserNotRegistered, UserNotDelivery {
        ArrayList<ArrayList<String>> dados = new ArrayList<ArrayList<String>>();
        User entregador = getUser(entregadorId);

        if (entregador.isWhatType().equals("Entregador")) {
            for (Integer e : ((Entregador) entregador).empresas) {
                Enterprise emp = empresas.stream().filter(empresa -> empresa.id == e).findFirst().get();
                ArrayList<String> dadosUser = new ArrayList<>();
                dadosUser.add(emp.nome);
                dadosUser.add(emp.endereco);
                dados.add(dadosUser);
            }
            return "{" + dados.toString() + "}";
        } else {
            throw new UserNotDelivery();
        }
    }

    public static void clock(String abre, String fecha) throws InvalidTime, InvalidTimeFormat {
        if (abre == null || fecha == null) {
            throw new InvalidTime();
        }

        if (abre.isEmpty() || fecha.isEmpty()) {
            throw new InvalidTimeFormat();
        }

        if (abre.length() != 5 || fecha.length() != 5) {
            throw new InvalidTimeFormat();
        }

        String[] abreSplit = abre.split(":");
        int abreHoras = Integer.parseInt(abreSplit[0]);
        int abreMinutos = Integer.parseInt(abreSplit[1]);

        String[] fechaSplit = fecha.split(":");
        int fechaHoras = Integer.parseInt(fechaSplit[0]);
        int fechaMinutos = Integer.parseInt(fechaSplit[1]);

        if (abreHoras < 0 || abreHoras > 23 || abreMinutos < 0 || abreMinutos > 59 || fechaHoras < 0 || fechaHoras > 23 || fechaMinutos < 0 || fechaMinutos > 59) {
            throw new InvalidTime();
        }

        if (abreHoras == fechaHoras) {
            if (abreMinutos < fechaMinutos) {
                throw new InvalidTime();
            }
        }

        if (fechaHoras < abreHoras) {
            throw new InvalidTime();
        }
    }

    public void liberarPedido(Integer numero) throws PedidoNotFound, RequestAlreadyDone, RequestNotPreparing {
        Pedido pedido = getPedido(numero);
        if (pedido == null) {
            throw new PedidoNotFound();
        } else if (pedido.estado.equals("pronto")) {
            throw new RequestAlreadyDone();
        } else if (!pedido.estado.equals("preparando")) {
            throw new RequestNotPreparing();
        } else {
            try {
                pedidos.stream().filter(p -> p.numero == pedido.numero).findFirst().get().estado = "pronto";
            } catch (Exception e) {
                throw new PedidoNotFound();
            }
        }
    }

    public Integer obterPedido(Integer entregadorId) throws PedidoNotFound, UserNotRegistered, UserNotDelivery, NoEnterprises {
        User entregador = getUser(entregadorId);
        if (!entregador.isWhatType().equals("Entregador")) {
            throw new UserNotDelivery();
        }

        ArrayList<Integer> empresasEnt = ((Entregador) entregador).empresas;
        if (empresasEnt.isEmpty()) {
            throw new NoEnterprises();
        }
        Pedido deFarmacia = pedidos.stream()
                .filter(p -> empresas.stream()
                        .anyMatch(e -> e.id == p.empresa && e.isWhatType().equals("Farmacia")))
                .filter(p -> empresasEnt.contains(p.empresa))
                .filter(p -> p.estado.equals("pronto"))
                .findFirst()
                .orElse(null);

        if (deFarmacia != null) {
            return deFarmacia.numero;
        }

        Pedido normal = pedidos.stream().filter(p -> empresasEnt.contains(p.empresa) && p.estado.equals("pronto")).findFirst().orElse(null);

        if (normal != null) {
            return normal.numero;
        } else {
            throw new PedidoNotFound();
        }
    }

    public Integer criarEntrega(Integer pedidoId, Integer entregadorId, String destino) throws PedidoNotFound, PedidoAlreadySent, UserNotRegistered, UserNotDelivery, RestauranteNotFound, BusyDelivery, InvalidDelivery, PedidoNotReady {
        Pedido pedido = getPedido(pedidoId);
        if (pedido.estado.equals("liberando")) {
            throw new PedidoAlreadySent();
        }
        if (!pedido.estado.equals("pronto")) {
            throw new PedidoNotReady();
        }

        if (destino == null || destino.trim().isEmpty()) {
            destino = users.stream().filter(u -> u.id == pedido.cliente).findFirst().get().endereco;
        }

        User entregador = getUser(entregadorId);
        if (!entregador.isWhatType().equals("Entregador")) {
            throw new InvalidDelivery();
        }

        Entregador ent = (Entregador) entregador;
        if (ent.ocupado) {
            throw new BusyDelivery();
        }

        User cliente = getUser(pedido.cliente);
        Enterprise empresa = getEmpresa(pedido.empresa);
        pedidos.stream().filter(p -> p.numero == pedidoId).findFirst().get().estado = "entregando";
        users.stream()
                .filter(u -> u.id == entregadorId)
                .findFirst()
                .ifPresent(u -> {
                    if (u.isWhatType().equals("Entregador")) {
                        Entregador entregad = (Entregador) u;
                        entregad.ocupado = true;
                    }
                });
        ArrayList<String> produtos = new ArrayList<>();
        for (Produto p: pedido.produtos) {
            produtos.add(p.nome);
        }
        Entrega entrega = new Entrega(cliente.nome, empresa.nome, pedido.numero, entregadorId, destino, produtos);
        entregas.add(entrega);
        return entrega.id;
    }

    public String getEntrega(Integer id, String atributo) throws EntregaNotFound, InvalidAttribute, AttributeNotFound {
        if (atributo == null) {
            throw new InvalidAttribute();
        } else if (atributo.isEmpty()) {
            throw new InvalidAttribute();
        }
        Entrega entrega = entregas.stream().filter(e -> e.id == id).findFirst().orElse(null);
        if (entrega != null) {
            return switch (atributo) {
              case "id" -> "" + entrega.id;
              case "cliente" -> entrega.cliente;
              case "empresa" -> entrega.empresa;
              case "entregador" -> users.stream().filter(u -> u.id == entrega.entregador).findFirst().get().nome;
              case "destino" -> entrega.destino;
              case "pedido" -> "" + entrega.pedido;
              case "produtos" -> "{" + entrega.produtos.toString() + "}";

              default -> throw new AttributeNotFound();
            };
        } else {
            throw new EntregaNotFound();
        }
    }

    public Integer getIdEntrega(Integer pedidoId) throws EntregaIdNotFound {
        try {
            Integer entId = entregas.stream().filter(e -> Objects.equals(e.pedido, pedidoId)).findFirst().get().id;
            return entId;
        } catch (Exception e) {
            throw new EntregaIdNotFound();
        }
    }

    public void entregar(Integer entregaId) throws EntregaNotFound {
        Entrega entrega = entregas.stream().filter(e -> e.id == entregaId).findFirst().orElse(null);
        if (entrega == null) {
            throw new EntregaNotFound();
        }

        pedidos.stream().filter(p -> p.numero == entrega.pedido).findFirst().get().estado = "entregue";
        users.stream()
                .filter(u -> u.id == entrega.entregador)
                .findFirst()
                .ifPresent(u -> {
                    if (u.isWhatType().equals("Entregador")) {
                        Entregador entregad = (Entregador) u;
                        entregad.ocupado = false;
                    }
                });
    }
}