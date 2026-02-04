# Memelândia

Projeto em microsserviços, utilizando Java, Spring Boot e MongoDB. Configuração centralizada em um servidor de configuração,
serviço de descoberta com Eureka e comunicação externa com Gateway. Possui 3 serviços de domínio: usuários, memes e categorias
de memes. O banco de dados utilizado é o MongoDB.
Utiliza Zipkin para obsevabilidade, e Swagger-UI para documentação.

## Serviços

/config-server
/discovery-server
/gateway
/user-service
/category-service
/meme-service

## Execute

1. Inicie as databases
   - docker compose up -d

2. Para cada serviço, execute
   - mvn spring-boot:run

## Endpoints disponíveis:

- Gateway: http://localhost:8081
- Eureka: http://localhost:9091
- Config Server: http://localhost:8888
- Zipkin: http://localhost:9411
- Swagger UI: http://localhost:8081/swagger-ui/index.html
