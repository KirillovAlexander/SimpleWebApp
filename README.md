# SimpleWebApp
REST-сервис, предоставляющий интерфейс для работы с базой данных сотрудников.

## Описание
При создании приложения были использованы:
- Spring Boot
- Spring JDBC
- В качестве базы данных - PostgreSQL
- Сборщик Maven
- Для запуска используется Docker
- Логирование с помощью Log4j
- OpenAPI документация
- JUnit, Mockito и Testcontainers для тестирования
- Liquibase для миграций  
- Vue.js для UI

Сервис реализует методы для работы с базой сотрудников, описанные в [протоколе](https://github.com/KirillovAlexander/SimpleWebApp/blob/master/EmployeeServiceSpecification.yaml).

По умолчанию приложение запускается на `http://localhost:8080/`.

Для запуска должна быть развернута Postgres с базой `employeedb` на порту `5432`. В файле `application.property` необходимо указать данные для подключения к БД в полях
`spring.datasource.username` и `spring.datasource.password`. По умолчанию там указаны значения `HR` и `HR` соответственно.

## Запуск с использованием Docker
Для запуска приложения необходимо склонировать репозиторий, открыть корневую папку проекта и выполнить команду `mvn package`.
После упаковки проекта необходимо создать docker-образ с помощью команды `docker build -t simple_web_app:v.1 .`.
Затем запустить сервис с помощью команды `docker-compose up -d`.

## Запуск на локальной машине
Для запуска на локальной машине необходимо изменить значение property для подключения к БД на `spring.datasource.url=jdbc:postgresql://localhost:5432/employeedb`,
так как по умолчанию указан доступ из docker-контейнера.

##Swagger UI
После запуска приложения по адресу `http://localhost:8080/swagger-ui.html` доступна OpenAPI 3 документация.

