# Getting Started

## Overview
* This microservice is to calculate rewarded points for each transaction
* Transaction table is responsible for saving transaction details with its specific customer
* Customer table is for our existing customers in the system
* Rewarded points table is for saving calculated points for each customer per month
* In transaction rest controller, there is 2 endpoints
  * One for create new transaction with pending status
  * One for update transaction that commits the transaction and calculate its reward points
* In customer rest controller, the report endpoint is to get rewarded points for each customer in the previous quarter of year
* Report endpoint is paginated to avoid load all records at once
* Some attributes have validation like (amount = 0, empty strings, etc)
* If some data not found in database, exception will be thrown like (400)

### project setup
* Spring boot: 2.7.2
* Java JDK: 1.8
* H2 in-memory database installed for this project
* Dummy and sample data added for testing purposes

### How to build
* Open cmd
* Run 'mvn clean package' command to run with testing

### How to run tests
* Open cmd
* Run 'mvn test'

### How to run
* Open cmd
* Run 'mvn spring-boot:run' to run application as spring boot mvn run

### How to test
* Attached postman collection import it then test endpoints
* Or test with [swagger-api](http://localhost:8080/swagger-ui/index.html)
* For tracing logs, check cmd console or open logs file in /log folder
* 'ahmed', 'khaled' and 'fouad' are three usernames for customers existing in our database
* Create transaction test, use one of these customers username to pass to create transaction 
* Update transaction test, use transaction id from create transaction endpoint to pass it in update transaction endpoint
* Transaction status field is numeric field for setting transaction status (0 -> PAID, 1 -> PENDING_PAYMENT, 2 -> REFUND)
* Customer report test, its GET mapping endpoint just run to get rewarded points for each customer in previous 3 months