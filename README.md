# Проект реестр техники в виде REST-приложения с OpenAPI документацией.

- Язык программирования: Java 22;
- Frameworks: Spring Boot Starter v3.2.4, Spring Web, Spring JPA, Spring Hibernate;
- База данных: Postgres SQL;
- Библиотека для генерации документации: springdoc-openapi v2.5.0

## Инструкция по запуску
Клонирование репозитория  
```sh
git clone https://github.com/SverdlovSemen/tech-registry
```

Переход в директорию проекта
```sh
cd {path}/tech-registry
```

Сборка проекта с использованием Maven
```sh
mvn clean install
```

Запуск приложения
```sh
mvn spring-boot:run
```

После запуска приложения перейдите по следующему адресу в вашем веб-браузере:  
http://localhost:8080/swagger-ui/index.html#  
Вы увидите интерфейс Swagger UI, где можно ознакомиться с документацией API.
