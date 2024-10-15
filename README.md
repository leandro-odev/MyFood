# MY FOOD
**Leandro Wanderley Quintela Tenório Cavalcante, e Caio Agra Lemos**

## 1. Descrição Geral do Design Arquitetural do Sistema

**Funcionalidade Principal:**
O sistema foi desenvolvido para gerenciar um conjunto de funcionalidades relacionadas a um sistema de pedidos, incluindo a gestão de usuários, empresas, produtos e pedidos. A principal finalidade é realizar testes de aceitação para verificar se a implementação atende aos requisitos especificados.

**Tecnologias e Frameworks Utilizados:**
- **XML**: Utilizado para armazenar dados sobre usuários, empresas, podendo serem restaurantes, mercado ou farmacia, pedidos e entregas.
- **Bibliotecas utilizadas**: Utilizadas apenas as bibliotecas padrões do Java.

## 2. Principais Componentes e Suas Interações

**Principais Componentes:**
1. **Facade**: Classe que fornece uma interface simples para a lógica de gerenciamento, delegando as chamadas para o Sistema.
2. **Sistema**: Implementa o padrão Singleton e contém todas as funcionalidades principais, como o gerenciamento de usuários, restaurantes, produtos e pedidos.
3. **User**: Classe base para os usuários do sistema, com subclasses **Cliente**, **Dono** e **Entregador** .
4. **Enterprise**: Classe base para as empresas do sistema, com subclasses **Fármacia**, **Mercado** e **Restaurante**.
5. **Entrega**: Representa uma entrega de um pedido, incluindo informações sobre o entregador e o status da entrega.
6. **Pedido**: Representa um pedido feito por um cliente em um restaurante.
7. **Produto**: Representa um produto disponível em um restaurante.
8. **XMLUtils**: Classe utilitária para ler e salvar dados em arquivos XML.
9. **Exceções**: Conjunto de classes que estendem `Exception` para tratar erros específicos.

**Interações Entre Componentes:**
- O **Sistema**, como Singleton, centraliza todas as operações relacionadas à gestão de usuários, restaurantes, produtos e pedidos.
- A **Facade** atua como um intermediário, chamando os métodos do **Sistema** de forma organizada e simplificada. Por exemplo, ao criar um novo usuário, a **Facade** simplesmente chama `sistema.criarUsuario()`, delegando a responsabilidade ao Sistema.
- **XMLUtils** é utilizado pelo **Sistema** para carregar e salvar dados em XML.
- O **Sistema** manipula objetos de **User**, **Enterprise**, **Entrega** e **Pedido**, enquanto a **Facade** simplifica o acesso a essas funcionalidades para os clientes do sistema.

## 3. Padrões de Projeto Adotados

### 3.1. Singleton
- **Descrição Geral**: Garante que apenas uma instância do **Sistema** exista durante a execução do programa, controlando seu ciclo de vida.
- **Problema Resolvido**: Evita múltiplas instâncias da classe principal do sistema, garantindo consistência e eficiência nas operações.
- **Identificação da Oportunidade**: Durante o desenvolvimento, foi observado que várias funcionalidades do sistema, como o gerenciamento de usuários, pedidos, entregas e empresas, precisavam ser acessadas globalmente e manter um estado centralizado. Usar múltiplas instâncias para controlar essas operações poderia resultar em inconsistências e complicar o gerenciamento de recursos. O padrão Singleton foi escolhido para garantir que a classe Sistema fosse instanciada apenas uma vez e usada de forma consistente por todo o sistema.
- **Aplicação no Projeto**: A classe **Sistema** implementa o padrão Singleton, centralizando todas as funcionalidades e sendo acessada por toda a aplicação por uma única instância.

### 3.2. Facade

- **Descrição Geral**: Fornece uma interface simplificada para um conjunto de operações complexas.
- **Problema Resolvido**: Simplifica a interação com o sistema ao delegar as responsabilidades para o Sistema.
- **Identificação da Oportunidade**: Com a adição da classe **Sistema**, que contém todas as funcionalidades principais, a interface direta com essa classe poderia se tornar complexa para clientes externos. O padrão **Facade** foi implementado para simplificar essas interações, fornecendo uma interface limpa e organizada. A **Facade** evita que os clientes precisem conhecer os detalhes internos do **Sistema**, promovendo um acoplamento fraco e facilita a manutenção.
- **Aplicação no Projeto**: A classe **Facade** atua como um intermediário, chamando as funções da classe **Sistema** para realizar as operações necessárias.

## Conclusão

O sistema demonstrado é bem estruturado para o gerenciamento de um sistema de pedidos, com uma arquitetura que utiliza o **Facade** para simplificar a interação com o **Sistema** e o padrão **Singleton** para garantir que apenas uma instância do **Sistema** exista.