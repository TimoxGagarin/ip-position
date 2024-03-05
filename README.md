# IP Position API

## Описание API

API предоставляет информацию об IP-адресах, городах, провайдерах и их взаимосвязях. Возможности включают получение данных из внешнего API, работы с базой данных, добавление, удаление и обновление информации.

## Dependencies (Зависимости)

Проект использует следующие зависимости:

- [Spring Boot](https://spring.io/projects/spring-boot)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [H2 Database](https://www.h2database.com)
- [Spring Web](https://spring.io/guides/gs/serving-web-content/)
- [Jakarta Persistence API (JPA)](https://jakarta.ee/specifications/persistence/)
- [RestTemplate](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/client/RestTemplate.html)

## Как запустить

1. Склонируйте репозиторий:

    ```bash
    git clone https://github.com/TimoxGagarin/ip-position.git
    ```

2. Перейдите в каталог проекта:

    ```bash
    cd ip-position
    ```

3. Соберите проект:

    ```bash
    mvn clean install
    ```

4. Запустите приложение:

    ```bash
    mvn spring-boot:run
    ```

   Приложение будет доступно по адресу [http://localhost:8080](http://localhost:8080).

## Конечные точки

### 1. Получить всю информацию об IP-адресах

- **Конечная точка:** `GET /api/ip/get/all`
- **Описание:** Получить список всех IP-адресов с дополнительной информацией.

### 2. Получить информацию об IP-адресе из внешнего API

- **Конечная точка:** `GET /api/ip/get/external_api?ip={ip_address}`
- **Описание:** Получить информацию об IP-адресе из внешнего API.
- **Параметры:**
  - `ip_address`: IP-адрес.

### 3. Получить информацию об IP-адресе из базы данных

- **Конечная точка:** `GET /api/ip/get/db?ip={ip_address}`
- **Описание:** Получить информацию об IP-адресе из базы данных.
- **Параметры:**
  - `ip_address`: IP-адрес.

### 4. Получить провайдеров, предоставляющих услуги в указанном городе

- **Конечная точка:** `GET /api/ip/get/providers?cityName={city_name}`
- **Описание:** Получить список провайдеров в указанном городе.
- **Параметры:**
  - `cityName`: Название города.

### 5. Получить города, обслуживаемые указанным провайдером

- **Конечная точка:** `GET /api/ip/get/cities?providerIsp={provider_isp}`
- **Описание:** Получить список городов, обслуживаемых указанным провайдером.
- **Параметры:**
  - `providerIsp`: Название провайдера (ISP).

### 6. Добавить новую информацию об IP-адресе

- **Конечная точка:** `POST /api/ip/post`
- **Описание:** Добавить новую информацию об IP-адресе.
- **Тело запроса:** JSON-объект с данными об IP-адресе.

### 7. Удалить информацию об IP-адресе

- **Конечная точка:** `DELETE /api/ip/delete?id={ip_info_id}`
- **Описание:** Удалить информацию об IP-адресе.
- **Параметры:**
  - `id`: Идентификатор IP-адреса.

### 8. Обновить информацию об IP-адресе

- **Конечная точка:** `PUT /api/ip/put?id={ip_info_id}`
- **Описание:** Обновить информацию об IP-адресе.
- **Параметры:**
  - `id`: Идентификатор IP-адреса.

## Как использовать

1. **Получение всей информации об IP-адресах:**
   - `GET /api/ip/get/all`

2. **Получение информации об IP-адресе из внешнего API:**
   - `GET /api/ip/get/external_api?ip={ip_address}`
   - **Пример:** `GET /api/ip/get/external_api?ip=8.8.8.8`

3. **Получение информации об IP-адресе из базы данных:**
   - `GET /api/ip/get/db?ip={ip_address}`
   - **Пример:** `GET /api/ip/get/db?ip=8.8.8.8`

4. **Получение провайдеров, предоставляющих услуги в указанном городе:**
   - `GET /api/ip/get/providers?cityName={city_name}`
   - **Пример:** `GET /api/ip/get/providers?cityName=New York`

5. **Получение городов, обслуживаемых указанным провайдером:**
   - `GET /api/ip/get/cities?providerIsp={provider_isp}`
   - **Пример:** `GET /api/ip/get/cities?providerIsp=Comcast`

6. **Добавление новой информации об IP-адресе:**
   - `POST /api/ip/post`
   - **Тело запроса:** JSON-объект с данными об IP-адресе.

7. **Удаление информации об IP-адресе:**
   - `DELETE /api/ip/delete?id={ip_info_id}`
   - **Пример:** `DELETE /api/ip/delete?id=1`

8. **Обновление информации об IP-адресе:**
   - `PUT /api/ip/put?id={ip_info_id}`
   - **Пример:** `PUT /api/ip/put?id=1`