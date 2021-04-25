# DNA-Mutant-Analyzer

Service for mutant/human detection based on their DNA sequence represented in a matrix type

## Requisites

### Build management

`Gradle 6`

### Database

A PostgreSQL database, si far it si set in `postgres/public` schema bit it can ber changed in
the `application.properties` file

For local/testing purposes, the Database must be running in local with default port and user /
password:

#### Local

* **URL:** jdbc:postgresql://localhost:5432/postgres
* **Username:** postgres
* **Password:** postgres

For cloud environments, the `ENV` variables must set with the configuration

* **URL:** `DATABASE_URL` _i.e.: postgres://user:pass@cloud-service:5432/database_
* **Username:** `DATABASE_USER` _i.e.: user_
* **Password:** `DATABASE_PASS` _i.e.: password_

## Running the service

### Local

To run the service in _local_ environment, you can run the command:

```shell
spring_profiles_active=local ./gradlew clean build bootRun
```

where `spring_profiles_active` set the `env` variable to set the active profile, this case `local`

### Cloud

To run the service in _cloud_ environment, you can run the command:

```bash
java -jar DNA-Mutant-Analyzer-0.0.1-SNAPSHOT.jar
```

where `DNA-Mutant-Analyzer-0.0.1-SNAPSHOT.jar` is the jar build with `gradle` and can be found
in `build/libs` and it needs to have the _Database env variables_ set

## Using the service

### Analyze mutant

To analyze a DNA sequence if it is mutant or not, it is necessary to use a `POST` request with the
following:

#### Request

`POST`: `{server}:{port}/mutant` _i.e.: http://localhost:8080/mutant_

#### Headers

* `Content-Type: application/json`

#### Body

```json
{
  "dna": [
    "ATGCGA",
    "CAGTGC",
    "TTATGT",
    "AGAAGG",
    "CCCCTA",
    "TCACTG"
  ]
}
```

#### Response

* `true`
  OR
* `false`

where `true`means that the DNA sequence is from a mutant and `false` means that the DNA sequence is
from a human

### Retrieve stats

The stats retrieve the information of how many DNA sequences has been analyzed with result for _
mutant_ and how many with result for _human_, you need to use a `GET` request to do it with
following:

#### Request

`GET`: `{server}:{port}/stats` _i.e.: http://localhost:8080/stats_

#### Headers

* `Content-Type: application/json`

#### Response

```json
{
  "count_mutant_dna": 1,
  "count_human_dna": 1,
  "ratio": 1.5
}
```

where

* `count_mutant_dna` is the number of DNA analyzed with result of _mutant_
* `count_human_dna` is the number of DNA analyzed with result of _human_
* `ratio` is the ration of _mutant_ and _humans_ analyzed
