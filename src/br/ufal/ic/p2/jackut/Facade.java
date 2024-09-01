package br.ufal.ic.p2.jackut;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import br.ufal.ic.p2.jackut.Exceptions.*;
import br.ufal.ic.p2.jackut.Exceptions.Enterprise.*;
import br.ufal.ic.p2.jackut.Exceptions.Orders.*;
import br.ufal.ic.p2.jackut.Exceptions.products.*;

public class Facade {
    List<User> users;
    List<Restaurante> restaurantes;
    List<Pedido> pedidos;

    public Facade() {
        //zerarSistema();
        File usersFile = new File("users.xml");
        File restaurantesFile = new File("restaurantes.xml");
        File pedidosFile = new File("pedidos.xml");

        if (usersFile.exists()) {
            users = XMLUtils.lerUsuarios("users.xml");
        } else {
            users = new ArrayList<>();
        }

        if (restaurantesFile.exists()) {
            restaurantes = XMLUtils.lerRestaurantes("restaurantes.xml");
        } else {
            restaurantes = new ArrayList<>();
        }

        if (pedidosFile.exists()) {
            pedidos = XMLUtils.lerPedidos("pedidos.xml");
        } else {
            pedidos = new ArrayList<>();
        }
    }

    public void encerrarSistema() {
        XMLUtils.salvarUsuarios(users, "users.xml");
        XMLUtils.salvarPedidos(pedidos, "pedidos.xml");
        XMLUtils.salvarRestaurantes(restaurantes, "restaurantes.xml");
    }

    public void zerarSistema() {
        File usersFile = new File("users.xml");
        File restaurantesFile = new File("restaurantes.xml");
        File pedidosFile = new File("pedidos.xml");
        usersFile.delete();
        restaurantesFile.delete();
        pedidosFile.delete();
    }

    public User getUser(int id) throws UserNotRegistered {
        try {
            return users.stream().filter(u -> u.id == id).findFirst().orElseThrow();
        } catch (NoSuchElementException e) {
            throw new UserNotRegistered();
        }
    }

    public Restaurante getRestaurante(int id) {
        try {
            return restaurantes.stream().filter(r -> r.id == id).findFirst().get();
        } catch (Error e) {
            throw new Error("Não foi possivel encontrar restaurante");
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
        if (user instanceof Dono && atributo.equals("cpf")) {
            return ((Dono) user).cpf;
        }
        return switch (atributo) {
            case "nome" -> user.nome;
            case "email" -> user.email;
            case "senha" -> user.senha;
            case "endereco" -> user.endereco;
            case "id", "numero" -> user.id + "";

            default -> throw new UserNotRegistered();
        };
    }

    //Cliente
    public void criarUsuario(String nome, String email, String senha, String endereco) throws EmailAlreadyExist {
        if (nome == null || nome.isEmpty()) {
            throw new Error("Nome invalido");
        } else if (email == null || email.isEmpty() || !email.contains("@")) {
            throw new Error("Email invalido");
        } else if (senha == null || senha.isEmpty()) {
            throw new Error("Senha invalido");
        } else if (endereco == null || endereco.isEmpty()) {
            throw new Error("Endereco invalido");
        }

        if (users.stream().anyMatch(u -> u.email.equals(email))) {
            throw new EmailAlreadyExist();
        }
        User newUser = new Cliente(nome, email, senha, endereco);
        users.add(newUser);
    }

    //Dono
    public void criarUsuario(String nome, String email, String senha, String endereco, String cpf) throws EmailAlreadyExist {
        if (nome == null || nome.isEmpty()) {
            throw new Error("Nome invalido");
        } else if (email == null || email.isEmpty() || !email.contains("@")) {
            throw new Error("Email invalido");
        } else if (senha == null || senha.isEmpty()) {
            throw new Error("Senha invalido");
        } else if (endereco == null || endereco.isEmpty()) {
            throw new Error("Endereco invalido");
        } else if (cpf == null || cpf.isEmpty() || cpf.contains("/") || cpf.length() != 14) {
            throw new Error("CPF invalido");
        }

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

    public int criarEmpresa(String tipoEmpresa, int dono, String nome, String endereco, String tipoCozinha) throws NameAlreadyExist, AddresAlreadyExist, NameAndAddresAlreadyExist, UserCantCreate {
        if(users.stream().noneMatch(u -> u.id == dono && u instanceof Dono)) {
            throw new UserCantCreate();
        }

        if (tipoEmpresa.equalsIgnoreCase("restaurante")) {

            if ( restaurantes.stream().anyMatch(r -> r.nome.equals(nome)) && !restaurantes.stream().anyMatch(r -> r.id == dono) ){
                throw new NameAlreadyExist();
            } else if ( restaurantes.stream().anyMatch(r -> r.nome.equals(nome)) && restaurantes.stream().anyMatch(r -> r.id == dono) && restaurantes.stream().anyMatch(r -> r.endereco.equals(endereco)) ) {
                throw new  NameAndAddresAlreadyExist();
            }

            Restaurante novoRestaurante = new Restaurante(nome, endereco, tipoCozinha, dono);
            restaurantes.add(novoRestaurante);
            return novoRestaurante.id;
        } else {
            throw new Error("Empresa não é um restaurante");
        }
    }

    public String getEmpresasDoUsuario(int idDono) throws UserCantCreate {

        if (!users.stream().anyMatch(r -> r.id == idDono && r.isDono())) {
            throw new UserCantCreate();
        }

        StringBuilder stringRestaurantes = new StringBuilder("{[");
        for (Restaurante restaurante : restaurantes) {
            if (restaurante.idDono == idDono) {
                stringRestaurantes.append("[").append(restaurante.nome).append(", ").append(restaurante.endereco).append("], ");
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

    public int getIdEmpresa (int idDono, String nome, int indice) throws EnterpriseNameNotRegistered {
        if (nome == null || nome.isEmpty()) {
            throw new Error("Nome invalido");
        } else if (indice < 0 || indice >= users.size()) {
            throw new Error("Indice invalido");
        }
        List<Restaurante> empresasComMesmoNome = restaurantes.stream().filter(r -> r.nome.equals(nome)).toList();

        if (empresasComMesmoNome.size() == 0) {
            throw new EnterpriseNameNotRegistered();
        }
        if (indice >= empresasComMesmoNome.size()) {
            throw new Error("Indice maior que o esperado");
        }

        return empresasComMesmoNome.get(indice).id;
    }

    public String getAtributoEmpresa (int empresa, String atributo) throws InvalidAttribute, EnterpriseNotRegistered {

        Restaurante restaurante = restaurantes.stream().filter(r -> r.id == empresa).findFirst().orElseThrow(EnterpriseNotRegistered::new);

        if (atributo == null) {
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

    public int criarProduto(int empresa, String nome, float valor, String categoria) throws ProductNameAtEnterprise, InvalidName, InvalidPrice, WrongCategory {
        if (nome == null || nome.equals("")) {
            throw new InvalidName();
        } else if (valor < 0) {
            throw new InvalidPrice();
        } else if (categoria == null || categoria.equals("")) {
            throw new WrongCategory();
        }
        Restaurante r = getRestaurante(empresa);
        if (r.produtos.stream().anyMatch(p -> p.nome.equals(nome))) {
            throw new ProductNameAtEnterprise();
        }
        Produto p = new Produto(nome, valor, categoria);
        r.produtos.add(p);
        return p.numero;
    }

    public void editarProduto(int produto, String nome, float valor, String categoria) throws InvalidName, InvalidPrice, WrongCategory, ProductNotRegistered {

        if (nome == null || nome.equals("")) {
            throw new InvalidName();
        } else if (valor < 0) {
            throw new InvalidPrice();
        } else if (categoria == null || categoria.equals("")) {
            throw new WrongCategory();
        } else if (produto < 0 || produto >= restaurantes.size() || restaurantes.stream().noneMatch(r -> r.produtos.stream().anyMatch(p -> p.numero == produto))) {
            throw new ProductNotRegistered();
        }

        for (Restaurante restaurante : restaurantes) {
            for (Produto p : restaurante.produtos) {
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

    public String getProduto(String nome, int empresa, String atributo) throws ProductNotFound, AtributeDontExist {
        Restaurante restaurante = getRestaurante(empresa);
        for (Produto p : restaurante.produtos) {
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
                        return restaurante.nome;
                }
                throw new AtributeDontExist();
            }
        }
        throw new ProductNotFound();
    }

    public String listarProdutos(int empresa) throws EnterpriseNotFound {

        if (restaurantes.stream().noneMatch(r -> r.id == empresa)) {
            throw new EnterpriseNotFound();
        }

        Restaurante r = getRestaurante(empresa);
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
        if (users.stream().anyMatch(u -> u.id == cliente && u.isDono())) {
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

    public void adicionarProduto(int numero, int produto) throws NoOpenedOrder, ProductDoesntBelongEnterprise, CannotAddProductOrderClosed {

        if (pedidos.stream().noneMatch(p -> p.numero == numero)) {
            throw new NoOpenedOrder();
        } else if(pedidos.stream().anyMatch(p -> p.numero == numero && p.estado.equals("preparando"))) {
            throw new CannotAddProductOrderClosed();
        }

        Pedido p = getPedido(numero);
        Restaurante r = getRestaurante(p.empresa);

        if (!r.produtos.stream().anyMatch(rest -> rest.numero == produto)) {
            throw new ProductDoesntBelongEnterprise();
        }

        for (Produto prod : r.produtos) {
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
                String nomeEmpresa = restaurantes.stream().filter(r -> r.id == idEmpresa).map(r -> r.nome).findFirst().orElse("Empresa não encontrada");
                return nomeEmpresa;
            default:
                throw new AtributeDontExist();
        }
//        throw new AtributeDontExist();
    }

    public void fecharPedido(int numero) throws OrderNotFound {
        if (pedidos.stream().noneMatch(p -> p.numero == numero)) {
            throw new OrderNotFound();
        }
        Pedido p = getPedido(numero);
        p.estado = "preparando";
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
}
