MY FOOD
Leandro Wanderley Quintela Tenório Cavalcante
Caio Agra Lemos


O sistema apresentado é estruturado de maneira modular e utiliza uma fachada (Facade) para centralizar a interação com os diferentes componentes. O propósito dessa arquitetura é facilitar o gerenciamento de usuários, restaurantes e pedidos, além de promover a persistência de dados por meio de arquivos XML. Os componentes principais incluem as classes para Usuários, Restaurantes, Pedidos e a própria Fachada que encapsula toda a lógica do sistema e fornece uma interface simplificada para o cliente.
Principais componentes e suas interações

    Facade: Centraliza a lógica do sistema, atuando como uma interface única para os diversos componentes. Ela encapsula a criação, modificação e remoção de usuários, restaurantes e pedidos. Além disso, coordena a persistência desses dados, gravando e lendo informações de arquivos XML.

    User: Representa os usuários do sistema, que podem ser do tipo Cliente ou Dono (proprietário de restaurante). O sistema permite a criação de usuários com diferentes permissões e atributos específicos, como CPF para donos.

    Restaurante: Classe que modela os restaurantes criados pelos usuários do tipo Dono. Inclui detalhes como nome, endereço, tipo de cozinha e os produtos oferecidos.

    Pedido: Modela os pedidos feitos pelos clientes em restaurantes. Cada pedido está associado a um cliente e um restaurante e pode conter múltiplos produtos.

    Produto: Representa os produtos que são oferecidos pelos restaurantes, incluindo atributos como nome, preço e categoria.

Esses componentes interagem entre si através da Facade, que é responsável por gerenciar as regras de negócios e garantir a integridade das operações, como a criação de pedidos, adição de produtos, e o gerenciamento de usuários e empresas.
Padrões de Projeto
1. Padrão de Projeto: Facade

    Descrição Geral: O padrão Facade visa fornecer uma interface simplificada para interações com sistemas complexos. Ele oculta as complexidades internas dos componentes e apresenta uma única interface para o cliente, tornando a interação com o sistema mais intuitiva e fácil.

    Problema Resolvido: Em sistemas grandes e complexos, pode ser difícil para os clientes interagirem diretamente com todos os componentes. A complexidade aumenta à medida que o sistema cresce. O padrão Facade resolve esse problema oferecendo uma interface única que consolida as interações, facilitando o uso.

    Identificação da Oportunidade: A necessidade de uma interface simplificada surgiu no contexto de um sistema que gerencia múltiplos componentes interdependentes, como usuários, restaurantes e pedidos. Implementar todas as interações diretamente nos componentes aumentaria o acoplamento e a complexidade.

    Aplicação no Projeto: No projeto, a classe Facade serve como ponto de entrada para todas as funcionalidades. Em vez de o cliente interagir diretamente com as classes de User, Restaurante, Pedido e outras, ele interage com a Facade, que encapsula essas interações. Por exemplo, a criação de um usuário ou restaurante é feita através de métodos da Facade, que internamente chama os métodos necessários para manipular os componentes adequados.

2. Padrão de Projeto: Singleton

    Descrição Geral: O padrão Singleton garante que uma classe tenha apenas uma única instância ao longo do ciclo de vida do sistema e fornece um ponto global de acesso a essa instância.

    Problema Resolvido: Em certos cenários, é necessário garantir que haja apenas uma instância de uma classe em execução. Isso é particularmente importante para a gestão de recursos compartilhados, como o estado de um sistema ou um cache central.

    Identificação da Oportunidade: O Singleton foi identificado como uma solução ideal para a gestão de persistência de dados e controle centralizado do estado do sistema, garantindo que a classe Facade possa ser acessada de maneira uniforme em todo o sistema sem criar múltiplas instâncias que poderiam gerar inconsistências.

    Aplicação no Projeto: Embora o código fornecido não aplique diretamente o padrão Singleton, ele poderia ser implementado na classe Facade para garantir que apenas uma instância do sistema esteja ativa ao longo de sua execução, evitando múltiplos pontos de controle e inconsistências na manipulação dos dados.

3. Padrão de Projeto: Factory Method

    Descrição Geral: O Factory Method é um padrão de criação que define uma interface para criar objetos, mas permite que as subclasses decidam qual classe instanciar. Ele promove a criação flexível de objetos sem expor a lógica de criação ao cliente.

    Problema Resolvido: O Factory Method resolve o problema da criação de objetos complexos e personalizados de forma organizada, desacoplando o código de criação do código de uso dos objetos.

    Identificação da Oportunidade: A criação de diferentes tipos de usuários no sistema (Cliente e Dono) é uma oportunidade clara para a aplicação do Factory Method, pois a lógica de criação desses usuários pode variar de acordo com suas permissões e atributos.

    Aplicação no Projeto: O método criarUsuario na Facade exemplifica uma implementação simples do Factory Method. Dependendo dos parâmetros passados (como o CPF, no caso dos donos), o sistema cria instâncias de classes específicas (Cliente ou Dono), sem expor a lógica de criação ao cliente do sistema.

4. Padrão de Projeto: Command

    Descrição Geral: O padrão Command encapsula uma solicitação como um objeto, permitindo que clientes parametrizem outras solicitações, enfileirem operações ou façam operações reversíveis.

    Problema Resolvido: Ele resolve o problema de como manipular e enfileirar comandos sem acoplar a execução diretamente ao invocador. Isso permite implementar desfazer/refazer e enfileirar comandos de forma eficiente.

    Identificação da Oportunidade: No contexto do sistema, o padrão Command poderia ser identificado para gerenciar ações dos pedidos, como adicionar produtos, remover produtos ou alterar o estado do pedido. Isso permitiria um controle mais detalhado sobre as operações realizadas no sistema.

    Aplicação no Projeto: Embora o padrão Command não esteja explicitamente implementado no projeto, ele poderia ser introduzido para melhorar a flexibilidade no gerenciamento de pedidos. Por exemplo, cada ação realizada em um pedido poderia ser encapsulada em um objeto de comando, permitindo maior controle sobre a reversibilidade e execução dessas operações.