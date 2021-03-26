#!/bin/sh

CSRF_TOKEN=$(curl http://localhost:7750/pulsar-manager/csrf-token)

curl \
-X PUT http://localhost:7750/pulsar-manager/users/superuser \
-H 'X-XSRF-TOKEN: $CSRF_TOKEN' \
-H 'Cookie: XSRF-TOKEN=$CSRF_TOKEN;' \
-H "Content-Type: application/json" \
-d '{"name": "admin", "password": "changeme", "description": "test", "email": "username@test.org"}' \

echo
echo Open UI at http://localhost:9527
echo name: admin
echo password: changeme
echo
echo Environment Name: pulsar
echo Service URL: http://pulsar:8080