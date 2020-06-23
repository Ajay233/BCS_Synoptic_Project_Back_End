# Quiz Manager API

## How to run

First create the database:
- docker-compose up -d

Then run the app using the maven command:
- mvn spring-boot:run

To quit the app use:
- ctrl + c

Then to close the DB container use:
- docker-compose down

Once the app is up and running, open your browser and copy and paste `http://localhost:8080` into your browser address bar.

## Swagger API documentaion

To access the Swagger documentation for the quiz manager API simply copy and paste the following url into your browser address bar,
once you have started the app using the instructions in the 'how to run' section: `http://localhost:8080/swagger-ui.html#`

### how to set up a default auth header

### How to use