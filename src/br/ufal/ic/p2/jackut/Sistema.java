//package br.ufal.ic.p2.jackut;
//
//import br.ufal.ic.p2.jackut.Exceptions.EmailAlreadyExist;
//
//import java.io.File;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//public class Sistema {
//    List<User> users;
//    List<Restaurante> restaurantes;
//    List<Pedido> pedidos;
//
//    public Sistema() {
//        File usersFile = new File("users.xml");
//        File restaurantesFile = new File("restaurantes.xml");
//        File pedidosFile = new File("pedidos.xml");
//
//        if (usersFile.exists()) {
//            users = XMLUtils.lerUsuarios("users.xml");
//        } else {
//            users = new ArrayList<>();
//        }
//
//        if (restaurantesFile.exists()) {
//            restaurantes = XMLUtils.lerRestaurantes("restaurantes.xml");
//        } else {
//            restaurantes = new ArrayList<>();
//        }
//
//        if (pedidosFile.exists()) {
//            pedidos = XMLUtils.lerPedidos("pedidos.xml");
//        } else {
//            pedidos = new ArrayList<>();
//        }
//    }
//
//    public void encerrarSistema() {
//        XMLUtils.salvarUsuarios(users, "users.xml");
//        XMLUtils.salvarPedidos(pedidos, "pedidos.xml");
//        XMLUtils.salvarRestaurantes(restaurantes, "restaurantes.xml");
//    }
//
//    public void zerarSistema() {
//        File usersFile = new File("users.xml");
//        File restaurantesFile = new File("restaurantes.xml");
//        File pedidosFile = new File("pedidos.xml");
//        usersFile.delete();
//        restaurantesFile.delete();
//        pedidosFile.delete();
//    }
//
//    public User getUser(int id) {
//        try {
//            return users.stream().filter(u -> u.id == id).findFirst().get();
//        } catch (Error e) {
//            throw new Error("Não foi possivel encontrar usuário");
//        }
//    }
//
//    public Restaurante getRestaurante(int id) {
//        try {
//            return restaurantes.stream().filter(r -> r.id == id).findFirst().get();
//        } catch (Error e) {
//            throw new Error("Não foi possivel encontrar restaurante");
//        }
//    }
//
//    public Pedido getPedido(int id) {
//        try {
//            return pedidos.stream().filter(p -> p.numero == id).findFirst().get();
//        } catch (Error e) {
//            throw new Error("Não foi possivel encontrar pedido");
//        }
//    }
//
//    public String getAtributo(int id) {
//        User user = getUser(id);
//        return user.nome;
//    }
//
//    public void criarUsuario(String nome, String email, String senha, String endereco, String cpf) throws EmailAlreadyExist {
//        if (users.stream().anyMatch(u -> u.email.equals(email))) {
//            throw new EmailAlreadyExist();
//        }
//        if (users.stream().anyMatch(u -> u.cpf.equals(cpf))) {
//            throw new Error("CPF já utilizado");
//        }
//        User newUser = new Cliente(nome, email, senha, endereco, cpf);
//        users.add(newUser);
//    }
//
//    public void criarUsuario(String nome, String email, String senha, String endereco, String cpf) throws EmailAlreadyExist {
//        if (users.stream().anyMatch(u -> u.email.equals(email))) {
//            throw new EmailAlreadyExist();
//        }
//        User newUser = new Dono(nome, email, senha, endereco, cpf);
//        users.add(newUser);
//    }
//
//    public int login(String email, String senha) {
//        return users.stream().filter(u -> u.email.equals(email) && u.senha.equals(senha)).findFirst().get().id;
//    }
//
//    public int criarEmpresa(String tipoEmpresa, int dono, String nome, String endereco, String tipoCozinha) {
//        if (tipoEmpresa.equalsIgnoreCase("restaurante")) {
//            if (restaurantes.stream().anyMatch(r -> r.nome.equals(nome) || r.endereco.equals(endereco))) {
//                throw new Error("Nome ou endereço já utilizados");
//            }
//            Restaurante novoRestaurante = new Restaurante(nome, endereco, tipoCozinha, dono);
//            restaurantes.add(novoRestaurante);
//            return novoRestaurante.id;
//        } else {
//            throw new Error("Empresa não é um restaurante");
//        }
//    }
//
//    public String getEmpresasDoUsuario(int idDono) {
//        String stringRestaurantes = "";
//        for (Restaurante restaurante : restaurantes) {
//            if (restaurante.idDono == idDono) {
//                stringRestaurantes += restaurante.nome + " | " + restaurante.endereco + "\n";
//            }
//        }
//
//        if (stringRestaurantes.isBlank()) {
//            throw new Error("Esse dono não existe ou não possui restaurantes");
//        } else {
//            return stringRestaurantes;
//        }
//    }
//
//    public int getIdEmpresa (int idDono, String nome, int indice) {
//        try {
//            List<Restaurante> empresasDono = restaurantes.stream().filter(r -> r.idDono == idDono).toList();
//            Optional<Restaurante> empresaNome = Optional.of(empresasDono.stream().filter(r -> r.nome.equals(nome)).findFirst().get());
//            return empresaNome.map(restaurante -> restaurante.id).orElseGet(() -> empresasDono.get(indice).id);
//        } catch (Error e) {
//            throw new Error("Empresa nao encontrada");
//        }
//    }
//
//    public String getAtributoEmpresa (int empresa, String atributo) {
//        Restaurante restaurante = getRestaurante(empresa);
//        switch (atributo.toLowerCase()) {
//            case "id":
//                return restaurante.id + "";
//            case "nome":
//                return restaurante.nome;
//            case "endereco":
//                return restaurante.endereco;
//            case "tipocozinha":
//                return restaurante.tipoCozinha;
//            case "dono":
//                return restaurante.idDono + "";
//        }
//       throw new Error("Atributo inexistente");
//    }
//
//    public int criarProduto(int empresa, String nome, float valor, String categoria) {
//        Restaurante r = getRestaurante(empresa);
//        if (r.produtos.stream().anyMatch(p -> p.nome.equals(nome))) {
//            throw new Error("Nesse restaurante já existe um produto com esse nome");
//        }
//        Produto p = new Produto(nome, valor, categoria);
//        r.produtos.add(p);
//        return p.numero;
//    }
//
//    public void editarProduto(int produto, String nome, float valor, String categoria) {
//        for (Restaurante restaurante : restaurantes) {
//            for (Produto p : restaurante.produtos) {
//                if (p.numero == produto) {
//                    p.nome = nome;
//                    p.valor = valor;
//                    p.categoria = categoria;
//                    return;
//                }
//            }
//        }
//        throw new Error("Produto não encontrado");
//    }
//
//    public String getProduto(String nome, int empresa, String atributo) {
//        Restaurante restaurante = getRestaurante(empresa);
//        for (Produto p : restaurante.produtos) {
//            if (p.nome.equals(nome)) {
//                switch (atributo.toLowerCase()) {
//                    case "id", "produto":
//                        return p.numero + "";
//                    case "nome":
//                        return p.nome;
//                    case "valor":
//                        return p.valor + "";
//                    case "categoria":
//                        return p.categoria;
//                }
//                throw new Error("Atributo inexistente");
//            }
//        }
//        throw new Error("Algo deu errado");
//    }
//
//    public String listarProdutos(int empresa) {
//        Restaurante r = getRestaurante(empresa);
//        String stringProdutos = "";
//        for (Produto p : r.produtos) {
//            stringProdutos += p.nome + " ";
//        }
//        if (stringProdutos.isBlank()) {
//            throw new Error("Não foi possivel encontrar tais produtos");
//        } else {
//            return stringProdutos;
//        }
//    }
//
//    public int criarPedido(int cliente, int empresa) {
//        Pedido p = new Pedido(cliente, empresa);
//        pedidos.add(p);
//        return p.numero;
//    }
//
//    public int getNumeroPedido(int cliente, int empresa, int indice) {
//        try {
//            List<Pedido> pedidosCliente = pedidos.stream().filter(p -> p.empresa == empresa && p.cliente == cliente).toList();
//            return pedidosCliente.get(indice).numero;
//        } catch (Error e) {
//            throw new Error("Pedido nao encontrado");
//        }
//    }
//
//    public void adicionarProduto(int numero, int produto) {
//        Pedido p = getPedido(numero);
//        Restaurante r = getRestaurante(p.empresa);
//        for (Produto prod : r.produtos) {
//            if (prod.numero == produto) {
//                p.produtos.add(prod);
//                break;
//            }
//        }
//    }
//
//    public String getPedidos(int numero, String atributo) {
//        Pedido p = getPedido(numero);
//        switch (atributo.toLowerCase()) {
//            case "id", "numero":
//                return p.numero + "";
//            case "cliente":
//                return p.cliente + "";
//            case "produto":
//                String produtos = "";
//                for (Produto prod : p.produtos) {
//                    produtos += prod.nome + " ";
//                }
//                return produtos;
//            case "estado":
//                return p.estado;
//            case "valor", "preço", "preco":
//                return p.valor() + "";
//        }
//        throw new Error("Atributo inexistente");
//    }
//
//    public void fecharPedido(int numero) {
//        Pedido p = getPedido(numero);
//        p.estado = "fechado";
//    }
//
//    public void removerPedido(int numero) {
//        Pedido p = getPedido(numero);
//        pedidos.remove(p);
//    }
//}
