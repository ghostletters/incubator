# getting-started project

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```
./mvnw quarkus:dev
```

# add admin account
```
  CSRF_TOKEN=$(curl http://localhost:7750/pulsar-manager/csrf-token)
  curl \
-H 'X-XSRF-TOKEN: $CSRF_TOKEN' \
-H 'Cookie: XSRF-TOKEN=$CSRF_TOKEN;' \
-H "Content-Type: application/json" \
  -X PUT http://localhost:7750/pulsar-manager/users/superuser \
-d '{"name": "admin", "password": "changeme", "description": "test", "email": "username@test.org"}'
```

# pulsar Service URL
  http://pulsar:8080
