# vendas
 Spring Boot Expert - JPA, RESTFul API, Security, JWT e Mais

Incluir no arquivo pom.xml o parent:

- Spring Boot Starter Parent

Link: https://mvnrepository.com/

Adicionar também as dependências e builds


Criar a classe Principal: VendasApplication

Adicionar Dependências Starter, dependências mais comums e usadas


Spring Boot Framework


Container IOC (Inversion of Control)

Configuration: classe de configuração
Component: classe com métodos e operações

MVC: Model - View - Controller

```
Container IOC -> @Configuration -> @Bean

Container IOC -> @Component -----> @Controller
                            \----> @Repository
                            \----> @Service
```
Acesso Spring Boot: http://localhost:8080/
Acesso H2: http://localhost:8080/h2-console


Propriedades Spring Boot Properties: https://docs.spring.io/spring-boot/docs/current/reference/html/appendix-application-properties.html

http://localhost:8080/h2-console

create table cliente (
    id int not null primary key,
    nome varchar(100)
)

Spring Boot utiliza HikariCP como DataSource (Pool de conexões): https://github.com/brettwooldridge/HikariCP


# Persistência e Acesso a Dados com Spring Data JPA

## O que Você Será Capaz de Fazer ao Concluir este Módulo

## Configurando e Obtendo Conexões com Bases de Dados

## O Modelo de Negócio

## Scripts de Criação do Banco de Dados

Para criar automaticamente as tabelas ao iniciar o Spring Boot, criar o arquivo data.sql na pasta resource com os scripts.

## Criando as Classes de Modelo

Criados em java/io.github.dougllasfps/domain.entity/

## Salvando e Recuperando Clientes

Criados métodos para listar e criar cliente

## Concluindo o Cadastro de Clientes

Criando métodos para update e delete de cliente

## Mapeando a Entidade Cliente para JPA

Mudando a Classe Cliente incluindo as notations

## Persistindo Entidades com Entity Manager

Refatorado List e Insert

## Refatorando as Outras Operações para o JPA

Refatorado todo o Clientes.java

## Introdução aos Repositórios Spring Data

Limpeza dos Códigos para usar o JPA Repository

## Query Methods

Métodos customizados do JPA

## Logando o SQL Gerado no Console

Inserir as linhas abaixo no application.properties para melhor visualizar os dados:

```
spring.jpa.properties.hibernate.show_sql=true
spring.jpa.properties.hibernate.format_sql=true
```

## Trabalhando com @Query

Exemplos de Consultas: JPQL e SQL Nativa

## Mapeando a Entidade Produto

Mapeamento Classe Produto

## Mapeando a Entidade Pedido

Mapeamento Classe Pedido

## Mapeando ItemPedido

Mapeamento Classe ItemPedido

## Criando os Repositorios das Entidades

Criando demais repositórios

## Fazendo Consultas com Relacionamentos JPA

## Carregando os Pedidos por Cliente

Correção da aula anterior...

# Spring Web: Desenvolvimento de API RESTFUL

## Criando o Controller de Clientes

Limpeza do Código VendasApplication e Criação / configuração do Controller

## Mapeando as Requisições com Request Mapping

Observações do Request Mapping

## Requisição GET com Parâmentros e Response Entity

## Requisicão POST e Request Body

## Delete Mapping: Deletando um Recurso no Servidor

## Put Mapping - Atualizando um Cliente

## Pesquisa de Clientes por Parâmetros

## @ResponseStatus e @RestController: Refatorando API de Clientes

Ao usar a notation @RestController ao invés de @Controller, não é mais necessário usar as notations @ResponseBody, pois a mesma já a contém.

Refatorado para padrão RESTful.

## Implementando a API de Produtos

API Produtos completa

## Criação do Controller e Serviço de Pedidos

Criação / Implementação da Camada de Serviço para Pedidos

## Trabalhando com o Modelo DTO

Criação dos DTOs

## Utilizando o Projeto Lombok

Adicionado a Biblioteca [Lombok] (https://projectlombok.org/)

Refatorado o Projeto

## Método para Realização de um Pedido

Finalizado o Pedido

## Spring Boot Dev Tools

Incluir no pom.xml a dependência para não precisar mais ficar reiniciando o Projeto para verificar atualizações, basta recompilar: CTRL + F9

## Utilizando o Controller Advice e os ExceptionHandlers para Tratar Erros na API

## Obtendo os Detalhes de um Pedido

Consultar detalhes do pedido

## Criando o Status do Pedido

Adicionando um atributo de Status do Pedido

## Patch Mapping - Realizando Cancelamento de Pedidos

Método para Cancelar o pedido

# Bean Validation

## Utilizando e Testando o Bean Validation

Criando validação do campo Cliente > Nome

## Validando a Entidade Cliente

Outras Validações

## Validando a Entidade Produto

Validações Produto, precisa da annotation @Valid no save e update

## Validando a Entidade Pedido

Validações Pedido

## Criando uma Annotation de Validação Customizada

Validação Customizada

## Internacionalização

# Spring Security e JWT

## Adicionando o Módulo Security

Adicionar dependência no pom.xml

## Criando a Classe de Configuração do Security

Criar Classe Security

## Password Encoder

Adicionar método de encoder

## Configurando Autenticação em Memória

Configuração do Authentication Manage Builder

## Configurando a Autorização de URLs

Configuração do configure(HttpSecurity)

## Configurando as Roles e Authorities

Configuração completa

## Autenticação Basic

Configurando autenticação Basic

## Implementação do UserDetailsService

Implementação

## Implementando o Cadastro de Usuários

Implementação do cadastro do usuário

## Testando a Autenticação com Cadastro de Usuários

Finalizando

## Introdução ao JWT

## Gerando o Token

Token

## Decodificando o Token

Decode Token

## Implementando o Filtro do JWT

Filtro implementado

## Finalizando a Configuração do JWT no Spring Security

Registrar filtro no Spring Security

## Implementando o Método de Autenticação de Usuários

Finalizando

# Migração para o Banco MySQL

## Fazendo a migração para o Banco MySQL

Mudar parâmetros de conexão

# Documentação de API com SWAGGER

## Configurando o Swagger

Inclusão das dependências e configurações básicas

https://github.com/mplushnikov/lombok-intellij-plugin/issues/952

## Configurando o Security no Swagger

Habilitando consultas com Token no swagger

## Customizando a UI da Documentação da API

Customizando a UI do Swagger: ClienteController

# Build e Deploy

## Gerando o JAR

JAR (Java Archive)

Gerar o JAR

```
mvn clean package
```

Executar o JAR

```
java -jar .\vendas-1.0-SNAPSHOT.jar
```

## Gerando um Arquivo WAR

WAR (Web Archive)

incluir no pom.xml a tag PACKAGING e dependencia do tomcat
extender a classe principal

executar mesmos comandos do JAR

```
<packaging>war</packaging>
```

```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-tomcat</artifactId>
    <scope>provided</scope>
</dependency>
```

## Profiles Maven para Builds Diferentes

alterado configuracao de profiles do pom.xml e executar com os comandos abaixo

```
mvn clean package -P desenvolvimento
```

ou

```
mvn clean package -P producao
```
# jupiter-backend
