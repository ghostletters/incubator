# Pulsar IO connectors

Downlaod [Debezium PostgreSQL CDC source](https://pulsar.apache.org/en/download/) via
```
sh ./download_io_connector.sh
```
- copies the `.nar` file to `./docker/io_connectors/`. 
- updates `docker-compose.yml` with pulsar client version from `pom.xml` 
  - PULSAR_VERSION `2.7.1`

# Docker Setup

Start services via
```
docker-compose -f ./docker/docker-compose.yml up
```

- [postgres](https://www.postgresql.org/) - relational database
- [elasticsearch](https://www.elastic.co/elasticsearch/) - (fulltext) search and analytics engine
- [kibana](https://www.elastic.co/kibana) - elastic search frontend
- [pulsar](https://pulsar.apache.org/en/) - messaging and streaming platform
- [pulsarmanager](https://pulsar.apache.org/docs/en/administration-pulsar-manager/) - pulsar frontend

# Pulsar Setup

- download [Pulsar Binary](https://pulsar.apache.org/en/download/)
- unzip
- copy debezium IO connector `pulsar-io-debezium-postgres-2.7.1.nar` into `<pulsar folder>/connectors/` folder
- copy debezium IO config `debezium-postgres-source-config.yaml` into `<pulsar folder>/` folder
- change into folder `cd <pulsar folder>`
- run `bin/pulsar-admin source localrun  --source-config-file debezium-postgres-source-config.yaml`

Full example [Pulsar Docu - Debezium source connector](https://pulsar.apache.org/docs/en/io-debezium-source/#example-of-postgresql)

# Quarkus App (Java)

This project uses [Quarkus](https://quarkus.io/), the Supersonic Subatomic Java Framework.

You can run the application in dev mode that enables live coding using:
```
./mvnw quarkus:dev
```

Create `Gift` entities via http://localhost:8081/hello

# Optional: Pulsar Manager

Create admin account via
```
sh create_pulsar_manager_admin.sh
```

- Open UI at http://localhost:9527
  - login: `admin`
  - password: `changeme`
- Press **New Environment**
  - Environment Name: `pulsar` - is the name of the docker service here
  - Service URL: `http://pulsar:8080`
- Check [public/default/namespace](http://localhost:9527/#/management/namespaces/public/default/namespace?tab=topics)

More details: [Pulsar Manager Docu](https://pulsar.apache.org/docs/en/administration-pulsar-manager/)
