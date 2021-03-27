#!/bin/sh

mkdir -p docker/io_connectors

PULSAR_VERSION=$(mvn help:evaluate -Dexpression=pulsar.client.version -q -DforceStdout)
echo download pulsar version: ${PULSAR_VERSION}
echo

curl https://downloads.apache.org/pulsar/pulsar-${PULSAR_VERSION}/apache-pulsar-${PULSAR_VERSION}-bin.tar.gz \
  --output apache-pulsar-${PULSAR_VERSION}-bin.tar.gz

tar xvfz apache-pulsar-${PULSAR_VERSION}-bin.tar.gz

mkdir -p ~/dev
mv -r apache-pulsar-${PULSAR_VERSION} ~/dev/

mkdir ~/dev/apache-pulsar-${PULSAR_VERSION}/connectors/
cp ./docker/io_connectors/pulsar-io-debezium-postgres-${PULSAR_VERSION}.nar ~/dev/apache-pulsar-${PULSAR_VERSION}/connectors/

sed -i "s/pulsar-io-debezium-postgres-.*/pulsar-io-debezium-postgres-${PULSAR_VERSION}.nar/" ./debezium-postgres-source-config.yaml
cp debezium-postgres-source-config.yaml ~/dev/apache-pulsar-${PULSAR_VERSION}/

echo
echo Start the Pulsar Debezium connector in local run mode using command:
echo "~/dev/apache-pulsar-${PULSAR_VERSION}/bin/pulsar-admin source localrun \
 --source-config-file debezium-postgres-source-config.yaml"
