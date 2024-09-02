# MY FOOD
# Leandro Wanderley Quintela Tenório Cavalcante
# Caio Agra Lemos

## 1. Descrição Geral do Design Arquitetural do Sistema

**Funcionalidade Principal:**
O sistema foi desenvolvido para gerenciar um conjunto de funcionalidades relacionadas a um sistema de pedidos, incluindo a gestão de usuários, restaurantes, produtos e pedidos. A principal finalidade é realizar testes de aceitação para verificar se a implementação atende aos requisitos especificados.

**Tecnologias e Frameworks Utilizados:**
- **XML**: Utilizado para armazenar dados sobre usuários, restaurantes e pedidos.
- **Bibliotecas Padrão do Java**: Fornecem as funcionalidades essenciais necessárias para manipulação de arquivos, coleções e exceções.

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

**Diagramas ou Descrições das Interações:**
- Não há diagramas fornecidos, mas a interação principal é o **Facade** controlando as operações e utilizando **XMLUtils** para persistência de dados.

## 3. Padrões de Projeto Adotados

### 3.1. Singleton

- **Descrição Geral**: O padrão Singleton assegura que uma classe tenha uma única instância e fornece um ponto de acesso global a essa instância.
- **Problema Resolvido**: Evita a criação de múltiplas instâncias de uma classe, o que pode ser útil para gerenciar recursos compartilhados ou configuração centralizada.
- **Identificação da Oportunidade**: Embora não explicitamente usado no código fornecido, o padrão Singleton poderia ser útil para garantir que o **Facade** funcione como um único ponto de controle para todas as operações do sistema.
- **Aplicação no Projeto**: No código fornecido, não há uma aplicação clara do Singleton, mas a ideia seria garantir que a instância do **Facade** seja única e global.

### 3.2. Factory Method

- **Descrição Geral**: O padrão Factory Method define uma interface para criar um objeto, mas permite que subclasses decidam qual classe instanciar.
- **Problema Resolvido**: Permite que a criação de objetos seja desacoplada da classe que os utiliza, facilitando a manutenção e a extensibilidade.
- **Identificação da Oportunidade**: No contexto do código, poderia ser usado para criar instâncias de **Pedido**, **Restaurante** ou **Produto** com base em diferentes requisitos.
- **Aplicação no Projeto**: O código não mostra uma implementação explícita do Factory Method, mas poderia ser aplicado na criação de novos usuários e produtos, permitindo variação na criação de instâncias de acordo com a necessidade.

### 3.3. Strategy

- **Descrição Geral**: O padrão Strategy define uma família de algoritmos, encapsula cada um e os torna intercambiáveis. Permite que o algoritmo varie independentemente dos clientes que o utilizam.
- **Problema Resolvido**: Permite alterar o comportamento de uma classe sem modificar seu código, oferecendo flexibilidade na escolha do comportamento.
- **Identificação da Oportunidade**: Poderia ser útil para definir diferentes estratégias de validação ou execução de pedidos.
- **Aplicação no Projeto**: No código fornecido, a aplicação do padrão Strategy não é explícita, mas pode ser sugerida para a implementação de diferentes estratégias de validação para a criação de usuários e pedidos.

## Conclusão

O sistema demonstrado é bem estruturado para o gerenciamento de um sistema de pedidos, com uma arquitetura que utiliza o **Facade** para centralizar o controle das operações. Embora os padrões de projeto como Singleton, Factory Method e Strategy possam ser aplicáveis, a implementação atual não os demonstra explicitamente, sugerindo que a arquitetura pode ser expandida para incluir esses padrões de forma mais evidente.

Se houver mais detalhes específicos ou ajustes necessários, sinta-se à vontade para informar!
