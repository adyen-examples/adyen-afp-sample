# Adyen for Platforms (AfP) Sample Application

## Intro

Adyen for Platforms is an end-to-end payment solution for peer-to-peer marketplaces, on-demand services, crowdfunding platforms, and any other platform business models.

This application is a simplified version of a platform that utilises AfP, showcasing the following use cases:
* signup and start Hosted Onboarding
* login and resume Hosted Onboarding

The frontend is implemented in React, the backend is in Java (Spring Boot) integrating the Adyen Java library 
([GitHub](https://github.com/Adyen/adyen-java-api-library) | [Documentation](https://docs.adyen.com/development-resources/libraries?tab=java_2)).

## Prerequisites

- Java 17
- Maven 3

## 1. Installation

```
git clone https://github.com/adyen-examples/adyen-afp-sample.git
```

## 2. Set the environment variables
* [BCL API key](https://docs.adyen.com/marketplaces-and-platforms/get-started/): API key for accessing Balance Platform [Configuration API](https://docs.adyen.com/api-explorer/balanceplatform/latest/overview)
* [LEM API key](https://docs.adyen.com/marketplaces-and-platforms/get-started/): API key for accessing [Legal Entity Management API](https://docs.adyen.com/api-explorer/legalentity/latest/overview)
* [HMAC key](https://docs.adyen.com/development-resources/webhooks/verify-hmac-signatures): HMAC key to validate incoming webhook requests
* [Hosted Onboarding Theme id](https://docs.adyen.com/marketplaces-and-platforms/collect-verification-details/hosted/customize-hosted-onboarding/): id of the Hosted Onboarding Theme created in the Customer Area

On Linux/Mac/Windows export/set the environment variables.
```shell
export BCL_API_KEY=yourBclApiKey
export LEM_API_KEY=yourLemApiKey
export ADYEN_HMAC_KEY=yourHmacKey
export HOSTED_ONBOARDING_THEME_ID=yourHostedOnboardingThemeId
```

Alternatively, it's possible to define the variables in the `application.properties`.
```txt
BCL_API_KEY=yourBclApiKey
LEM_API_KEY=yourLemApiKey
ADYEN_HMAC_KEY=yourHmacKey
HOSTED_ONBOARDING_THEME_ID=yourHostedOnboardingThemeId
```

## 3. Run the application

#### Run React and Java
Run separately frontend and backend:
```shell
cd react-app
npm start
```

```shell
mvn spring-boot:run
```

Access [http://localhost:3000/](http://localhost:3000/)

#### Build the application
Build the SpringBoot jar that contains the entire application and run it:
```   
mvn package
java -jar target/adyen-afp-sample.jar
```

Access [http://localhost:8080/](http://localhost:8080/)

# Webhooks

Webhooks deliver asynchronous notifications about the onboarding status and other events that are important to receive and process.  
Check out how to [setup a webhook](https://docs.adyen.com/marketplaces-and-platforms/webhooks/) 
and learn tips and best practises in [this blog post](https://www.adyen.com/knowledge-hub/consuming-webhooks).

### Webhook setup

In the [Balance Platform Customer Area](https://balanceplatform-test.adyen.com/balanceplatform) in the `Developers → Webhooks` section, 
create a new `Configuration` webhook.

A good practice is to set up basic authentication, copy the generated HMAC Key and store it as an environment variable. 
The application will use this to ensure the integrity of the incoming webhook requests.

Make sure the webhook is **enabled**, so it can receive notifications.

### Expose an endpoint

This demo provides a simple webhook implementation (`WebhookController` class) that processes the requests sent to `/api/webhooks/notifications` 
and demonstrates how to receive, validate and consume the webhook payload.

### Test your webhook

To make sure that the Adyen platform can reach your application, we have written a [Webhooks Testing Guide](https://github.com/adyen-examples/.github/blob/main/pages/webhooks-testing.md)
that explores several options on how you can easily achieve this (e.g. running on localhost or cloud).