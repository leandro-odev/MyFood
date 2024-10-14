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

    private Sistema() {
        File usersFile = new File("users.xml");
        File empresasFile = new File("empresas.xml");
        File pedidosFile = new File("pedidos.xml");

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
    }

    public void zerarSistema() {
        File usersFile = new File("users.xml");
        File empresasFile = new File("empresas.xml");
        File pedidosFile = new File("pedidos.xml");
        users.clear();
        empresas.clear();
        pedidos.clear();
        usersFile.delete();
        empresasFile.delete();
        pedidosFile.delete();
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

    public Pedido getPedido(int id) {
        try {
            return pedidos.stream().filter(p -> p.numero == id).findFirst().get();
        } catch (Error e) {
            throw new Error("Não foi possivel encontrar pedido");
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
    private void verifyData(String nome, String email, String senha, String endereco) throws InvalidName {
        if (nome == null || nome.isEmpty()) {
            throw new InvalidName();
        } else if (email == null || email.isEmpty() || !email.contains("@")) {
            throw new Error("Email invalido");
        } else if (senha == null || senha.isEmpty()) {
            throw new Error("Senha invalido");
        } else if (endereco == null || endereco.isEmpty()) {
            throw new Error("Endereco invalido");
        }
    }

    //Verificar dados do Dono
    private void verifyData(String nome, String email, String senha, String endereco, String cpf) throws InvalidName {
        verifyData(nome, email, senha, endereco);
        if (cpf == null || cpf.contains("/") || cpf.length() != 14) {
            throw new Error("CPF invalido");
        }
    }

    //Cliente
    public void criarUsuario(String nome, String email, String senha, String endereco) throws EmailAlreadyExist, InvalidName {
        verifyData(nome, email, senha, endereco);

        if (users.stream().anyMatch(u -> u.email.equals(email))) {
            throw new EmailAlreadyExist();
        }
        User newUser = new Cliente(nome, email, senha, endereco);
        users.add(newUser);
    }

    //Dono
    public void criarUsuario(String nome, String email, String senha, String endereco, String cpf) throws EmailAlreadyExist, InvalidName {
        verifyData(nome, email, senha, endereco, cpf);

        if (users.stream().anyMatch(u -> u.email.equals(email))) {
            throw new EmailAlreadyExist();
        }
        User newUser = new Dono(nome, email, senha, cpf, endereco);
        users.add(newUser);
    }

    public int login(String email, String senha) {
        if (email == null || email.isEmpty() || !email.contains("@") ||senha == null || senha.isEmpty() || senha.length() < 4) {
            throw new Error("Login ou senha invalidos");
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

    public int getIdEmpresa (int idDono, String nome, int indice) throws EnterpriseNameNotRegistered, InvalidName {
        if (nome == null || nome.isEmpty()) {
            throw new InvalidName();
        } else if (indice < 0 || indice >= users.size()) {
            throw new Error("Indice invalido");
        }

        List<Enterprise> empresasComMesmoNome = empresas.stream().filter(r -> r.nome.equals(nome)).toList();

        if (indice < empresasComMesmoNome.size() && empresasComMesmoNome.size() != 0) {
            return empresasComMesmoNome.get(indice).id;
        } else if (empresasComMesmoNome.size() != 0 && indice >= empresasComMesmoNome.size()) {
            throw new Error("Indice maior que o esperado");
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

    public void editarProduto(int produto, String nome, float valor, String categoria) throws InvalidName, InvalidPrice, WrongCategory, ProductNotRegistered {

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
        throw new Error("Produto não encontrado");
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

    public int getNumeroPedido(int cliente, int empresa, int indice) {
        try {
            List<Pedido> pedidosCliente = pedidos.stream().filter(p -> p.empresa == empresa && p.cliente == cliente).toList();
            return pedidosCliente.get(indice).numero;
        } catch (Error e) {
            throw new Error("Pedido nao encontrado");
        }
    }

    public void adicionarProduto(int numero, int produto) throws NoOpenedOrder, ProductDoesntBelongEnterprise, CannotAddProductOrderClosed, RestauranteNotFound {

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

    public String getPedidos(int numero, String atributo) throws InvalidAttribute, AtributeDontExist, OrderNotFound {
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
//        throw new AtributeDontExist();
    }

    public void fecharPedido(int numero) throws OrderNotFound {
        Pedido pedido = pedidos.stream().filter(p -> p.numero == numero).findFirst().orElseThrow(OrderNotFound::new);
        pedidos.remove(pedido);
        pedido.estado = "preparando";
        pedidos.add(pedido);
        System.out.println(pedido);
    }

    public void removerPedido(int numero) {
        Pedido p = getPedido(numero);
        pedidos.remove(p);
    }

    public void removerProduto(int pedido, String produto) throws OrderNotFound, InvalidProduct, ProductNotFound, CannotRemoveProductOrderClosed {
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
    public int criarEmpresa(String tipoEmpresa, int dono, String nome, String endereco, String abre, String fecha, String tipoMercado) throws NameAlreadyExist, AddresAlreadyExist, NameAndAddresAlreadyExist, UserCantCreate {

        if (tipoEmpresa == null || tipoEmpresa.isEmpty()) {
            throw new Error("Tipo de empresa invalido");
        }

        if (endereco == null || endereco.isEmpty()) {
            throw new Error("Endereco da empresa invalido");
        }

        if (tipoEmpresa.equals("mercado")) {

            if (abre == null || fecha == null || abre.isEmpty() || fecha.isEmpty()) {
                throw new Error("Horario invalido"); // Se uma das horas estiver vazia, deve ser considerado "Horario invalido"
            }

            if (!isValidTime(abre) || !isValidTime(fecha)) {
                throw new Error("Formato de hora invalido");
            }

            if (isInvalidOpeningHour(abre, fecha)) {
                throw new Error("Horario invalido");
            }

            if (users.stream().noneMatch(u -> u.id == dono && (u.isWhatType() == "Dono"))) {
                throw new UserCantCreate();
            }

            if (nome == null || nome.isEmpty()) {
                throw new Error("Nome invalido");
            }

            if (tipoMercado == null || tipoMercado.isEmpty()) {
                throw new Error("Tipo de mercado invalido");
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
            throw new Error("Empresa não é um mercado");
        }
    }

    public void alterarFuncionamento(int mercado, String abre, String fecha) throws EnterpriseNotRegistered {
        if (empresas.stream().noneMatch(m -> m.id == mercado)) {
            throw new EnterpriseNotRegistered();
        }

        if (abre == null || fecha == null || abre.isEmpty() || fecha.isEmpty()) {
            throw new Error("Horario invalido");
        } else if (!isValidTime(abre) || !isValidTime(fecha)) {
            throw new Error("Formato de hora invalido");
        } else if (isInvalidOpeningHour(abre, fecha)) {
            throw new Error("Horario invalido");
        }

        Enterprise empresa = empresas.stream().filter(n -> n.id == mercado).findFirst().get();
        if (empresa.isWhatType().equals("Mercado")) {
            Mercado m = (Mercado) empresa;
            m.abre = abre;
            m.fecha = fecha;
        } else {
            throw new Error("Nao e um mercado valido");
        }
    }

    // Farmácia
    public int criarEmpresa(String tipoEmpresa, int dono, String nome, String endereco, Boolean aberto24Horas, int numeroFuncionarios) throws NameAndAddresAlreadyExist, NameAlreadyExist, UserCantCreate, InvalidName {
        if (tipoEmpresa == null || tipoEmpresa.isEmpty()) {
            throw new Error("Tipo de empresa invalido");
        }

        if (endereco == null || endereco.isEmpty()) {
            throw new Error("Endereco da empresa invalido");
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
    public void criarUsuario(String nome, String email, String senha, String endereco, String veiculo, String placa) throws EmailAlreadyExist, InvalidName, PlacaAlreadyExist, InvalidPlaque, InvalidVehicle {
        verifyData(nome, email, senha, endereco);

        if (veiculo == null || veiculo.isEmpty()) {
            throw new InvalidVehicle();
        }

        if (placa == null || placa.isEmpty()) {
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

//    public List<Entregador> getEntregadores(int empresa) {
//        return users.stream().anyMatch(u -> u.id == empresa && (u.isWhatType() == "Dono")) ? users.stream().filter(u -> u instanceof Entregador).map(u -> (Entregador) u).toList() : new ArrayList<>();
//    }


    public static boolean isValidTime(String time) {
        if (time.length() != 5 || time.charAt(2) != ':') {
            return false;
        }

        try {
            String[] parts = time.split(":");
            int horas = Integer.parseInt(parts[0]);
            int minutos = Integer.parseInt(parts[1]);

            if (horas < 0 || horas > 23 || minutos < 0 || minutos > 59) {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }

        return true;
    }

    public static boolean isInvalidOpeningHour(String abre, String fecha) {

        String[] abreParts = abre.split(":");
        String[] fechaParts = fecha.split(":");

        int abreHoras = Integer.parseInt(abreParts[0]);
        int abreMinutos = Integer.parseInt(abreParts[1]);
        int fechaHoras = Integer.parseInt(fechaParts[0]);
        int fechaMinutos = Integer.parseInt(fechaParts[1]);

        if (abreHoras > fechaHoras || (abreHoras == fechaHoras && abreMinutos >= fechaMinutos)) {
            return true;
        }
        return false;
    }
}