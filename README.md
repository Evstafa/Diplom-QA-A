# Дипломный проект по профессии «Тестировщик»

Дипломный проект — автоматизация тестирования комплексного сервиса, взаимодействующего с СУБД и API Банка.


![](pic/service.png)

### Документы ###

1. [Дипломное задание](https://github.com/netology-code/qa-diploma)
2. [План автоматизации](https://github.com/Evstafa/Diplom-QA-A/blob/main/documentation/Plan.md)
3. [Отчет по итогам тестирования](https://github.com/Evstafa/Diplom-QA-A/blob/main/documentation/Report.md)
4. [Отчет по итогам автоматизации] (https://github.com/Evstafa/Diplom-QA-A/blob/main/documentation/Summary.md)

### Запуск проекта, тестирование, отчет ###

1. Запустить Docker Desktop
2. Запустить IntelliJ IDEA
3. Открыть скачанный с GitHub проект (https://github.com/Evstafa/Diplom-QA-A)
4. Запустить контейнеры командой в корне проекта `docker-compose up`
5. Запустить приложение командой:
- для запуска с подключением к MySQL:

`java -jar artifacts/aqa-shop.jar --spring.datasource.url=jdbc:mysql://localhost:3306/app`
- для запуска с подключением к PostgreSQL:

`java -jar artifacts/aqa-shop.jar --spring.datasource.url=jdbc:postgresql://localhost:5432/app`

6. Запустить тесты во второй вкладке Terminal командой:
- для запуска с подключением к MySQL:

`.\gradlew clean test -DdbUrl=jdbc:mysql://localhost:3306/app`
- для запуска с подключением к PostgreSQL:

`.\gradlew clean test -DdbUrl=jdbc:postgresql://localhost:5432/app`

7. Получить отчет после полного завершения тестов в браузере командой `.\gradlew allureServe`
8. Закрыть отчет командой `Ctrl + C`, подтвердить выход, нажав на `Y`
9. Закрыть приложение командой `CTRL + C` в первой вкладке Terminal
10. После остановки приложения остановить контейнеры командой `docker-compose down`
