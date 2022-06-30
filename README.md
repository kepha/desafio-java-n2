
# Kepha Store

O Kepha Store é uma API RESTful que fornece um serviço de carrinho
de compras, recebendo ordens de compra postadas pelos clientes . Aqui,
é feito o CRUD de todas as entidades, utilizando o banco de dados 
PostgreSQL.

Com a implementação de um serviço Cron utilizando quartz que fica
executando em background fazendo uma busca de um em um minuto no banco de dados
especificamente na tabela de orders verificando as ordens que
estao em status de WAITING (Aguardando) atualizando para o status
AUTHORIZED (Atualizado).

```ts
    @Transactional
    @Scheduled(cron = "{cron.expr}", identity = "My service cron")
    public void schenduler() {
        List<Order> orderWaiting = orderRepository.findByStatus(OrderStatus.WAITING);

        for (Order order : orderWaiting) {
            System.out.println("Id:" + order.id + " Created: " + order.created_at + " Status: " + order.status);
            order.status = order.status.AUTHORIZED;
            System.out.println(" AUTHORIZED: " + Instant.now());
        }
    }
```
Para saber mais sobre o serviço Cron e Quartz, acesse: https://quarkus.io/guides/quartz


## Tecnologias utilizadas

- [Java](ttps://www.java.com/)
- [Quarkus](https://quarkus.io/)
- [PostgreSQL](https://www.postgresql.org/)
- [Docker](https://www.docker.com/)

## Dependências

- hibernate-orm-panache
- hibernate-validator
- smallrye-openapi
- jdbc-postgresql
- quartz
- resteasy-jsonb
- resteasy
- security-jpa
- smallrye-jwt
- lombok         
- flyway

## Executando o projeto

Com o terminal aberto na pasta do projeto e com o docker executando, digite o seguinte comando:

```bash
    docker-compose up
```
Assim, serão gerados as imagens do PostgreSQL, Kaycloak, e da aplicação, e o container da aplicação.
Para realizar os testes da API, acesse: http://localhost:8080/q/swagger-ui/

