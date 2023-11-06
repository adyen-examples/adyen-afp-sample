# Adyen for Platforms sample application

## Intro

Adyen for Platforms is an end-to-end payment solution for peer-to-peer marketplaces, on-demand services, crowdfunding platforms, and any other platform business models.

This sample application is a simplified version of a platform built on top of AfP, supporting the following use cases:
* signup and start Hosted Onboarding
* login and resume Hosted Onboarding

The frontend is implemented in React, the backend is in Java (Spring Boot) leveraging the Adyen Java library 
([GitHub](https://github.com/Adyen/adyen-java-api-library) | [Documentation](https://docs.adyen.com/development-resources/libraries?tab=java_2)).

## Requirements

- [Adyen API Credentials](https://docs.adyen.com/development-resources/api-credentials/)
- Java 17
- Network access to Maven central

## 1. Installation

```
git clone https://github.com/adyen-examples/adyen-afp-sample.git
```

## 2. Set the environment variables
* [API key](https://docs.adyen.com/user-management/how-to-get-the-api-key)
* [Client Key](https://docs.adyen.com/user-management/client-side-authentication)
* [Merchant Account](https://docs.adyen.com/account/account-structure)
* [HMAC Key](https://docs.adyen.com/development-resources/webhooks/verify-hmac-signatures)

On Linux/Mac/Windows export/set the environment variables.
```shell
export BCL_API_KEY=yourAdyenApiKey
export LEM_API_KEY=yourAdyenMerchantAccount
export ADYEN_HMAC_KEY=yourHmacKey
export HOSTED_ONBOARDING_THEME_ID=yourHmacKey
```

Alternatively, it's possible to define the variables in the `application.properties`.
```txt
# API KEY for accessing Balance Platform Configuration API
BCL_API_KEY=

# API KEY for accessing LEM API
LEM_API_KEY=

# HMAC key to authenticate incoming webhook requests
ADYEN_HMAC_KEY=

# Id of the Hosted Onboarding Theme created in the Customer Area
HOSTED_ONBOARDING_THEME_ID=
```