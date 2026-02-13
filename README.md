# Framework de Testes Automatizados (Selenium + JUnit 5 + Allure + Maven)

Este repositório traz um **template pronto** para iniciar testes UI com **Selenium WebDriver**, **JUnit 5**, **Allure** e **Maven**, organizado em **Page Objects** e com **CI no GitHub Actions**.

## Principais Componentes
- **Java 17**, **Maven**
- **Selenium 4**, **WebDriverManager** (gerencia drivers automaticamente)
- **JUnit 5**, **Allure** (anotações, resultados e relatório)
- **Page Object Pattern** (facilita manutenção e reutilização)
- **Arquivo de configuração** (`src/test/resources/config/config.properties`)
- **GitHub Actions** com execução headless e upload de artefatos Allure

## Estrutura
```
.
├── .github/workflows/ci.yml
├── .gitignore
├── pom.xml
├── README.md
├── src
│   ├── main
│   │   └── java
│   │       └── com/example/framework
│   │           ├── core
│   │           │   ├── Config.java
│   │           │   └── DriverFactory.java
│   │           └── pages
│   │               ├── BasePage.java
│   │               ├── LoginPage.java
│   │               └── SecureAreaPage.java
│   └── test
│       ├── java
│       │   └── com/example/framework/tests
│       │       ├── BaseTest.java
│       │       └── LoginTest.java
│       └── resources
│           ├── allure.properties
│           ├── junit-platform.properties
│           └── config
│               └── config.properties
```

## Pré-requisitos
- **Java 17+** instalado
- **Maven 3.8+** disponível no PATH
- (Opcional) **Allure CLI** para gerar relatório local: <https://docs.qameta.io/allure/>

## Como executar localmente
```bash
# Limpa e roda os testes (headless por padrão via config.properties)
mvn clean test

# Rodar alterando propriedades via linha de comando
mvn clean test -Dbrowser=chrome -Dheadless=true -DbaseUrl=https://the-internet.herokuapp.com

# Gerar relatório Allure (requer Allure CLI instalado)
allure serve target/allure-results
# ou
mvn allure:report && open target/site/allure-maven/index.html
```

## GitHub Actions
O workflow em `.github/workflows/ci.yml`:
- Usa Ubuntu, configura Java 17
- Instala Google Chrome
- Executa `mvn -B clean test -Dheadless=true`
- Faz upload dos artefatos `target/allure-results`

## Configuração
Edite `src/test/resources/config/config.properties`:
```properties
baseUrl=https://the-internet.herokuapp.com
browser=chrome
headless=true
implicitWait=10
```

## Observações
- Os testes usam o site público de demonstração **The Internet (Herokuapp)** para login.
- **WebDriverManager** resolve e baixa os binários de driver automaticamente.
- Os resultados do Allure ficam em `target/allure-results`.
