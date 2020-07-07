# Inventory Viewing System

Spring Boot Application built using WebSocket messaging and Angular UI to dynamically display the inventory records.

### Inventory Service App

This Project contains the server side application built using Spring Boot, Stomp and SockJS using WebSocket based communication.
The WebSocket protocol is one of the ways to make the application handle real-time messages. It allows to implement bidirectional communication between applications. 

App has the below features,

  - REST APIs to Get, Add, Update and Delete Inventory Records
  - A SockJS endpoint and configured message broker
  - Publishes data with SockJS and Stomp during Add, Update and Delete
  - In memory DB which can store the inventory records
  - Database Access Layer test cases

#### Tech

* Java 8
* Spring Boot
* Stomp
* SockJS
* Lombok
* Swagger
* H2
* Junit
* Maven

### Inventory Viewer Web App

This Project contains the Web UI which subscribes and exchanges the messages over a WebSocket and a data table which can dynamically display all the inventory records.

App has the below features,

  - Angular UI with Table which can dynamically detect events and updates without page load
  - Creates SockJS server and connects to stomp client
  - Subscribes to created message broker asynchronously with stomp client
  - If there are any event from the backend, it updates data table dynamically
  - Invokes REST API to fetch inventory records from the Backend Service app

#### Tech

* Angular 10
* Bootstrap
* stompjs
* sockjs-client
* jquery 

### Installation

To start the service, navigate to `inventory-service-app` and run below command,

```sh
mvn clean install test spring-boot:run
```

Note: It requires JRE 8 and maven to be installed in the local machine.
It starts the Spring Boot Application at `port 8080`.

Swagger Documentation to access the APIs

```sh
http://localhost:8080/swagger-ui.html
```

To start the web app, navigate to `inventory-viewer-webapp` and run below command,

```sh
npm install
ng serve
```
Note: It requires angular cli and node js to be installed in the local machine.
It starts the Angular Application at `port 4200`.

### Test the Application

The Angular Web Application running in `http://localhost:4200/` will load initial data by accessing the get Inventory API from the backend service at `http://localhost:8080`

API to add inventory record,

```json
POST: http://localhost:8080/api/inventory
{
    "sku": "S17T-BKB-RM",
    "name": "Blackberry 2021",
    "count": 23
}
```

Upon adding a new Inventory, UI application will automatically detect the event and dynamically load the data table without any page refresh.

The Data table component will refresh automatically for all events created from the backend service during create, delete and update inventory records.