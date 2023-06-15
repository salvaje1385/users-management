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

![Screenshot_20230615_133437](https://github.com/salvaje1385/users-management/assets/36721058/b9b70a2c-6e71-4568-9e14-eb269e1259c2)

1. First you'll need to call the "**POST** users/create" endpoint, to create the first user.

![Screenshot_20230615_132931](https://github.com/salvaje1385/users-management/assets/36721058/d9e77450-7982-49bb-9096-c15cb5edcae7)

2. Then you'll call the "**POST** users/login" endpoint, to login the user and get the Bearer Token, which will be used to authorize the other endpoints.

![Screenshot_20230615_133606](https://github.com/salvaje1385/users-management/assets/36721058/7d7d0d88-d28f-40ef-86ae-8e8c502932cc)

3. Then paste the token in the Collection's Authorization tab, and click the Save button. Now you'll be able to access all the other endpoints.

![Screenshot_20230615_134610](https://github.com/salvaje1385/users-management/assets/36721058/b8f374ea-0df6-4f3c-99ae-44997fdcc5bb)

4. Take into account that when testing the "**PUT** users" endpoint, you won't be able to update the userId, email or password.

# Test with Swagger-ui

You can test the application using Swagger (http://localhost:8080/swagger-ui.html). Here you can follow the same steps mentioned above: Create the user, login, and use the token to hit the other endpoints.

Note that you need to paste the token in the Authorization text box using the prefix "Bearer ".

![Screenshot_20230615_140215](https://github.com/salvaje1385/users-management/assets/36721058/a31b8d4a-5c6c-4a99-8f85-d633425c2c2c)

