# Fábrica de Chocolate Wonka

Projeto desenvolvido para a disciplina MC322, do IC da UNICAMP. A aplicação simula o funcionamento de uma fábrica de chocolates Wonka, permitindo gerenciar demandas, matéria-prima, máquinas, produção, manutenção e estratégias de fabricação.

## Funcionalidades

- Cadastro e atualização de demandas para diferentes tipos de produtos
- Consulta e compra de matéria-prima, com controle de estoque e orçamento
- Execução da produção por uma linha composta por misturador, embaladora e estação de inspeção
- Seleção da próxima demanda por meio de diferentes estratégias de produção
- Consulta do estado das máquinas e realização de manutenções
- Auditoria e geração de relatórios sobre máquinas e produtos fabricados
- Salvamento e recuperação do estado da fábrica em arquivo XML

## Tecnologias utilizadas

- Java 17 para implementação da aplicação.
- Gradle para compilação, execução e gerenciamento das dependências.
- JUnit 5 (Jupiter) para testes automatizados.
- XML, manipulado pelas APIs DOM do Java, para representar os dados persistidos.

## Como executar o projeto

É necessário ter o Java 17 ou uma versão compatível instalada. Os comandos a seguir devem ser executados no diretório raiz do projeto, onde está localizado o Gradle Wrapper.

### Compilar o projeto

```bash
./gradlew build
```

### Executar a aplicação

```bash
./gradlew run
```

### Executar os testes automatizados

```bash
./gradlew test
```

O relatório HTML dos testes é gerado em:

```text
app/build/reports/tests/test/index.html
```

## Organização do projeto

O código-fonte segue a estrutura convencional de projetos Java com Gradle. As classes estão separadas em pacotes de acordo com suas responsabilidades:

```text
app/
├── src/
│   ├── main/
│   │   └── java/br/unicamp/ic/mc322/
│   │       ├── Main.java
│   │       ├── model/        # entidades e componentes do domínio
│   │       ├── service/      # gerenciamento e regras da produção
│   │       ├── strategy/     # estratégias de seleção de demandas
│   │       ├── persistence/  # leitura e escrita do xml
│   │       └── exception/    # exceções específicas da aplicação
│   └── test/
│       └── java/br/unicamp/ic/mc322/
│           ├── model/        # testes das entidades do domínio
│           ├── service/      # testes do gerenciamento da fábrica
│           └── persistence/  # testes da persistência
├── build.gradle.kts
└── ...
```

As principais responsabilidades são:

- **`model`**: representa os elementos da fábrica, como matérias-primas, demandas, produtos, máquinas e seus componentes.
- **`service`**: contém o `GerenciadorProducao`, responsável por coordenar demandas, estoque, orçamento, máquinas e produção.
- **`strategy`**: reúne as implementações de estratégias para selecionar demandas.
- **`persistence`**: contém `PersistenciaPlanta`, responsável por acessar o arquivo, e `ConversorPlantaXml`, responsável por converter entre o XML e os objetos do domínio.
- **`exception`**: reúne exceções utilizadas para representar falhas de negócio e de persistência.

## Persistência dos dados

A persistência é realizada em XML. Por padrão, a aplicação utiliza o caminho `dados/planta.xml`, relativo ao diretório de execução.

Ao iniciar, se esse arquivo existir, a aplicação oferece a opção de recuperar o estado salvo ou começar uma nova fábrica. Caso não exista um arquivo, uma nova fábrica é inicializada. No menu principal também estão disponíveis as opções **9 – Salvar estado da fábrica** e **10 – Carregar estado da fábrica**.

O carregamento cria uma nova instância de `GerenciadorProducao` a partir dos dados lidos. A fábrica em uso só é substituída depois que a leitura e a reconstrução terminam com sucesso. Assim, uma falha de carregamento não substitui o estado atual por uma fábrica parcialmente recuperada.

### Estrutura dos dados persistidos

O XML registra os seguintes dados:

- **Configuração geral:** cenário operacional, orçamento atual, número do próximo lote e estratégia de produção selecionada
- **Matéria-prima:** nome, quantidade em estoque, unidade de medida e custo por unidade
- **Máquinas:** tipo concreto da máquina, nome, capacidade máxima, custo de operação, probabilidade-base de falha e estado ligada/desligada
- **Componentes das máquinas:** balança, braços robóticos e regulador de temperatura, incluindo a saúde atual e máxima de cada componente
- **Demandas:** tipo de produto, quantidade solicitada e status da demanda

O arquivo contém um atributo de versão para identificar o formato do XML. Na leitura, são verificadas a estrutura, os tipos conhecidos e a compatibilidade dos valores com as regras do domínio.

## Escopo e Limitações

### Dados Persistidos vs. Não Persistidos

A implementação prioriza a recuperação de um estado útil da fábrica, contemplando os dados obrigatórios do enunciado: estoque de matéria-prima, máquinas e seus componentes, e demandas com seus respectivos estados. Também são persistidos dados complementares que influenciam a continuidade da operação.

**Dados persistidos:**

- **Estoque de matéria-prima:** nome, quantidade, unidade e custo por unidade. O identificador interno (`id`) não é gravado; ele é gerado novamente quando a matéria-prima é reconstruída.
- **Máquinas e componentes:** tipo, nome, capacidade, custo de operação, probabilidade-base de falha, estado de funcionamento e saúde atual/máxima da balança, de cada braço robótico e do regulador de temperatura.
- **Demandas:** tipo de produto, quantidade e status (`PENDENTE`, `EM_PRODUCAO`, `CONCLUIDA` ou `CANCELADA`).
- **Estado geral da fábrica:** cenário operacional, orçamento atual, estratégia de produção e número do próximo lote.

O orçamento, o cenário, a estratégia e o próximo lote foram incluídos porque influenciam as próximas operações da fábrica. Recuperá-los evita que a aplicação volte aos valores iniciais depois de carregar um estado salvo.

**Dados não persistidos:**

- **Produtos fabricados e armazenados:** a lista interna de produtos aprovados (`produtosFabricados`) não é gravada no XML. Consequentemente, o armazém é iniciado vazio após a recuperação.
- **Dados individuais dos produtos:** como o armazém não é persistido, também não são recuperados os identificadores, status, qualidade, probabilidade acumulada de falha, lote e tempo real de produção de cada produto.
- **Identificadores gerados automaticamente:** o `id` da matéria-prima e os identificadores/contadores estáticos de produtos não são preservados entre execuções. Eles são gerados novamente conforme os objetos são criados.
- **Parâmetros fixos dos subcomponentes:** valores definidos diretamente pelos construtores das máquinas, como a precisão da balança, a velocidade dos braços e a temperatura-alvo do regulador, não são gravados separadamente. Esses valores são reconstruídos a partir da implementação do tipo concreto da máquina. A composição e os estados de saúde dos componentes, por outro lado, são registrados no XML.

A lista de produtos fabricados foi mantida apenas em memória porque a persistência do armazém é complementar ao conjunto mínimo exigido. Essa escolha mantém a implementação concentrada na recuperação do estoque de matéria-prima, das máquinas e das demandas. Como consequência, o histórico e os produtos já aprovados não permanecem disponíveis após reiniciar a aplicação.

### Estratégia de Testes Automatizados

Os testes automatizados utilizam **JUnit 5** e verificam coportamentos observáveis do sistema. A suíte está organizada em classes de teste por responsabilidade:

- **`DemandaTest`:** verifica a criação de demandas, atualização de quantidades, rejeição de quantidades que deixariam o valor negativo, transições de status, impedimento de concluir uma demanda cancelada e cálculo do custo estimado.
- **`MateriaPrimaTest`:** verifica a criação e validação dos dados da matéria-prima, consumo de estoque, tratamento de consumo superior à quantidade disponível, adição de estoque e consulta de disponibilidade.
- **`GerenciadorProducaoTest`:** verifica a configuração inicial da fábrica, atualização de demandas, rejeição de atualizações inválidas, compra de matéria-prima, tratamento de orçamento insuficiente e condições que impedem a fabricação.
- **`PersistenciaPlantaTest`:** verifica a criação do arquivo XML, a preservação dos dados após salvar e carregar, e o tratamento de arquivo inexistente, arquivo vazio, XML corrompido e dados incompatíveis com as regras do domínio.

Os testes de persistência utilizam o recurso `@TempDir` do JUnit para criar arquivos em diretórios temporários. Dessa maneira, os arquivos utilizados nos testes ficam separados do arquivo `dados/planta.xml` utilizado pela aplicação. Cada teste prepara seus próprios dados e não depende de uma execução manual anterior do programa.

A execução dos testes é integrada ao Gradle. O comando `./gradlew test` executa a suíte e gera o relatório HTML indicado anteriormente; o comando `./gradlew build` também executa a tarefa de testes como parte da construção do projeto.

## Tratamento de erros de persistência

A leitura e a escrita do XML podem falhar. A aplicação trata, entre outras, as seguintes situações:

- **Arquivo inexistente:** sinalizado por `ArquivoInexistenteException`.
- **Arquivo vazio, XML malformado ou estrutura/versão incompatível:** sinalizados por `ArquivoCorrompidoException`.
- **Valores que não respeitam as regras do domínio:** sinalizados por `DadosInvalidosException`.
- **Falhas gerais de leitura, escrita ou geração do XML:** representadas por `PersistenciaException`.

Na interface, as falhas são apresentadas ao usuário por mensagens de erro. Quando o carregamento falha durante a execução, a fábrica que já estava em uso é mantida. Durante o salvamento, a gravação é feita inicialmente em um arquivo temporário e depois o arquivo de destino é substituído, reduzindo o risco de perder um estado salvo anteriormente caso ocorra uma falha durante a escrita.
