package br.ufal.ic.p2.jackut;

import br.ufal.ic.p2.jackut.Enterprises.Enterprise;
import br.ufal.ic.p2.jackut.Enterprises.Farmacia;
import br.ufal.ic.p2.jackut.Enterprises.Mercado;
import br.ufal.ic.p2.jackut.Enterprises.Restaurante;
import br.ufal.ic.p2.jackut.Users.Cliente;
import br.ufal.ic.p2.jackut.Users.Dono;
import br.ufal.ic.p2.jackut.Users.Entregador;
import br.ufal.ic.p2.jackut.Users.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class XMLUtils {
    public static void salvarUsuarios(List<User> users, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            writer.write("<users>\n");

            for (User user : users) {
                if (user.isWhatType().equals("Cliente")) {
                    Cliente cliente = (Cliente) user;
                    writer.write("    <cliente>\n");
                    writer.write("        <id>" + cliente.id + "</id>\n");
                    writer.write("        <nome>" + cliente.nome + "</nome>\n");
                    writer.write("        <email>" + cliente.email + "</email>\n");
                    writer.write("        <senha>" + cliente.senha + "</senha>\n");
                    writer.write("        <endereco>" + cliente.endereco + "</endereco>\n");
                    writer.write("    </cliente>\n");
                } else if (user.isWhatType().equals("Dono")) {
                    Dono dono = (Dono) user;
                    writer.write("    <dono>\n");
                    writer.write("        <id>" + dono.id + "</id>\n");
                    writer.write("        <nome>" + dono.nome + "</nome>\n");
                    writer.write("        <email>" + dono.email + "</email>\n");
                    writer.write("        <senha>" + dono.senha + "</senha>\n");
                    writer.write("        <cpf>" + dono.cpf + "</cpf>\n");
                    writer.write("        <endereco>" + dono.endereco + "</endereco>\n");
                    writer.write("    </dono>\n");
                } else if (user.isWhatType().equals("Entregador")) {
                    Entregador entregador = (Entregador) user;
                    writer.write("    <entregador>\n");
                    writer.write("        <id>" + entregador.id + "</id>\n");
                    writer.write("        <nome>" + entregador.nome + "</nome>\n");
                    writer.write("        <email>" + entregador.email + "</email>\n");
                    writer.write("        <senha>" + entregador.senha + "</senha>\n");
                    writer.write("        <endereco>" + entregador.endereco + "</endereco>\n");
                    writer.write("        <veiculo>" + entregador.veiculo + "</veiculo>\n");
                    writer.write("        <placa>" + entregador.placa + "</placa>\n");
                    writer.write("        <empresas>\n");
                    for (int empresa : entregador.empresas) {
                        writer.write("            <empresa>" + empresa + "</empresa>\n");
                    }
                    writer.write("        </empresas>\n");
                    writer.write("        <ocupado>" + entregador.ocupado + "</ocupado>\n");
                    writer.write("    </entregador>\n");
                }
            }

            writer.write("</users>\n");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void salvarEmpresas(List<Enterprise> empresas, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            writer.write("<empresas>\n");

            for (Enterprise empresa : empresas) {
                if (empresa.isWhatType().equals("Mercado")) {
                    Mercado mercado = (Mercado) empresa;
                    writer.write("    <mercado>\n");
                    writer.write("        <id>" + mercado.id + "</id>\n");
                    writer.write("        <idDono>" + mercado.idDono + "</idDono>\n");
                    writer.write("        <nome>" + mercado.nome + "</nome>\n");
                    writer.write("        <endereco>" + mercado.endereco + "</endereco>\n");
                    writer.write("        <produtos>\n");
                    for (Produto produto : mercado.produtos) {
                        writer.write("            <produto>\n");
                        writer.write("                <numero>" + produto.numero + "</numero>\n");
                        writer.write("                <nome>" + produto.nome + "</nome>\n");
                        writer.write("                <valor>" + produto.valor + "</valor>\n");
                        writer.write("                <categoria>" + produto.categoria + "</categoria>\n");
                        writer.write("            </produto>\n");
                    }
                    writer.write("        </produtos>\n");
                    writer.write("        <entregadores>\n");
                    for (int entregador : mercado.entregadores) {
                        writer.write("            <entregador>" + entregador + "</entregador>\n");
                    }
                    writer.write("        </entregadores>\n");
                    writer.write("        <abre>" + mercado.abre + "</abre>\n");
                    writer.write("        <fecha>" + mercado.fecha + "</fecha>\n");
                    writer.write("        <tipoMercado>" + mercado.tipoMercado + "</tipoMercado>\n");
                    writer.write("    </mercado>\n");
                } else if (empresa.isWhatType().equals("Farmacia")) {
                    Farmacia farmacia = (Farmacia) empresa;
                    writer.write("    <farmacia>\n");
                    writer.write("        <id>" + farmacia.id + "</id>\n");
                    writer.write("        <idDono>" + farmacia.idDono + "</idDono>\n");
                    writer.write("        <nome>" + farmacia.nome + "</nome>\n");
                    writer.write("        <endereco>" + farmacia.endereco + "</endereco>\n");
                    writer.write("        <produtos>\n");
                    for (Produto produto : farmacia.produtos) {
                        writer.write("            <produto>\n");
                        writer.write("                <numero>" + produto.numero + "</numero>\n");
                        writer.write("                <nome>" + produto.nome + "</nome>\n");
                        writer.write("                <valor>" + produto.valor + "</valor>\n");
                        writer.write("                <categoria>" + produto.categoria + "</categoria>\n");
                        writer.write("            </produto>\n");
                    }
                    writer.write("        <entregadores>\n");
                    for (int entregador : farmacia.entregadores) {
                        writer.write("            <entregador>" + entregador + "</entregador>\n");
                    }
                    writer.write("        </entregadores>\n");
                    writer.write("        </produtos>\n");
                    writer.write("        <aberto24horas>" + farmacia.aberto24horas + "</aberto24horas>\n");
                    writer.write("        <numeroFuncionarios>" + farmacia.numeroFuncionarios + "</numeroFuncionarios>\n");
                    writer.write("    </farmacia>\n");
                } else if (empresa.isWhatType().equals("Restaurante")) {
                    Restaurante restaurante = (Restaurante) empresa;
                    writer.write("    <restaurante>\n");
                    writer.write("        <id>" + restaurante.id + "</id>\n");
                    writer.write("        <nome>" + restaurante.nome + "</nome>\n");
                    writer.write("        <endereco>" + restaurante.endereco + "</endereco>\n");
                    writer.write("        <tipoCozinha>" + restaurante.tipoCozinha + "</tipoCozinha>\n");
                    writer.write("        <idDono>" + restaurante.idDono + "</idDono>\n");
                    writer.write("        <produtos>\n");
                    for (Produto produto : restaurante.produtos) {
                        writer.write("            <produto>\n");
                        writer.write("                <numero>" + produto.numero + "</numero>\n");
                        writer.write("                <nome>" + produto.nome + "</nome>\n");
                        writer.write("                <valor>" + produto.valor + "</valor>\n");
                        writer.write("                <categoria>" + produto.categoria + "</categoria>\n");
                        writer.write("            </produto>\n");
                    }
                    writer.write("        </produtos>\n");
                    writer.write("        <entregadores>\n");
                    for (int entregador : restaurante.entregadores) {
                        writer.write("            <entregador>" + entregador + "</entregador>\n");
                    }
                    writer.write("        </entregadores>\n");

                    writer.write("    </restaurante>\n");
                }

                writer.write("</restaurantes>\n");
                }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void salvarPedidos(List<Pedido> pedidos, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            writer.write("<pedidos>\n");

            for (Pedido pedido : pedidos) {
                writer.write("    <pedido>\n");
                writer.write("        <id>" + pedido.numero + "</id>\n");
                writer.write("        <idCliente>" + pedido.cliente + "</idCliente>\n");
                writer.write("        <idRestaurante>" + pedido.empresa + "</idRestaurante>\n");
                writer.write("        <estado>" + pedido.estado + "</estado>\n");

                writer.write("        <produtos>\n");
                for (Produto produto : pedido.produtos) {
                    writer.write("            <produto>\n");
                    writer.write("                <numero>" + produto.numero + "</numero>\n");
                    writer.write("                <nome>" + produto.nome + "</nome>\n");
                    writer.write("                <valor>" + produto.valor + "</valor>\n");
                    writer.write("                <categoria>" + produto.categoria + "</categoria>\n");
                    writer.write("            </produto>\n");
                }
                writer.write("        </produtos>\n");

                writer.write("    </pedido>\n");
            }

            writer.write("</pedidos>\n");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<User> lerUsuarios(String fileName) {
        List<User> usuarios = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            User usuario = null;

            while ((line = reader.readLine()) != null) {
                line = line.trim();

                if (line.startsWith("<cliente>")) {
                    usuario = new Cliente("", null, null, null);
                } else if (line.startsWith("<dono>")) {
                    usuario = new Dono("", null, null, null, null);
                } else if (line.startsWith("<entregador>")) {
                    usuario = new Entregador("", null, null, null, null, null);
                } else if (line.startsWith("<id>")) {
                    usuario.id = Integer.parseInt(line.replaceAll("<.*?>", ""));
                } else if (line.startsWith("<nome>")) {
                    usuario.nome = line.replaceAll("<.*?>", "");
                } else if (line.startsWith("<email>")) {
                    usuario.email = line.replaceAll("<.*?>", "");
                } else if (line.startsWith("<senha>")) {
                    usuario.senha = line.replaceAll("<.*?>", "");
                } else if (usuario instanceof Cliente && line.startsWith("<endereco>")) {
                    ((Cliente) usuario).endereco = line.replaceAll("<.*?>", "");
                } else if (usuario instanceof Dono && line.startsWith("<cpf>")) {
                    ((Dono) usuario).cpf = line.replaceAll("<.*?>", "");
                } else if (usuario instanceof Dono && line.startsWith("<endereco>")) {
                    ((Dono) usuario).endereco = line.replaceAll("<.*?>", "");
                } else if (usuario instanceof Entregador && line.startsWith("<veiculo>")) {
                    ((Entregador) usuario).veiculo = line.replaceAll("<.*?>", "");
                } else if (usuario instanceof Entregador && line.startsWith("<placa>")) {
                    ((Entregador) usuario).placa = line.replaceAll("<.*?>", "");
                } else if (usuario instanceof Entregador && line.startsWith("<empresas>")) {
                    List<Integer> empresas = new ArrayList<>();
                    while (!(line = reader.readLine().trim()).startsWith("</empresas>")) {
                        int empresaId = Integer.parseInt(line.replaceAll("<.*?>", ""));
                        empresas.add(empresaId);
                    }
                    ((Entregador) usuario).empresas = empresas.stream().mapToInt(i -> i).boxed().collect(Collectors.toCollection(ArrayList::new));
                } else if (usuario instanceof Entregador && line.startsWith("<ocupado>")) {
                    ((Entregador) usuario).ocupado = Boolean.parseBoolean(line.replaceAll("<.*?>", ""));
                } else if (line.startsWith("</cliente>") || line.startsWith("</dono>") || line.startsWith("</entregador>")) {
                    usuarios.add(usuario);  // Adiciona o usuário à lista
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return usuarios;
    }

    public static List<Enterprise> lerEmpresas(String fileName) {
        List<Enterprise> empresas = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            Enterprise empresa = null;

            while ((line = reader.readLine()) != null) {
                line = line.trim();

                if (line.startsWith("<mercado>")) {
                    empresa = new Mercado(0, null, null, null, null, null);
                } else if (line.startsWith("<farmacia>")) {
                    empresa = new Farmacia(0, null, null, null, null);
                } else if (line.startsWith("<restaurante>")) {
                    empresa = new Restaurante(null, null, null, 0);
                } else if (line.startsWith("<id>")) {
                    empresa.id = Integer.parseInt(line.replaceAll("<.*?>", ""));
                } else if (line.startsWith("<idDono>")) {
                    empresa.idDono = Integer.parseInt(line.replaceAll("<.*?>", ""));
                } else if (line.startsWith("<nome>")) {
                    empresa.nome = line.replaceAll("<.*?>", "");
                } else if (line.startsWith("<endereco>")) {
                    empresa.endereco = line.replaceAll("<.*?>", "");
                } else if (empresa instanceof Mercado && line.startsWith("<abre>")) {
                    ((Mercado) empresa).abre = line.replaceAll("<.*?>", "");
                } else if (empresa instanceof Mercado && line.startsWith("<fecha>")) {
                    ((Mercado) empresa).fecha = line.replaceAll("<.*?>", "");
                } else if (empresa instanceof Mercado && line.startsWith("<tipoMercado>")) {
                    ((Mercado) empresa).tipoMercado = line.replaceAll("<.*?>", "");
                } else if (empresa instanceof Farmacia && line.startsWith("<aberto24horas>")) {
                    ((Farmacia) empresa).aberto24horas = Boolean.parseBoolean(line.replaceAll("<.*?>", ""));
                } else if (empresa instanceof Farmacia && line.startsWith("<numeroFuncionarios>")) {
                    ((Farmacia) empresa).numeroFuncionarios = Integer.parseInt(line.replaceAll("<.*?>", ""));
                } else if (empresa != null && line.startsWith("<produtos>")) {
                    List<Produto> produtos = new ArrayList<>();
                    while (!(line = reader.readLine().trim()).startsWith("</produtos>")) {
                        if (line.startsWith("<produto>")) {
                            Produto produto = new Produto("", 0, "");
                            while (!(line = reader.readLine().trim()).startsWith("</produto>")) {
                                if (line.startsWith("<numero>")) {
                                    produto.numero = Integer.parseInt(line.replaceAll("<.*?>", ""));
                                } else if (line.startsWith("<nome>")) {
                                    produto.nome = line.replaceAll("<.*?>", "");
                                } else if (line.startsWith("<valor>")) {
                                    produto.valor = Float.parseFloat(line.replaceAll("<.*?>", ""));
                                } else if (line.startsWith("<categoria>")) {
                                    produto.categoria = line.replaceAll("<.*?>", "");
                                }
                            }
                            produtos.add(produto);
                        }
                    }
                    empresa.produtos = produtos;
                } else if (empresa != null && line.startsWith("<entregadores>")) {
                    List<Integer> entregadores = new ArrayList<>();
                    while (!(line = reader.readLine().trim()).startsWith("</entregadores>")) {
                        int entregadorId = Integer.parseInt(line.replaceAll("<.*?>", ""));
                        entregadores.add(entregadorId);
                    }
                    empresa.entregadores = entregadores;
                } else if (line.startsWith("</mercado>") || line.startsWith("</farmacia>") || line.startsWith("</restaurante>")) {
                    empresas.add(empresa);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return empresas;
    }

    public static List<Pedido> lerPedidos(String fileName) {
        List<Pedido> pedidos = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            Pedido pedido = null;

            while ((line = reader.readLine()) != null) {
                line = line.trim();

                if (line.startsWith("<pedido>")) {
                    pedido = new Pedido(0, 0);
                } else if (line.startsWith("<id>")) {
                    pedido.numero = Integer.parseInt(line.replaceAll("<.*?>", ""));
                } else if (line.startsWith("<idCliente>")) {
                    pedido.cliente = Integer.parseInt(line.replaceAll("<.*?>", ""));
                } else if (line.startsWith("<idRestaurante>")) {
                    pedido.empresa = Integer.parseInt(line.replaceAll("<.*?>", ""));
                } else if (line.startsWith("<estado>")) {
                    pedido.estado = line.replaceAll("<.*?>", "");
                } else if (line.startsWith("<produtos>")) {
                    ArrayList<Produto> produtos = new ArrayList<>();
                    while (!(line = reader.readLine().trim()).startsWith("</produtos>")) {
                        if (line.startsWith("<produto>")) {
                            Produto produto = new Produto(null, 0, null);
                            while (!(line = reader.readLine().trim()).startsWith("</produto>")) {
                                if (line.startsWith("<numero>")) {
                                    produto.numero = Integer.parseInt(line.replaceAll("<.*?>", ""));
                                } else if (line.startsWith("<nome>")) {
                                    produto.nome = line.replaceAll("<.*?>", "");
                                } else if (line.startsWith("<valor>")) {
                                    produto.valor = Float.parseFloat(line.replaceAll("<.*?>", ""));
                                } else if (line.startsWith("<categoria>")) {
                                    produto.categoria = line.replaceAll("<.*?>", "");
                                }
                            }
                            produtos.add(produto);
                        }
                    }
                    pedido.produtos = produtos;
                } else if (line.startsWith("</pedido>")) {
                    pedidos.add(pedido);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pedidos;
    }

}