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

![Screenshot_20230615_133437.png](..%2F..%2F..%2FDownloads%2FScreenshot_20230615_133437.png)

1. First you'll need to call the "**POST** users/create" endpoint, to create the first user.

![Screenshot_20230615_132931.png](..%2F..%2F..%2FDownloads%2FScreenshot_20230615_132931.png)

2. Then you'll call the "**POST** users/login" endpoint, to login the user and get the Bearer Token, which will be used to authorize the other endpoints.

![Screenshot_20230615_133606.png](..%2F..%2F..%2FDownloads%2FScreenshot_20230615_133606.png)

3. Then paste the token in the Collection's Authorization tab, and click the Save button. Now you'll be able to access all the other endpoints.

![Screenshot_20230615_134610.png](..%2F..%2F..%2FDownloads%2FScreenshot_20230615_134610.png)

4. Take into account that when testing the "**PUT** users" endpoint, you won't be able to update the userId, email or password.

# Test with Swagger-ui

You can test the application using Swagger (http://localhost:8080/swagger-ui.html). Here you can follow the same steps mentioned above: Create the user, login, and use the token to hit the other endpoints.

Note that you need to paste the token in the Authorization text box using the prefix "Bearer ".

![Screenshot_20230615_140215.png](..%2F..%2F..%2FDownloads%2FScreenshot_20230615_140215.png)