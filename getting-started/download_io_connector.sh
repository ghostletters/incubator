#!/bin/sh

mkdir -p docker/io_connectors

PULSAR_VERSION=$(mvn help:evaluate -Dexpression=pulsar.client.version -q -DforceStdout)
echo set pulsar version: ${PULSAR_VERSION} for docker-compose.yml
echo

sed -i "s/PULSAR_VERSION=.*/PULSAR_VERSION=${PULSAR_VERSION}/" ./docker/.env
sed -i "s/PULSAR_VERSION.*/PULSAR_VERSION \`${PULSAR_VERSION}\`/" ./README.md

curl https://downloads.apache.org/pulsar/pulsar-${PULSAR_VERSION}/connectors/pulsar-io-debezium-postgres-${PULSAR_VERSION}.nar \
  --output docker/io_connectors/pulsar-io-debezium-postgres-${PULSAR_VERSION}.nar
