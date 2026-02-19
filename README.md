# Desafio prático de automação de testes
## Camada front-end

------

#### Ferramentas utilizadas

- Java 17;
- Maven;
- Selenium 4;
- WebDriver Manager;
- JUnit 5;
- Allure;

#### Como executar os testes

1) Antes de tudo, você precisa ter o [Java 17](https://uanscarvalho.com.br/instalando-java-17-no-windows/) e o [Maven](https://dicasdeprogramacao.com.br/como-instalar-o-maven-no-windows/) instalados e configurados na sua máquina. Os links anteriores mostram este processo no Windows.

2) Na pasta de sua preferência, abra o terminal de comandos e cole este comando:

```
git clone https://github.com/cassiolinden/desafio-pratico-frontend.git
```

3) Ainda na mesma pasta, para executar os testes, você precisa rodar os seguintes comandos:

```bash
# Limpa e roda os testes (headless por padrão via config.properties)
mvn clean test

# Alternativamente, pode ser rodado com diferentes propriedades via linha de comando
mvn clean test -Dbrowser=chrome -Dheadless=true
```

4) Para visualizar o relatório da execução, rode os seguintes comandos:

```bash
# Gerar relatório Allure (requer Allure CLI instalado)
mvn allure:report
mvn allure:serve
```

#### Estrutura do projeto

```
.
├── .github
│   └── workflows
│       ├── ci.yml
│       └── scheduled.yml
├── .gitignore
├── pom.xml
├── README.md
├── src
│   ├── main
│   │   └── java
│   │       └── com/desafio/frontend
│   │           ├── core
│   │           │   ├── AutoScreenshotExtension.java
│   │           │   ├── Config.java
│   │           │   ├── DriverFactory.java
│   │           │   └── ScreenshotUtils.java
│   │           └── pages
│   │               ├── BasePage.java
│   │               ├── HomePage.java
│   │               └── RoomPage.java
│   └── test
│       ├── java
│       │   └── com/desafio/frontend/tests
│       │       ├── BaseTest.java
│       │       ├── HomeTest.java
│       │       └── RoomTest.java
│       └── resources
│           ├── allure.properties
│           ├── junit-platform.properties
│           └── config
│               └── config.properties
```

#### Estratégia adotada

Para a escolha do software a ser testado, fui atrás de algo que pudesse representar um fluxo de um software real, além da possibilidade de ser testado tanto no front quanto no back-end. Nas minhas pesquisas, cheguei [neste repositório](https://github.com/BMayhew/awesome-sites-to-test-on) com uma lista de sugestões. Nele, encontrei o [Sunny Meadows Bed & Breakfast](https://automationintesting.online/), um portal que simula a reserva de quartos numa pousada, atendendo, assim, meus requisitos. Ele também conta com um [repositório no GitHub](https://github.com/mwinteringham/restful-booker-platform).

Com o software escolhido, comecei a fazer alguns testes exploratórios: envio de mensagens na home, redirecionamento de links, fluxo de reserva ponta-a-ponta, além de verificar que tipos de validações eram feitas nos formulários disponibilizados. Segui a mesma linha para a parte administrativa do software, onde validei o cadastro de quartos e suas reservas vinculadas, o módulo de relatórios, *branding* e de mensagens, com o ituito de descobrir como o dia a dia da aplicação era refletido na visão administrativa da mesma.

Durante este processo, comecei o rascunho dos casos de teste. Aqui, eu acabei focando mais na parte funcional, com o objetivo de validar as regras de negócio da aplicação. Com a lista de casos de teste pronta, perguntei ao Copilot por sugestões de cenários, e incluí cenários de segurança, com o uso de Injections. Após isso, segui para a escrita dos cenários de teste, seguindo o modelo proposto para o desafio - para esta etapa e para a definição de prioridades, contei com o auxílio do Copilot, passando por revisão criteriosa feita por mim, para averiguar se o preenchimento estava coerente de acordo com a realidade.

Para a parte da automação front-end, pensei em utilizar o Selenium em Java, com a arquitetura Page Objects, com os quais eu tenho maior familiaridade - a manutenibilidade também pesou nesta escolha. Os cenários que eu escolhi automatizar se basearam na sua prioridade (alta), considerando aspectos como quebra de fluxo crítico e risco de perda de receita. Também automatizei alguns cenários de prioridade média, para servir de suporte para os cenários com prioridade mais alta, por conta da possibilidade de reuso proporcionado pela arquitetura utilizada.

Tanto o projeto de automação front-end quanto o de back-end contam com a geração dos relatórios pelo Allure, cujos resultados são disponibilizados via GitHub Pages. Também incluí para ambos a execução via pipeline do GitHub Actions, de forma agendada para todas as manhãs de segunda a sexta, além da possibilidade de acionar a execução de forma manual.

Os cenários escolhidos para a automação e o status da última execução podem ser conferidos [aqui](https://cassiolinden.github.io/desafio-pratico-frontend/).