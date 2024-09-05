This projects was developed with spring-webflux, using persistence in MySql.
Steps to run from the code editor
    1. Create schema "management" in MySQL, in te host "localhost:3306"


Also, docker was used to perform containerized deployment in a local environment.
Steps for deploy with Docker:
    1. Excute:  mvn clean install -DskipTests //Generate jar
    2. Execute: docker-compose build //Build image
    3. Execute: docker-compose up db // Run container db MySQL
    4. Execute:  docker-compose up java_app // Run container java project

Note: The machine on which it is deployed must be running docker 