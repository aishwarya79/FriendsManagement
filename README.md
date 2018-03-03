# Friends Management

Friends Management application is a simple social networking platform that allows varios users to interact with each other. Currently the application supports the following features :

  - Add Friends
  - Subscribe to another user's updates
  - Block a user

# Technical Design

#### API Server
Friends Management is a Spring Boot Application that uses Spring JPA for persistence. It is a RESTful API that accepts HTTP Request, processes it,  and returns HTTP Response.
- The incoming requests are handled by a **_RestController_**, which performs some basic input validations and invokes the Service layer. The Controller is also responseible for transforming the HTTP Request/Request to domain objects.
- Business logic is implemented at the **_Service_** layer, which processes the request, interacts - with the persistence layer if required, and returns the response to the Controller. 
- The **_Repository_** layer is responsible for all Data Access operations
- All Exceptions thrown in the application are handled by **_ControllerAdvice_** 

The figure below depicts the application flow.



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

Please refer to the [DDL Scripts](https://nodejs.org/) for detailed schema

### API Documentation

Dillinger is currently extended with the following plugins. Instructions on how to use them in your own application are linked below.

Use Case | Http Method | API |
| ------ | ------ | ------ | 
| Add a Friend | POST | [/friendsmanagement/api/v1/friends/connect] |
| Get all Friends | POST | [/friendsmanagement/api/v1/friends/list] |
| Get Common Friends | POST | [/friendsmanagement/api/v1/friends/common]|
| Subscribe to Updates | POST | [/friendsmanagement/api/v1/users/subscribe]|
| Block a User | POST | [/friendsmanagement/api/v1/users/block] |
| Get all Recipients of an update | POST | [/friendsmanagement/api/v1/getRecipients]|

Please refer to the [Postman Collection](https://nodejs.org/) for Request and Response samples.

### Running the Application
