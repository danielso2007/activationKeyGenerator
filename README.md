# Activation Key Generator

[![Build Status](https://travis-ci.org/danielso2007/activationKeyGenerator.svg?branch=master)](https://travis-ci.org/danielso2007/activationKeyGenerator) ![GitHub repo size](https://img.shields.io/github/repo-size/danielso2007/activationKeyGenerator.svg) ![GitHub All Releases](https://img.shields.io/github/downloads/danielso2007/activationKeyGenerator/total.svg) ![GitHub issues](https://img.shields.io/github/issues/danielso2007/activationKeyGenerator.svg) ![GitHub followers](https://img.shields.io/github/followers/danielso2007.svg) ![GitHub package.json version](https://img.shields.io/github/package-json/v/danielso2007/activationKeyGenerator.svg?color=green) ![GitHub language count](https://img.shields.io/github/languages/count/danielso2007/activationKeyGenerator.svg) ![GitHub top language](https://img.shields.io/github/languages/top/danielso2007/activationKeyGenerator.svg)

Study to create a system that generates an activation key and a framework to use it. The key will be created using JWT and the framework will check if the generated key is valid or if its use has expired.
The goal is to study a better case for activation key usage for Java applications.

The system will use the World Time API to check the expiration date, without having to use the local date and time where the framework is installed.
As stated above, it is just a study and suggestions and criticism are welcome.

# Running project via Maven

You must have installed on your system:
- Java 11
- Apache Maven 3.6.1 (or higher)

### Running service project

To run the project, run the command:

`cd activationKeyGenerator-service/`

`mvn clean package spring-boot:run`

By default, the local profile is run, where tests are skipped. If you want to run the production profile, pass as profile:

`mvn clean package spring-boot:run -Pprod`

### Testing the service project

The project is set to skip the tests. To run the tests, run the command below by passing the test profile:

`mvn clean package`

`mvn test -Ptest`

# Running project by IntelliJ IDEA Community

In Run/Debug Configurations.

### Create a configuration of type Application, and fill in the fields:

- **Name**: `Project`
- **Main class**: `com.akg.ActivationKeyGeneratorApplication`
- **Working directory**: `(Your project path)/activationKeyGenerator`
- **Use classpath of module**: `activationKeyGenerator-service`
- **JRE**: `11`
- **Before launch**: `Build and Run Maven Goal (clean package)`

### Creating Maven Configuration:

### To run the project:

- **Name**: `Run Project`
- **Working directory**: `(Your project path)/activationKeyGenerator/activationKeyGenerator-service`
- **Command line**: `clean package spring-boot: run`
- **Profiles**: `local`

### To run project tests:

- **Name**: `Run Test`
- **Working directory**: `(Your project path)/activationKeyGenerator`
- **Command line**: `test`
- **Profiles**: `test`

# Running the service to reach activation key:

- `createtoken id issuer subject secretykey timeExpiration`
  - `id`: The token identifier;
  - `issuer`: The issuer of the token;
  - `subject`: For whom the token will be created (Suggestion);
  - `secretyKey`: Secret key to decode this token;
  - `timeExpiration`: Time, in milliseconds, for token validation.
  
Example:
```shell
java -jar activationKeyGenerator-service.jar createtoken 123 test me KJASHDKJSAD 2000
```

- `createfile token filedestination`
  - `token`: The token that will be inserted into the file;
  - `fileDestination`: Path where the file will be created.

Example:
```shell
java -jar activationKeyGenerator-service.jar KJASGDKHS /home/user/active.key
```

- `readKeyfile fileDestination`
  - `fileDestination`: The path to read file only. It will present the content that is inside the file;

Example:
```shell
java -jar activationKeyGenerator-service.jar readKeyfile /home/user/active.key
```

- `decode token secretkey`
  - `token`: The token that will be decoded;
  - `secretkey`: The secret key used to encode the token.

Example:
```shell
java -jar activationKeyGenerator-service.jar decode KJASHDKJASH TTER5612
```

- `tokenexpiration token secretkey`
  - `token`: The token that will be decoded;
  - `secretkey`: The secret key used to encode the token.

Example:
```shell
java -jar activationKeyGenerator-service.jar decode KJASHDKJASH TTER5612
```

- `help`
  - Displays some basic command.

Example:
```shell
java -jar activationKeyGenerator-service.jar help
```

# Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/2.1.6.RELEASE/reference/htmlsingle/#using-boot-devtools)
