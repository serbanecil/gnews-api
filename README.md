# Simple News API

## Overview

This project represent a simple API used to retrieve news articles.
It uses the API from [GNews](https://www.gnews.io) with which you can search for news articles.

## Obtaining an API Key for using the [GNews-API](https://www.gnews.io)
In order to use the GNews-API you need to obtain an api key from them.
1. Go to their [Website](https://www.gnews.io) and create an account.
2. After the account was successfully created, go to [Your Dashboard](https://gnews.io/dashboard) and copy the API key on the right hand side.
3. Edit the `application.properties` file under `resources` and replace the current value of the property `client.apiKey` with your api key.
4. Save the changes and you are good to go


## Local Installation
1. Build the service using the maven wrapper: `mvnw clean install` or `./mvnw clean install`
2. Run the application using the maven wrapper: `mvnw spring-boot:run` or `./mvnw spring-boot:run`

## Tests
Run all tests using `mvnw test` or `./mvnw test`

## REST API
The supported REST API can be easily verified via the swagger entry point that is reachable under: `http://localhost:8080/news/swagger-ui/index.html`



