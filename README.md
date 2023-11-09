# Spring QuickStart

Este é um projeto modelo para desenvolvimento de API's

### Tecnologias

- Java 11
- SpringBoot 3.1.5
- SpringDataJpa
- Hibernate
- SpringWeb
- SpringSecurity
- JWT
- SpringTest
- Lombok
- Flyway

### Persistência de dados

O SpringBoot é facilmente configurável a qualquer provedor de acesso a dados como H2 Database, PostgreSQL, MySQL, SQLServer, Oracle Database entre outros, basta informar a biblioteca correspondente e configurar a sua conexão de bancos de dados no arquivo `resources/application.properties`.

### Dependências

Um projeto SpringBoot é formado por dependência denominadas de **starters** e o nosso projeto por ser uma API Rest especificamente utilizará os starters citados no `pom.xml`

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
</dependency>

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

#### Projeto Lombok

O Lombok é uma biblioteca Java focada em produtividade e redução de código boilerplate que, por meio de anotações adicionadas ao nosso código, ensinamos o compilador (maven ou gradle) durante o processo de compilação a criar código Java.

```xml
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <version>1.18.24</version>
    <scope>provided</scope>
</dependency>
```

### Estrutura do projeto

O projeto é composto por pacotes que classificam as classes de acordo com o seu papel ou finalidades específicas.

| Nome       | Descrição                                                                                      |
| ---------- | ---------------------------------------------------------------------------------------------- |
| core       | pacote que contém classes de infraestrutura                                                    |
| service    | pacote que contém as classes responsáveis por gerenciar as regras de negóicio da aplicação     |
| repository | pacote que contém as interfaces responsáveis pela persistência de cada entidade correspondente |
| model      | pacote que contém as classes de modelo do sistema (entidades)                                  |
| dto        | pacote que contém as classes de transferência de dados (dtos)                                  |
| controller | pacote que contém as classes que representam os recursos https disponíveis pela aplicação      |
