# MY FOOD
**Leandro Wanderley Quintela Tenório Cavalcante, e Caio Agra Lemos**

## 1. Descrição Geral do Design Arquitetural do Sistema

**Funcionalidade Principal:**
O sistema foi desenvolvido para gerenciar um conjunto de funcionalidades relacionadas a um sistema de pedidos, incluindo a gestão de usuários, restaurantes, produtos e pedidos. A principal finalidade é realizar testes de aceitação para verificar se a implementação atende aos requisitos especificados.

**Tecnologias e Frameworks Utilizados:**
- **XML**: Utilizado para armazenar dados sobre usuários, restaurantes e pedidos.
- **Bibliotecas Padrão do Java**: Fornecem as funcionalidades essenciais necessárias para manipulação de arquivos e exceções.

## 2. Principais Componentes e Suas Interações

**Principais Componentes:**
1. **Facade**: Classe principal que encapsula a lógica de gerenciamento do sistema.
2. **User**: Classe base para os usuários do sistema, com subclasses **Cliente** e **Dono**.
3. **Restaurante**: Representa um restaurante com produtos e informações associadas.
4. **Pedido**: Representa um pedido feito por um cliente em um restaurante.
5. **Produto**: Representa um produto disponível em um restaurante.
6. **XMLUtils**: Classe utilitária para ler e salvar dados em arquivos XML.
7. **Exceções**: Conjunto de classes que estendem `Exception` para tratar erros específicos.

**Interações Entre Componentes:**
- O **Facade** coordena as operações e interage com as classes **User**, **Restaurante**, **Pedido**, e **Produto**.
- **XMLUtils** é utilizado pelo **Facade** para carregar e salvar dados em XML.
- O **Facade** manipula objetos de **User**, **Restaurante**, e **Pedido** e lida com exceções específicas através de suas próprias classes de exceção.

## 3. Padrões de Projeto Adotados

### 3.1. Facade

- **Descrição Geral**: Fornece uma interface simplificada para um conjunto de interfaces.
- **Problema Resolvido**: Simplifica a interação com um sistema complexo.
- **Identificação da Oportunidade**: Centralizar operações de gerenciamento.
- **Aplicação no Projeto**: A classe **Facade** já atua como um exemplo claro deste padrão, simplificando a interação com o sistema.


## Conclusão

O sistema demonstrado é bem estruturado para o gerenciamento de um sistema de pedidos, com uma arquitetura que utiliza o **Facade** para centralizar o controle das operações.