# getting-started project

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```
./mvnw quarkus:dev
```

# Add Pulsar Admin Mannger admin account - details: https://pulsar.apache.org/docs/en/administration-pulsar-manager/
```
CSRF_TOKEN=$(curl http://localhost:7750/pulsar-manager/csrf-token)

curl \
-X PUT http://localhost:7750/pulsar-manager/users/superuser \
-H 'X-XSRF-TOKEN: $CSRF_TOKEN' \
-H 'Cookie: XSRF-TOKEN=$CSRF_TOKEN;' \
-H "Content-Type: application/json" \
-d '{"name": "admin", "password": "changeme", "description": "test", "email": "username@test.org"}'
```

# Pulsar Admin Mannger service URL - 'pulsar' is the name of the docker service here
http://pulsar:8080
