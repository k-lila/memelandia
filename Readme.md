# Memelândia

Projeto em microsserviços com Java, Spring Boot e MongoDB. Configuração centralizada em um servidor de configuração,
serviço de descoberta com Eureka e comunicação externa com Gateway. Possui 3 serviços de domínio: usuários, memes e
categorias de memes. O banco de dados utilizado é o MongoDB. Utiliza Zipkin para obsevabilidade, e Swagger-UI para
documentação.

## Serviços

/config-server
/discovery-server
/gateway
/user-service
/category-service
/meme-service

## Execute

O projeto possui 2 ambientes docker, um com acesso ao cluster do MongoDB Atlas, e outro com banco de dados local.

### Para utilizar o cluster do MongoDB Atlas

- docker compose -f docker-compose.atlas.yml up -d --build

### Para utilizar database local

1. Suba os serviços

- docker compose -f docker-compose.localdb.yml up -d --build

2. Suba os bancos de dados

- docker compose -f docker-compose.database.yml up -d

## Endpoints disponíveis:

- Gateway: http://localhost:8080
- Eureka: http://localhost:9091
- Config Server: http://localhost:8888
- Zipkin: http://localhost:9411
- Swagger UI: http://localhost:8081/swagger-ui/index.html
