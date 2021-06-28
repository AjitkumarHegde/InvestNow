# InvestNow

A SpringBoot sample application(mutual funds themed) with JWT authentication using Spring Security and MySQL JPA. 

Key Features

- User Signup, Login(DB authentication) using Spring Security and JWT token generation. 
- User Role based API access.
- For logged out users, in-memory token blacklisting. Uses Spring Boot application event listeners to latch on to a user logout event.
- Containerized application along with an MySQL container(Using docker-compose).
- Swagger UI to test the APIs and Spotless for code formatting.
- Attached postman collection along with repository for testing.

Functionality

- As part of the application startup, initialize fund category, fund sector, and funds into the datastore. Also, preseed roles and a default (admin) user.
- Signup into the application by providing necessary details and login as the newly created user to generate a token which needs to be passed as the Authorization Header going forward.
- User can now access APIs to view fund categories, fund sectors, and funds and can invest in a fund using /invest API by providing necessary details.
- In order to add a sector/category/fund, login as admin user.  

Usage:

```
To run InvestNow application and MySQL containers in background -
docker-compose up -d

To run as a standalone SpringBoot application, update the database connection properties.
```

Scope/RoadMap

- Refresh token functionality.
- OAuth support for user login.
- Full pledged mutual funds application features.
