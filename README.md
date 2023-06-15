# users-management

This is a RESTful API to Manage Users using Spring Boot and JWT.

# Tools

- Java 8+
- Spring Boot
- JWT
- Lombok
- Mapstruct
- h2 DB

# Resources

- http://localhost:8080/h2-console/ (h2 Database Console)
- http://localhost:8080/swagger-ui.html (You can test the endpoints in Swagger-ui)

# Build and run

To build and run the application run the following commands, respectively:

```
mvn clean install
mvn spring-boot:run
```

# How to test

**1. DB Scripts**

You don't need a DB script to start the DB, as the App is using h2 DB, and will start working as soon you start hitting the endpoints.

**2. Postman Collection**

You can find the Postman Collection file in the project's root folder, it's callled "**user-management.postman_collection.json**".

**3. Example**

This is the list of available endpoints:

![Screenshot_20230615_133437](https://github.com/salvaje1385/users-management/assets/36721058/14a975f7-ce7c-4297-be56-5f50e2b372e0)

1. First you'll need to call the "**POST** users/create" endpoint, to create the first user.

![Screenshot_20230615_132931](https://github.com/salvaje1385/users-management/assets/36721058/2ea2a188-0605-4b67-a24d-c562fce1037d)

2. Then you'll call the "**POST** users/login" endpoint, to login the user and get the Bearer Token, which will be used to authorize the other endpoints.

![Screenshot_20230615_133606](https://github.com/salvaje1385/users-management/assets/36721058/bb91e519-4a94-4b8f-bb99-3a36e2a937d4)

3. Then paste the token in the Collection's Authorization tab, and click the Save button. Now you'll be able to access all the other endpoints.

![Screenshot_20230615_134610](https://github.com/salvaje1385/users-management/assets/36721058/8ce0886a-a35c-4663-9112-cd3db2d8c1a1)

4. Take into account that when testing the "**PUT** users" endpoint, you won't be able to update the userId, email or password.

# Test with Swagger-ui

You can test the application using Swagger (http://localhost:8080/swagger-ui.html). Here you can follow the same steps mentioned above: Create the user, login, and use the token to hit the other endpoints.

Note that you need to paste the token in the Authorization text box using the prefix "Bearer ".

![Screenshot_20230615_140215](https://github.com/salvaje1385/users-management/assets/36721058/2f816f00-00ed-48e8-9ca6-4c1d083a0bd6)

