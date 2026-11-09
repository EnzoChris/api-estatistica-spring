# API de estatísticas

Requer Java 21 e MongoDB. Para iniciar o banco localmente com Docker:

```sh
docker compose up -d
```

Inicie a API com `./mvnw spring-boot:run` (ou `mvnw.cmd spring-boot:run` no Windows).
Por padrão, ela usa `mongodb://localhost:27017/estatistica`. Defina `MONGODB_URI` para usar outro servidor.

Envie uma requisição para `POST http://localhost:8080/metricas`:

```json
{
  "dadosUm": [1, 2, 3],
  "dadosDois": [2, 4, 6]
}
```

A resposta JSON contém variâncias e desvios padrão amostrais, covariância amostral, correlação de Pearson e classificação final. As séries precisam ter o mesmo tamanho, pelo menos dois números finitos e variação em ambas; entradas inválidas recebem HTTP 400.

Execute os testes com `./mvnw test` (ou `mvnw.cmd test` no Windows).
