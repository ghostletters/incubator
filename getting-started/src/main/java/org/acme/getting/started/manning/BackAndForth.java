package org.acme.getting.started.manning;

import org.apache.pulsar.client.api.*;

import java.util.Date;

public class BackAndForth {

    private Consumer consumer;
    private Producer producer;
    private PulsarClient client;

    public static void main(String[] args) throws Exception {
        BackAndForth sl = new BackAndForth();
        sl.startConsumer();
//        sl.startProducer();
    }

    private String serviceUrl = "pulsar://localhost:6650";
    protected String topic = "persistent://public/default/foo.public.gift";
    protected String subscriptionName = "quarkus-gift-reader";

    protected void startProducer() {
        Runnable run = () -> {
            int counter = 0;
            while (true) {
                try {
                    getProducer().newMessage()
                            .value(String.format("{id: %d, time: %tc}", ++counter, new Date()).getBytes())
                            .send();
                    Thread.sleep(100);
                }
                catch (final Exception ex) {
                }
            }
        };
        new Thread(run).start();
    }

    protected void startConsumer() {
        Runnable run = () -> {
            while (true) {
                Message<byte[]> msg = null;
                try {
                    msg = getConsumer().receive();
                    System.out.printf("Message received: %s \n",
                            new String(msg.getData()));
                    getConsumer().acknowledge(msg);
                } catch (Exception e) {
                    System.err.printf("Unable to consume message: %s \n", e.getMessage());
                    consumer.negativeAcknowledge(msg);
                }
            }
        };
        new Thread(run).start();
    }

    protected Consumer<byte[]> getConsumer() throws PulsarClientException {
        if (consumer == null) {
            consumer = getClient().newConsumer()
                    .topic(topic)
                    .subscriptionName(subscriptionName)
                    .subscriptionType(SubscriptionType.Shared)
                    .subscribe();
        }
        return consumer;
    }

    protected Producer<byte[]> getProducer() throws PulsarClientException {
        if (producer == null) {
            producer = getClient().newProducer()
                    .topic(topic).create();
        }
        return producer;
    }

    protected PulsarClient getClient() throws PulsarClientException {
        if (client == null) {
            client = PulsarClient.builder()
                    .serviceUrl(serviceUrl)
                    .build();
        }
        return client;
    }
}
