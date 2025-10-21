# Getting Started

### To make it run



create an .env file in the docker-compose folder and add:
POSTGRES_USERNAME=<your usernmae>
POSTGRES_PASSWORD=<your password>
because it is in the same folder as the docker-compose.yml docker-compose will pick it up automaticly
 run mvn clean install

to run it live;

use the command docker-compose up -d (in the docker-compose folder)

and create a request like in the in the [CustomerOnboardingApplicationTests.java](src/test/java/com/abc/customer/onboarding/CustomerOnboardingApplicationTests.java)
and run it against the controller.

or just  run the integration test.

