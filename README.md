## Running the app

just go to the root folder and execute

```
gradle bootRun
```

## TODO

Because the exercise was taking too much time there are some still TODOs:

- some test are not completed, because the exercise was taking too much time
- validations are not performed when the merchant post an offer


## Managing the offers
### Create an offer

perform a POST to localhost:8080/offer with the payload

```json
{
    "name": "name1",
    "expirityDate": "2018-08-08",
    "description" : "blah blah",
    "discount" : 21.23,
    "discountPercentage" : 1.50
}
```

you should receive a 201 response containing the payload

```json
{
    "id": 1,
    "name": "name1",
    "creationDate": "2018-06-23",
    "expirityDate": "2018-08-08",
    "discount": 21.23,
    "discountPercentage": 1.5,
    "status": "VALID",
    "description": "blah blah"
}
```

Note that fields id, status and creationDate are created automatically

### Query all offers

perform a get to localhost:8080/offer you should receive a list of offers

### Query a specific offer

perform a get to localhost:8080/offer/{id} you should receive an offer

### Cancel an offer

perform a put to localhost:8080/offer/{id}/actions/cancel you should receive an offer

## DB access

A H2 memory database is created when the application starts to connect go to

```
http://localhost:8080/h2-console
```

and then specify:

```
Driver class: org.h2.Driver
JDBC URL: jdbc:h2:mem:testdb
user: sa
password: sa
```


