# CLEVERTEC SPRING

**Автор Евгений Гарбузов**

***

### Параметры приложения, библиотеки и зависимости

- Java 17
- Gradle 8.0
- Spring Framework 6.1.2, Spring Data JPA 3.2.1
- PostgreSQL 15.2, PostgreSQL JDBC Driver 42.7.1
- Liquibase 4.25.0
- Apache Tomcat 10.1.18
- Hibernate Core 6.4.1.Final, Hibernate Validator 8.0.1.Final
- Jackson 2.16.1
- HikariCP 5.1.0
- Jakarta Annotations 3.0.0-M1, Jakarta Servlet 6.1.0-M1
- Lombok Plugin 6.5.1
- MapStruct 1.5.5.Final
- JUnit 5.9.2

Для работы с приложением необходимо использовать Docker, для чего предусмотрен
[docker-compose.yml](docker-compose.yml "docker-compose.yml").
Перед началом работы необходимо выполнить команду ```docker compose up``` для создания контейнера базы данных.

Точка входа в приложение - main-класс [App](src/main/java/ru/clevertec/clevertecspring/App.java "App.java").

Apache Tomcat запускается автоматически при старте приложения
(см. [TomcatStarter](src/main/java/ru/clevertec/clevertecspring/util/TomcatStarter.java "TomcatStarter.java")).

***

### Примеры HTTP-запросов для работы с приложением

#### - GET (Person Get By UUID)

request url:</br>
http://localhost:8080/persons/6e19a7ae-78f5-475f-92cd-8e838bbc269e

response:

```json
{
  "uuid": "6e19a7ae-78f5-475f-92cd-8e838bbc269e",
  "name": "Ivan",
  "surname": "Ivanov",
  "sex": "MALE",
  "passport": {
    "series": "HB",
    "number": "1111111"
  },
  "residencyUuid": "7d7369f0-368f-4ed3-9fbb-71e7c7664121",
  "createDate": "2024-01-13T17:06:23:195",
  "updateDate": "2024-01-13T17:06:23:195"
}
```

#### - GET (Person Get All)

request url:</br>
http://localhost:8080/persons

response:

```json
[
  {
    "uuid": "6e19a7ae-78f5-475f-92cd-8e838bbc269e",
    "name": "Ivan",
    "surname": "Ivanov",
    "sex": "MALE",
    "passport": {
      "series": "HB",
      "number": "1111111"
    },
    "residencyUuid": "7d7369f0-368f-4ed3-9fbb-71e7c7664121",
    "createDate": "2024-01-13T17:06:23:195",
    "updateDate": "2024-01-13T17:06:23:195"
  },
  {
    "uuid": "36245727-058e-455b-9d42-accc28c05177",
    "name": "Petr",
    "surname": "Petrov",
    "sex": "MALE",
    "passport": {
      "series": "HB",
      "number": "1112222"
    },
    "residencyUuid": "7d7369f0-368f-4ed3-9fbb-71e7c7664121",
    "createDate": "2024-01-13T17:06:23:195",
    "updateDate": "2024-01-13T17:06:23:195"
  }
]
```

#### - POST (Person Create)

request url:</br>
http://localhost:8080/persons

request body:

```json
{
  "name": "Sidr",
  "surname": "Sidorov",
  "sex": "MALE",
  "passport": {
    "series": "HB",
    "number": "1113333"
  },
  "residencyUuid": "7d7369f0-368f-4ed3-9fbb-71e7c7664121"
}
```

response:

```json
{
  "uuid": "c6153e94-cc6c-45bd-a034-24bba8066e25",
  "name": "Sidr",
  "surname": "Sidorov",
  "sex": "MALE",
  "passport": {
    "series": "HB",
    "number": "1113333"
  },
  "residencyUuid": "7d7369f0-368f-4ed3-9fbb-71e7c7664121",
  "createDate": "2024-01-13T17:18:29:407",
  "updateDate": "2024-01-13T17:18:29:407"
}
```

#### - PUT (Person Update)

request url:</br>
http://localhost:8080/persons/6e19a7ae-78f5-475f-92cd-8e838bbc269e

request body:

```json
{
  "name": "Semen",
  "surname": "Semenov",
  "sex": "MALE",
  "passport": {
    "series": "HB",
    "number": "5511771"
  },
  "residencyUuid": "7d7369f0-368f-4ed3-9fbb-71e7c7664121"
}
```

response:

```json
{
  "uuid": "6e19a7ae-78f5-475f-92cd-8e838bbc269e",
  "name": "Semen",
  "surname": "Semenov",
  "sex": "MALE",
  "passport": {
    "series": "HB",
    "number": "5511771"
  },
  "residencyUuid": "7d7369f0-368f-4ed3-9fbb-71e7c7664121",
  "createDate": "2024-01-13T17:06:23:195",
  "updateDate": "2024-01-13T17:25:21:639"
}
```

#### - GET (Get Residents By House UUID)

request url:</br>
http://localhost:8080/persons/residents/7d7369f0-368f-4ed3-9fbb-71e7c7664121

response:

```json
[
  {
    "uuid": "6e19a7ae-78f5-475f-92cd-8e838bbc269e",
    "name": "Ivan",
    "surname": "Ivanov",
    "sex": "MALE",
    "passport": {
      "series": "HB",
      "number": "1111111"
    },
    "residencyUuid": "7d7369f0-368f-4ed3-9fbb-71e7c7664121",
    "createDate": "2024-01-13T18:18:47:762",
    "updateDate": "2024-01-13T18:18:47:762"
  },
  {
    "uuid": "36245727-058e-455b-9d42-accc28c05177",
    "name": "Petr",
    "surname": "Petrov",
    "sex": "MALE",
    "passport": {
      "series": "HB",
      "number": "1112222"
    },
    "residencyUuid": "7d7369f0-368f-4ed3-9fbb-71e7c7664121",
    "createDate": "2024-01-13T18:18:47:762",
    "updateDate": "2024-01-13T18:18:47:762"
  }
]
```

#### - GET (House Full Text Search)

request url:</br>
http://localhost:8080/houses/fulltextsearch/A

response:

```json
[
  {
    "uuid": "7d7369f0-368f-4ed3-9fbb-71e7c7664121",
    "area": "Eurasia",
    "country": "Belarus",
    "city": "Gomel",
    "street": "Bazarnaya",
    "number": "1-A",
    "createDate": "2024-01-13T18:18:47:745"
  },
  {
    "uuid": "fc34b864-9b6f-43f3-8f38-2707b34e3c7b",
    "area": "Eurasia",
    "country": "Belarus",
    "city": "Gomel",
    "street": "Bazarnaya",
    "number": "2-A",
    "createDate": "2024-01-13T18:18:47:745"
  }
]
```

***