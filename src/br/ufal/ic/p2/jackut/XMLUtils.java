package br.ufal.ic.p2.jackut;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class XMLUtils {
    public static void salvarUsuarios(List<User> users, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            writer.write("<users>\n");

            for (User user : users) {
                if (user instanceof Cliente) {
                    Cliente cliente = (Cliente) user;
                    writer.write("    <cliente>\n");
                    writer.write("        <id>" + cliente.id + "</id>\n");
                    writer.write("        <nome>" + cliente.nome + "</nome>\n");
                    writer.write("        <email>" + cliente.email + "</email>\n");
                    writer.write("        <senha>" + cliente.senha + "</senha>\n");
                    writer.write("        <endereco>" + cliente.endereco + "</endereco>\n");
                    writer.write("    </cliente>\n");
                } else if (user instanceof Dono) {
                    Dono dono = (Dono) user;
                    writer.write("    <dono>\n");
                    writer.write("        <id>" + dono.id + "</id>\n");
                    writer.write("        <nome>" + dono.nome + "</nome>\n");
                    writer.write("        <email>" + dono.email + "</email>\n");
                    writer.write("        <senha>" + dono.senha + "</senha>\n");
                    writer.write("        <cpf>" + dono.cpf + "</cpf>\n");
                    writer.write("        <endereco>" + dono.endereco + "</endereco>\n");
                    writer.write("    </dono>\n");
                }
            }

            writer.write("</users>\n");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void salvarRestaurantes(List<Restaurante> restaurantes, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            writer.write("<restaurantes>\n");

            for (Restaurante restaurante : restaurantes) {
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

                writer.write("    </restaurante>\n");
            }

            writer.write("</restaurantes>\n");

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

    public static void salvarMercados(List<Mercado> mercados, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            writer.write("<mercados>\n");

            for (Mercado mercado : mercados) {
                writer.write("    <mercado>\n");
                writer.write("        <id>" + mercado.id + "</id>\n");
                writer.write("        <dono>" + mercado.dono + "</dono>\n");
                writer.write("        <nome>" + mercado.nome + "</nome>\n");
                writer.write("        <endereco>" + mercado.endereco + "</endereco>\n");
                writer.write("        <abre>" + mercado.abre + "</abre>\n");
                writer.write("        <fecha>" + mercado.fecha + "</fecha>\n");
                writer.write("        <tipoMercado>" + mercado.tipoMercado + "</tipoMercado>\n");

                writer.write("    </mercado>\n");
            }

            writer.write("</mercados>\n");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static List<User> lerUsuarios(String fileName) {
        List<User> users = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            User user = null;
            while ((line = reader.readLine()) != null) {
                line = line.trim();

                if (line.startsWith("<cliente>")) {
                    user = new Cliente(null, null, null, null);
                } else if (line.startsWith("<dono>")) {
                    user = new Dono(null, null, null, null, null);
                } else if (line.startsWith("<id>")) {
                    user.id = Integer.parseInt(line.replaceAll("<.*?>", ""));
                } else if (line.startsWith("<nome>")) {
                    user.nome = line.replaceAll("<.*?>", "");
                } else if (line.startsWith("<email>")) {
                    user.email = line.replaceAll("<.*?>", "");
                } else if (line.startsWith("<senha>")) {
                    user.senha = line.replaceAll("<.*?>", "");
                } else if (line.startsWith("<endereco>")) {
                    if (user instanceof Cliente) {
                        ((Cliente) user).endereco = line.replaceAll("<.*?>", "");
                    } else if (user instanceof Dono) {
                        ((Dono) user).endereco = line.replaceAll("<.*?>", "");
                    }
                } else if (line.startsWith("<cpf>")) {
                    if (user instanceof Dono) {
                        ((Dono) user).cpf = line.replaceAll("<.*?>", "");
                    }
                } else if (line.startsWith("</cliente>") || line.startsWith("</dono>")) {
                    users.add(user);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }

    public static List<Restaurante> lerRestaurantes(String fileName) {
        List<Restaurante> restaurantes = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            Restaurante restaurante = null;
            Produto produto = null;
            while ((line = reader.readLine()) != null) {
                line = line.trim();

                if (line.startsWith("<restaurante>")) {
                    restaurante = new Restaurante(null, null, null, 0);
                    restaurante.produtos = new ArrayList<>(); // Inicializar a lista de produtos
                } else if (line.startsWith("<id>")) {
                    restaurante.id = Integer.parseInt(line.replaceAll("<.*?>", ""));
                } else if (line.startsWith("<nome>") && produto == null) {  // Verifica se � nome do restaurante
                    restaurante.nome = line.replaceAll("<.*?>", "");
                } else if (line.startsWith("<endereco>")) {
                    restaurante.endereco = line.replaceAll("<.*?>", "");
                } else if (line.startsWith("<tipoCozinha>")) {
                    restaurante.tipoCozinha = line.replaceAll("<.*?>", "");
                } else if (line.startsWith("<idDono>")) {
                    restaurante.idDono = Integer.parseInt(line.replaceAll("<.*?>", ""));
                } else if (line.startsWith("<produto>")) {
                    produto = new Produto(null, 0, null);
                } else if (line.startsWith("<numero>")) {
                    produto.numero = Integer.parseInt(line.replaceAll("<.*?>", ""));
                } else if (line.startsWith("<nome>") && produto != null) {  // Verifica se � nome do produto
                    produto.nome = line.replaceAll("<.*?>", "");
                } else if (line.startsWith("<valor>")) {
                    produto.valor = Float.parseFloat(line.replaceAll("<.*?>", ""));
                } else if (line.startsWith("<categoria>")) {
                    produto.categoria = line.replaceAll("<.*?>", "");
                } else if (line.startsWith("</produto>")) {
                    restaurante.produtos.add(produto);
                    produto = null;  // Reseta o produto ap�s adicion�-lo
                } else if (line.startsWith("</restaurante>")) {
                    restaurantes.add(restaurante);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return restaurantes;
    }

    public static List<Pedido> lerPedidos(String fileName) {
        List<Pedido> pedidos = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            Pedido pedido = null;
            Produto produto = null;
            while ((line = reader.readLine()) != null) {
                line = line.trim();

                if (line.startsWith("<pedido>")) {
                    pedido = new Pedido(0, 0);
                } else if (line.startsWith("<id>")) {
                    pedido.numero = Integer.parseInt(line.replaceAll("<.*?>", ""));
                } else if (line.startsWith("<estado>")) {
                    pedido.estado = line.replaceAll("<.*?>", "");
                } else if (line.startsWith("<idCliente>")) {
                    pedido.cliente = Integer.parseInt(line.replaceAll("<.*?>", ""));
                } else if (line.startsWith("<idRestaurante>")) {
                    pedido.empresa = Integer.parseInt(line.replaceAll("<.*?>", ""));
                } else if (line.startsWith("<produto>")) {
                    produto = new Produto(null, 0, null);
                } else if (line.startsWith("<numero>")) {
                    produto.numero = Integer.parseInt(line.replaceAll("<.*?>", ""));
                } else if (line.startsWith("<nome>")) {
                    produto.nome = line.replaceAll("<.*?>", "");
                } else if (line.startsWith("<valor>")) {
                    produto.valor = Float.parseFloat(line.replaceAll("<.*?>", ""));
                } else if (line.startsWith("<categoria>")) {
                    produto.categoria = line.replaceAll("<.*?>", "");
                } else if (line.startsWith("</produto>")) {
                    pedido.produtos.add(produto);
                } else if (line.startsWith("</pedido>")) {
                    pedidos.add(pedido);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pedidos;
    }

    public static List<Mercado> lerMercados(String fileName) {
        List<Mercado> mercados = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            Mercado mercado = null;
            Produto produto = null;
            while ((line = reader.readLine()) != null) {
                line = line.trim();

                if (line.startsWith("<mercado>")) {
                    mercado = new Mercado(0, null, null, null, null, null);
                } else if (line.startsWith("<id>")) {
                    mercado.id = Integer.parseInt(line.replaceAll("<.*?>", ""));
                } else if (line.startsWith("<nome>")) {
                    mercado.nome = line.replaceAll("<.*?>", "");
                } else if (line.startsWith("<endereco>")) {
                    mercado.endereco = line.replaceAll("<.*?>", "");
                } else if (line.startsWith("<abre>")) {
                    mercado.abre = line.replaceAll("<.*?>", "");
                } else if (line.startsWith("<fecha>")) {
                    mercado.fecha = line.replaceAll("<.*?>", "");
                } else if (line.startsWith("<tipoMercado>")) {
                    mercado.tipoMercado = line.replaceAll("<.*?>", "");
                } else if (line.startsWith("<produto>")) {
                    produto = new Produto(null, 0, null);
                } else if (line.startsWith("<numero>")) {
                    produto.numero = Integer.parseInt(line.replaceAll("<.*?>", ""));
                } else if (line.startsWith("<nome>") && produto != null) {  // Verifica se � nome do produto
                    produto.nome = line.replaceAll("<.*?>", "");
                } else if (line.startsWith("<valor>")) {
                    produto.valor = Float.parseFloat(line.replaceAll("<.*?>", ""));
                } else if (line.startsWith("<categoria>")) {
                    produto.categoria = line.replaceAll("<.*?>", "");
                } else if (line.startsWith("</produto>")) {
                    mercado.produtos.add(produto);
                    produto = null;  // Reseta o produto ap�s adicion�-lo
                }  else if (line.startsWith("</mercado>")) {
                    mercados.add(mercado);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mercados;
    }
}