package org.acme.getting.started;

import org.apache.pulsar.client.api.*;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class PulsarClientTest {

    @Test
    void testClient() throws Exception {
        PulsarClient pulsarClient = buildPulsarClient();
        Producer<byte[]> producer = pulsarClient.newProducer()
                .topic("test")
                .create();
        producer.send("hello world".getBytes());
        producer.close();

        Thread.sleep(500);


        Consumer<byte[]> consumer = buildGiftConsumer();
        Message<byte[]> message = consumer.receive();
        byte[] data = message.getData();
        System.out.println("result: " + new String(data));
        consumer.acknowledge(message);

        consumer.close();
//        pulsarClient.close();
    }

    private static Consumer<byte[]> buildGiftConsumer() throws PulsarClientException {
        PulsarClient client = buildPulsarClient();

        return client.newConsumer()
                .topic("test")
                .subscriptionName("foo")
                .subscribe();
    }

    private static PulsarClient buildPulsarClient() throws PulsarClientException {
        return PulsarClient.builder()
                .serviceUrl("pulsar://localhost:6650")
                .build();
    }
}
