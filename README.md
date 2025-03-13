# kt-tools

## Modules

### Healthcheck
A simple module to indicate the health of the API and whether it is UP.

#### Usage
1. Add the import and dependency in your `build.gradle`:
    ```groovy
    @Import(HealthCheckConfig::class)
    ```

### Security
A module providing standard security configurations for Spring Boot APIs.

#### Usage
1. Add the library to your project.
2. Set the environment variables `auth.access-token-secret` and `auth.api-key`.

## Publishing a New Version

To generate a new version of the library, update the version in `build.gradle` and run the appropriate command for the desired module:

- For Healthcheck module:
    ```sh
    ./gradlew publishHealthcheckPublicationToGitHubPackagesRepository
    ```

- For Security module:
    ```sh
    ./gradlew publishSecurityPublicationToGitHubPackagesRepository
    ```
