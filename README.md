[![Build Status](https://travis-ci.com/BorisGry/SimpleLabs.svg?branch=main)](https://travis-ci.com/BorisGry/SimpleLabs)
# Технология разработки программного обеспечения
## Лабораторная работа №1: создание микросервиса на Spring Boot с базой данных
# Грязнов Борис Валерьевич, Группа 3MAC2001
# Цель лабораторной работы:
## Целью лабораторной работы является знакомство с проектированием многослойной архитектуры Web-API (веб-приложений, микро-сервисов).

Приложение представляет из себя простой микросервис, реализующий CRUD на примере внутренней базы. Для работы приложения требуется запущенная БД postgresql.

# Подготовка базы данных
В файле ./src/main/resources/application.properties следует указать в параметре spring.datasource.username = имя пользователя для доступа в БД, в параметре spring.datasource.password = пароль для доступа к БД. В параметре spring.datasource.url = необходимо указать адрес для доступа к БД, например для доступа к БД запущенной на локальном компьютере значение будет jdbc:postgresql://localhost:8080:5432/postgresql, для БД запущенной в docker на локальной машине значение будет jdbc:postgresql://172.17.0.1:5432/postgresql.
Нужно запустить postgresql с помощью docker используя комманду 
- docker run -e POSTGRES_PASSWORD=root -p 5432:5432 postgres .
Создание схемы базы данных осуществляется с помощью ./src/main/resources/schema.sql. 
Тестовые данные для БД находятся в ./src/main/resources/data.sql . 

# Клонирование репозитария
Для клонирования репозитория необходимо выполнить команду
- git clone https://github.com/BorisGry/simpleapi.git
или же скачать zip-архив и распаковать его.

# Сборка проекта с помощью Maven
Сборка приложения осуществляется при помощи автоматической системы сборки проектов Maven. Для сборки необходимо выполнить команду mvn package -Dmaven.test.skip=true(с пропуском тестирования) находясь в директории проекта. После окончания выполнения команды появится папка target в которой находится скомпилированный код и файл simpleapi-1.0.jar.

# Сборка и запуск Docker-образа
Для сборки Docker образа следует выполнить команду docker build -t simpleapi:latest . находясь в директории с Dockerfile и собранным simpleapi-1.0.jar .
Запуск осуществляется командой docker run -p 8080:8080 simpleapi:latest , где первым указывается порт в локальной системе, а вторым порт docker.

# Примеры запросов к simpleapi.
Формат JSON: {name: "varchar", brand: "varchar",price "integer", quantity: "integer"}

# Получить список:
curl -X GET http://localhost:8080:8080/api/v1/product В ответ будет получен JSON.

# Получить запись по id:
curl -X GET http://localhost:8080:8080/api/v1/product/{id} В ответ будет получен JSON с результатом.

# Обновить запись:
curl -X POST http://localhost:8080/api/v1/product/{id} -d '{"name": "новое продукт", "brand": "новый бренд", "price": 10000, "quantity": 1000}' -H "Content-Type:application/json".

# Добавить запись:
curl -X POST http://localhost:8080/api/v1/product -d '{"name": "новый продукт", "brand": "новый бренд", "price": 10000, "quantity": 1000}' -H "Content-Type:application/json" В ответ будет получен JSON с новой записью.

# Удалить запись:
curl -i -X DELETE http://localhost:8080/api/v1/product/{id} В ответ будет получен статус 204 No Content.

#Также приложение возвращает значение hostname:
curl -X GET http://localhost:8080/api/v1/status В ответ будет получен JSON в виде {hostname: "hostname", "helloWord": "hello", "sweetDays": "daaa"}.

## Лабораторная работа №2: создание кластера Kubernetes и деплой приложения
Целью лабораторной работы является знакомство с кластерной архитектурой на примере Kubernetes, а также деплоем приложения в кластер.
#### Манифесты 
- deployment.yaml
>
    apiVersion: apps/v1
    kind: Deployment
    metadata:
      name: my-deployment
    spec:
      replicas: 5
      selector:
        matchLabels:
          app: my-app
      strategy:
        rollingUpdate:
          maxSurge: 1
          maxUnavailable: 1
        type: RollingUpdate
      template:
        metadata:
          labels:
            app: my-app
        spec:
          containers:
            - image: simplelab:latest
              imagePullPolicy: Never 
              name: simplelab
              ports:
                - containerPort: 8080
          hostAliases:
          - ip: "192.168.49.1"
            hostnames:
            - postgres.localhost

- service.yaml
>
    apiVersion: v1
    kind: Service
    metadata:
      name: my-service
    spec:
      type: NodePort
      ports:
        - nodePort: 31317
          port: 8080
          protocol: TCP
          targetPort: 8080
      selector:
        app: my-app
        
# Скриншот работающего приложения в консоли и подов.
![](https://github.com/BorisGry/SimpleLabs/blob/main/scrin/Screenshot%20from%202020-11-26%2020-05-43.png)   
        
# Лабораторная работа №3: CI/CD и деплой приложения в Heroku
# Цель работы: 
## Целью лабораторной работы является знакомство с CI/CD и его реализацией на примере Travis CI и Heroku.

## Ссылка на развернутое приложение: 
- [Поулчение status в приложении](https://simplelab-bg.herokuapp.com/api/v1/status) 
- [Получение всех продуктов](https://simplelab-bg.herokuapp.com/api/v1/product)
- [Получение конкретного продукта](https://simplelab-bg.herokuapp.com/api/v1/product/4)