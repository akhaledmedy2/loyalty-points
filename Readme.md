# Getting Started

## Overview
* This microservice is to calculate rewarded points for each transaction and responsible for saving transaction details with its specific customer also is responsbile for our existing customers in the system with saving calculated points for each customer per month

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
* Run 'mvn spring-boot:run'

### How to test
* Attached postman collection Loyalty Points.postman_collection.json
* Or test with http://{serverHost}:8080/swagger-ui/index.html
* Testing data 
	*'ahmed', 'khaled' and 'fouad' are three usernames for customers existing in our database
* Testing endpoint
	* Create transaction  POST '/transaction', pass username from one of these customers usernames and transaction amount
	* Update transaction PUT '/transaction', pass transaction_id from create transaction endpoint response, transaction amount and transaction status (0 -> PAID, 1 -> PENDING_PAYMENT, 2 -> REFUND)
	* Customer report  GET '/customer/report', run to get rewarded points for each customer in previous 3 months