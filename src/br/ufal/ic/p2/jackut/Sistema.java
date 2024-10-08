package br.ufal.ic.p2.jackut;

import br.ufal.ic.p2.jackut.Exceptions.*;
import br.ufal.ic.p2.jackut.Exceptions.Enterprise.*;
import br.ufal.ic.p2.jackut.Exceptions.Invalid.*;
import br.ufal.ic.p2.jackut.Exceptions.Orders.*;
import br.ufal.ic.p2.jackut.Exceptions.Products.*;

import java.io.File;
import java.util.*;

public class Sistema {

    private static Sistema instance;

    List<User> users;
    List<Restaurante> restaurantes;
    List<Mercado> mercados;
    List<Pedido> pedidos;

    private Sistema() {
        File usersFile = new File("users.xml");
        File restaurantesFile = new File("restaurantes.xml");
        File pedidosFile = new File("pedidos.xml");
        File mercadosFile = new File("mercados.xml");

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

        if (mercadosFile.exists()) {
            mercados = XMLUtils.lerMercados("mercados.xml");
        } else {
            mercados = new ArrayList<>();
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
        XMLUtils.salvarRestaurantes(restaurantes, "restaurantes.xml");
        XMLUtils.salvarMercados(mercados, "mercados.xml");
    }

    public void zerarSistema() {
        File usersFile = new File("users.xml");
        File restaurantesFile = new File("restaurantes.xml");
        File pedidosFile = new File("pedidos.xml");
        File mercadosFile = new File("mercados.xml");
        users.clear();
        restaurantes.clear();
        pedidos.clear();
        mercados.clear();
        usersFile.delete();
        restaurantesFile.delete();
        pedidosFile.delete();
        mercadosFile.delete();
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
        if (user.isDono() && atributo.equals("cpf")) {
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
        if(users.stream().noneMatch(u -> u.id == dono && u.isDono())) {
            throw new UserCantCreate();
        }

        if (tipoEmpresa.equals("restaurante")) {

            if (restaurantes.stream().anyMatch(r -> r.nome.equals(nome) && r.idDono != dono) ) {
                throw new NameAlreadyExist();
            }

            if (restaurantes.stream().anyMatch(r -> r.nome.equals(nome) && r.endereco.equals(endereco) && r.idDono == dono) ) {
                throw new NameAndAddresAlreadyExist();
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

        if (stringRestaurantes.length() == 2) {
            for (Mercado  mercado: mercados) {
                if (mercado.dono == idDono) {
                    stringRestaurantes.append("[").append(mercado.nome).append(", ").append(mercado.endereco).append("], ");
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

        List<Restaurante> restaurantesComMesmoNome = restaurantes.stream().filter(r -> r.nome.equals(nome)).toList();

        if (indice < restaurantesComMesmoNome.size() && restaurantesComMesmoNome.size() != 0) {
            return restaurantesComMesmoNome.get(indice).id;
        } else if (restaurantesComMesmoNome.size() != 0 && indice >= restaurantesComMesmoNome.size()) {
            throw new Error("Indice maior que o esperado");
        }

        List<Mercado> mercadosComMesmoNome = mercados.stream().filter(m -> m.nome.equals(nome) && m.dono == idDono).toList();

        if (indice >= 0 && indice < mercadosComMesmoNome.size() && mercadosComMesmoNome.size() != 0) {
            return mercadosComMesmoNome.get(indice).id;
        } else if (mercadosComMesmoNome.size() != 0 && indice >= mercadosComMesmoNome.size()) {
            throw new Error("Indice maior que o esperado");
        }

        throw new EnterpriseNameNotRegistered();
    }

    public String getAtributoEmpresa (int empresa, String atributo) throws InvalidAttribute, EnterpriseNotRegistered {

        Optional<Restaurante> restauranteOpt = restaurantes.stream().filter(r -> r.id == empresa).findFirst();
        if (restauranteOpt.isPresent()) {
            Restaurante restaurante = restauranteOpt.get();
            return getAtributoRestaurante(restaurante, atributo);
        }

        Optional<Mercado> mercadoOpt = mercados.stream().filter(m -> m.id == empresa).findFirst();
        if (mercadoOpt.isPresent()) {
            Mercado mercado = mercadoOpt.get();
            return getAtributoMercado(mercado, atributo);
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
                    String nomeDono = users.stream().filter(r -> r.id == mercado.dono).map(r -> r.nome).findFirst().orElse("Dono não encontrado");
                    return nomeDono;
            }

            throw new InvalidAttribute();
    }

    public int criarProduto(int empresa, String nome, float valor, String categoria) throws ProductNameAtEnterprise, InvalidName, InvalidPrice, WrongCategory, EnterpriseNotRegistered {
        if (nome == null || nome.equals("")) {
            throw new InvalidName();
        } else if (valor < 0) {
            throw new InvalidPrice();
        } else if (categoria == null || categoria.equals("")) {
            throw new WrongCategory();
        }

        Optional<Restaurante> restauranteOpt = restaurantes.stream().filter(r -> r.id == empresa).findFirst();
        Optional<Mercado> mercadoOpt = mercados.stream().filter(m -> m.id == empresa).findFirst();

        if (restauranteOpt.isPresent()) {
            Restaurante r = restauranteOpt.get();
            if(r.produtos.stream().anyMatch(p -> p.nome.equals(nome))) {
                throw new ProductNameAtEnterprise();
            }
            Produto p = new Produto(nome, valor, categoria);
            r.produtos.add(p);
            return p.numero;
        } else if (mercadoOpt.isPresent()) {
            Mercado m = mercadoOpt.get();
            if(m.produtos.stream().anyMatch(p -> p.nome.equals(nome))) {
                throw new ProductNameAtEnterprise();
            }
            Produto p = new Produto(nome, valor, categoria);
            m.produtos.add(p);
            return p.numero;
        } else {
            throw new EnterpriseNotRegistered();
        }
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

            if (users.stream().noneMatch(u -> u.id == dono && u.isDono())) {
                throw new UserCantCreate();
            }

            if (nome == null || nome.isEmpty()) {
                throw new Error("Nome invalido");
            }

            if (tipoMercado == null || tipoMercado.isEmpty()) {
                throw new Error("Tipo de mercado invalido");
            }

            if (mercados.stream().anyMatch(r -> r.nome.equals(nome) && r.dono != dono)) {
                throw new NameAlreadyExist();
            }

            if (mercados.stream().anyMatch(r -> r.nome.equals(nome) && r.endereco.equals(endereco) && r.dono == dono)) {
                throw new NameAndAddresAlreadyExist();
            }

            Mercado novoMercado = new Mercado(dono, nome, endereco, abre, fecha, tipoMercado);
            mercados.add(novoMercado);
            return novoMercado.id;
        } else {
            throw new Error("Empresa não é um mercado");
        }
    }

    public void alterarFuncionamento(int mercado, String abre, String fecha) throws EnterpriseNotRegistered {
        if (mercados.stream().noneMatch(m -> m.id == mercado)) {
            throw new EnterpriseNotRegistered();
        }

        Mercado m = mercados.stream().filter(n -> n.id == mercado).findFirst().get();
        m.abre = abre;
        m.fecha = fecha;
    }

    // Farmácia


    // Entregador
    public void criarUsuario(String nome, String email, String senha, String endereco, String veiculo, String placa) throws EmailAlreadyExist, InvalidName {
        verifyData(nome, email, senha, endereco);

        if (users.stream().anyMatch(u -> u.email.equals(email))) {
            throw new EmailAlreadyExist();
        }
        User newUser = new Entregador(nome, email, senha, endereco, veiculo, placa);
        users.add(newUser);
    }

    public List<Entregador> getEntregadores(int empresa) {
        return users.stream().anyMatch(u -> u.id == empresa && u.isDono()) ? users.stream().filter(u -> u instanceof Entregador).map(u -> (Entregador) u).toList() : new ArrayList<>();
    }


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