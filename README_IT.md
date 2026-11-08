# DDD + Hexagonal Architecture — progetto completo (Java 21)

Esempio eseguibile **a singolo servizio**, indipendente dal progetto Saga. Il dominio non dipende da Spring o JPA.

## Architettura

- `domain`: Aggregate Root `Order`, Value Object `Money`, invarianti e stato.
- `application.port.in`: input port e command del caso d'uso.
- `application.port.out`: output port del repository.
- `application.OrderService`: orchestrazione e confine transazionale.
- `adapter.in.rest`: controller REST, DTO, validazione e Problem Details.
- `adapter.out.jpa`: adapter JPA + H2.

Consulta i **sei diagrammi PNG** nella cartella `diagrams/` e il documento Word nella cartella `documentazione/`.

## Prerequisiti

Java 21, Maven 3.9+; Docker è opzionale.

## Compilazione e test

```bash
mvn clean test
mvn spring-boot:run
```

In alternativa:

```bash
docker compose up --build
```

## Esempi REST

```bash
curl -i -X POST http://localhost:8080/api/v1/orders \
 -H 'Content-Type: application/json' \
 -d '{"product":"Book","quantity":2,"unitPrice":10.50,"currency":"EUR"}'
```

Copia il valore `id` restituito e sostituiscilo a `UUID`:

```bash
curl http://localhost:8080/api/v1/orders/UUID
curl -X POST http://localhost:8080/api/v1/orders/UUID/confirm
curl -X POST http://localhost:8080/api/v1/orders/UUID/cancel
curl http://localhost:8080/actuator/health
```

La cancellazione di un ordine già confermato restituisce **409 Conflict**: è un'invariante del dominio, non un controllo nel controller.

## Come presentarlo al colloquio

1. Descrivi `Order` come Aggregate Root e `Money` come Value Object.
2. Spiega perché `OrderRepository` è una porta e `JpaOrderAdapter` è un adapter.
3. Mostra che il dominio non importa Spring né JPA.
4. Descrivi il flusso REST → input port → application service → domain → output port → JPA.
5. Esegui i test e mostra il caso di violazione dell'invariante (HTTP 409).
6. Discuti l'evoluzione: PostgreSQL, Flyway, optimistic locking, autenticazione, outbox, observability.

## Limiti dichiarati

- H2 in-memory: i dati non persistono dopo il riavvio.
- `ddl-auto: create-drop` è solo per demo, non per produzione.
- Non include sicurezza, Swagger UI, Kafka o Saga: sono progetti separati.
- In concorrenza, le transizioni richiederebbero `@Version` o locking e una gestione dei conflitti.
- Nessuna idempotency key per la creazione ordini.

Il progetto è fornito con test automatici ma la compilazione Maven non è stata eseguita nell'ambiente di preparazione, dove Maven non è installato.
