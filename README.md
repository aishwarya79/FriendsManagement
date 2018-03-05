# Friends Management

Friends Management application is a simple social networking platform that allows varios users to interact with each other. Currently the application supports the following features :

  - Add Friends
  - Subscribe to another user's updates
  - Block a user

# Technical Design

#### API Server
Friends Management is a Spring Boot Application that uses Spring JPA for persistence. It is a RESTful API that accepts HTTP Request, processes it,  and returns HTTP Response.
- The incoming requests are handled by a **_RestController_**, which performs some basic input validations and invokes the Service layer. The Controller is also responsible for transforming the HTTP Request/Request to domain objects.
- Business logic is implemented at the **_Service_** layer, which processes the request, interacts with the persistence layer if required, and returns the response to the Controller. 
- The **_Repository_** layer is responsible for all Data Access operations
- All Exceptions thrown in the application are handled by **_ControllerAdvice_** 

#### Database 
The application uses a **_Postgres_** Database to stores all the registered users and their relationships. Below tables are used by the application :
- **USERS** 
Stores all the users' details  of Friends Management app, user id being the unique idetifier
- **FRIENDSHIP**
Stores the ids of users who are added as friends
Has a foreign key relationship with *USERS* table
- **SUBSCRIPTION**
Stores the subscriber and subscribed user ids 
Has a foreign key relationship with *USERS* table
- **BLOCKED_USERS**
Stores the requestor and blocked user ids
Has a foreign key relationship with *USERS* table

Please refer to the [DDL Scripts](https://github.com/aishwarya79/FriendsManagement/blob/master/database/friendsmanagement.sql) for detailed schema.

### API Documentation

Detailed API documentation is available at [API Documentation](https://raw.githubusercontent.com/aishwarya79/FriendsManagement/master/api_docuentation.md).

Use Case | Http Method | API |
| ------ | ------ | ------ | 
| Add a Friend | POST | [/friendsmanagement/api/v1/friends/connect] |
| Get all Friends | POST | [/friendsmanagement/api/v1/friends/list] |
| Get Common Friends | POST | [/friendsmanagement/api/v1/friends/common]|
| Subscribe to Updates | POST | [/friendsmanagement/api/v1/users/subscribe]|
| Block a User | POST | [/friendsmanagement/api/v1/users/block] |
| Get all Recipients of an update | POST | [/friendsmanagement/api/v1/notification/recipients]|

Please refer to the [Postman Collection](https://raw.githubusercontent.com/aishwarya79/FriendsManagement/master/FriendsMgmt.postman_collection.json) for Request and Response samples.

### Running the Application
Friends Management App is already deployed on AWS cloud on a Linux instance. Import the Postman Collection provided to test the app.

To run the application on your local linux/mac machine , follow the below steps : 
1. Clone the repo from Github to your local machine. Navigate to application root folder.

2. Build the application using:
 	```
	mvn clean install 
	```
3. Update the postgres host in src/main/resources/application.properties. You can use the postgres database deployed on the aws instance. 
   Replace _localhost_ with _ec2-18-219-235-77.us-east-2.compute.amazonaws.com_
   *spring.datasource.url*=jdbc:postgresql://localhost:5432/postgres?currentSchema=spgroup

4. Run the application by running the following script:
	```
	sh run.sh 
	```