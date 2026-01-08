# memelandia

Projeto final do curso de Especialista Back-end Java da EBAC

# config-server

| Endpoint                                    | Função             |
| ------------------------------------------- | ------------------ |
| `http://localhost:8888/application/default` | Config             |
| `http://localhost:8888/actuator/health`     | Health check       |
| `POST /actuator/refresh`                    | Recarregar configs |

# discovery-server

| Endpoint                                | Função        |
| --------------------------------------- | ------------- |
| `http://localhost:9091/actuator/health` | Health check  |
| `http://localhost:9091/`                | Painel Eureka |
