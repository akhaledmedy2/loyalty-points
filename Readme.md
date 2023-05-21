# Getting Started

## Overview
This microservice is to calculate rewarded points for each transaction and responsible for saving transaction details with its specific customer also is responsbile for our existing customers in the system with saving calculated points for each customer per month

### project setup
* Spring boot: 2.7.2
* Java JDK: 1.8
* H2 in-memory database installed for this project
* Dummy and sample data added for testing purposes

### How to build
* Run 'mvn clean package'

### How to execute unit and integration tests
* Run 'mvn test'

### How to run
* Run 'mvn spring-boot:run -Dspring-boot.run.arguments=--server.port=8080'

### How to test
* Attached postman collection Loyalty Points.postman_collection.json
* Attached swagger url http://{serverHost}:{serverPort}/swagger-ui/index.html
* Testing data 
	*username -> 'ahmed', 'khaled', 'fouad'
* For Testing endpoint, check postman collection
	* Create transaction  POST 'http://{serverHost}:{serverPort}/transaction'
	* Update transaction PUT 'http://{serverHost}:{serverPort}/transaction', transaction_id from create transaction endpoint response, transaction status (0 -> PAID, 1 -> PENDING_PAYMENT, 2 -> REFUND)
	* Customer report  GET '/customer/report'