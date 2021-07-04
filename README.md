# ms-recipe
Created by Sean Hendry (2021) - ABN Amro Activity

#OVERVIEW

Spring Boot app with MongoDB, Unit tests for Functional endpoints; sample for non-functional testing (Mockito)

#SECURITY/MONITORING
HTTPS - The HTTPS protocol is enforced in this solution (provided by a self-signed certificate included) therefore
when you hit any endpoint please ensure you prefix it with 'https'

Actuator endpoints can be accessed through the '/managed' endpoint for e.g. https://localhost:8080/manage/heath
    - current Actuator endpoints exposed: health,info,metrics,prometheus,threaddump
    
Prometheus - Prometheus scraping available and visible via /manage/prometheus (may take a second or 2 to start up)




# TESTS
The assignment requested the 'when/then' approach for the Unit Tests (Mockito) however I am currently using the
Reactive WebFlux functional endpoint approach and thus the tests are making use of Flux/Mono & StepVerifier. Please
let me know if you would like to see the more classic approach as indicated of the 'when/then' Mockito process
and I will be more than happy to include this as well.

# DOCKER
The docker-compose.yml contains the entrypoint already so this can simply be run as follows:
> docker-compose build .
> docker-compose up

MongoDB is used for this solution; although 'mongo:3.2.4' is utilized here, mongo:latest can also be used
  
PLEASE NOTE: due to the Vodacom policies enforced on my machine, certificate errors were received during testing of 
docker-compose. Unfortunately, even with the certs added to the solution for our environment, I still faced an issue
whereby a 'man-in-the-middle' piece of logic is enforced when on the Vodacom machine builds that signs certs and
in my case is causing the Flapdoodle cert to be considered untrusted.