# Getting Started

# create a docker image

1. Run ./mvnw clean package -DskipTests
2. docker build -t <dockerhubusername>/application .
3. docker push <dockerhubusername>/application

Run docker compose up

Access the application's swagger locally on http:localhost:8000/swagger-ui/index.html




