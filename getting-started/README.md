# getting-started project

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```
./mvnw quarkus:dev
```

# Pulsar IO connectors

Requieres manual download of connectors:

- [Debezium PostgreSQL CDC source](https://debezium.io/)

Downlaod `.nar` files from [https://pulsar.apache.org/en/download/](https://pulsar.apache.org/en/download/) and place in folder

```
~/Downloads/
```

Check version in `./docker/docker-compose.yml`. Current version `2.6.1`.

# Pulsar Mannger

Add admin account - details: https://pulsar.apache.org/docs/en/administration-pulsar-manager/

```
CSRF_TOKEN=$(curl http://localhost:7750/pulsar-manager/csrf-token)

curl \
-X PUT http://localhost:7750/pulsar-manager/users/superuser \
-H 'X-XSRF-TOKEN: $CSRF_TOKEN' \
-H 'Cookie: XSRF-TOKEN=$CSRF_TOKEN;' \
-H "Content-Type: application/json" \
-d '{"name": "admin", "password": "changeme", "description": "test", "email": "username@test.org"}'
```

Pulsar Manager UI at http://localhost:9527

Create New Environment
- Name: `pulsar`
  - is the name of the docker service here
- Service URL: `http://pulsar:8080`

Check http://localhost:9527/#/management/namespaces/public/default/namespace?tab=topics
