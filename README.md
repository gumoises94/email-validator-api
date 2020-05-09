# email-validator-api
An API to validate an email address


## How it works

The API validates if the entered email address is a valid email, by checking:
- If the address follows the basic email format (example@gmail.com)
- If the email's domain is a public domain (otherwise, it will check if the domain is valid, by sending a request to the website)

## Getting started

#### Make the request to the API:

```
curl -v {your_api_address}/validation/example@live.com
```
or you can use my api in https://gumoises94-email-validator-api.herokuapp.com

#### Response Model - Example Value

```
{
    "email": "example@live.com",
    "isPublicDomain": true,
    "isCompanyDomain": false,
    "validationStatus": "VALID",
    "message": null
}
```

- **email**: The email you've sent in the request
- **isPublicDomain**: Will be true if the email's domain is a public domain
- **isCompanyDomain**: Will be true if the email's domain is a company's domain
- **validationStatus**: VALID or INVALID
- **message**: Message for invalids emails and server errors

## About the project

This project is for coding practice purpose. Feel free to reuse or share for your own matters. Contributions are welcome.

## Technical Details

Frameworks/APIs used:

- [Spring Boot](https://github.com/spring-projects/spring-boot)
- [Project Lombok](https://github.com/rzwitserloot/lombok)
- [JUnit](https://github.com/junit-team/junit5)



