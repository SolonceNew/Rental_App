# Сервис для учета арендаторов и автомобилей 

## Описание проекта
CRUD-приложение разработано на базе Spring Boot.
Данные о пользователях (арендаторах) машин и о машинах хранятся в базе данных PostgreSQL.
Используется Spring Data JPA и Hibernate.
Vue - реализовано при помощи Thymeleaf.
Протестировано c помощью JUnit и Mockito.
Реализована валидация.

Приложение позволяет:
1. Вносить новых арендаторов и машин.
2. Редактироватьи и удалять информацию об арендаторах и автомобилях.
3. Просматривать дополнительную информацию об арендаторах.
4. Назначать конкретному пользователю автомобили. В случае просрочки срока аренды появляется уведомление.
5. Поиск автомобилей по словам.

## Запуск сервера и базы данных:
* Приложение запускается на порте 8081 и доступен по url в браузере http://localhost:8081
* При переходе по адресу http://localhost:8081/people появляется база арендаторов, а также функционал для несения данных о новых арендаторах.
* При переходе по адресу http://localhost:8081/cars появляется база автомобилей, а также функционал для добавления (удаления) новых автомобилей и назначения их человеку.
* В приложении используется база данных PostgreSQL. Запускается на порте 5432. 

