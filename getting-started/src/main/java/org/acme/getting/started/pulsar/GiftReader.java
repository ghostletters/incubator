package org.acme.getting.started.pulsar;

import io.debezium.serde.DebeziumSerdes;
import io.quarkus.vertx.ConsumeEvent;
import org.acme.getting.started.database.Gift;
import org.apache.kafka.common.serialization.Serde;
import org.apache.pulsar.client.api.*;
import org.apache.pulsar.shade.org.apache.commons.lang.StringUtils;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.Map;

@ApplicationScoped
public class GiftReader {

    @Inject
    GiftFinder giftFinder;

    private Consumer<Gift> consumer;
    private PulsarClient client;

    private Serde<Gift> giftSerde;

    private String serviceUrl = "pulsar://localhost:6650";
    protected String topic = "persistent://public/default/foo.public.gift";
    protected String subscriptionName = "quarkus-gift-reader";

    MessageListener messageListener = new MessageListener<>() {
        @Override
        @Transactional
        public void received(Consumer consumer, Message msg) {
            String msgString = new String(msg.getData());
            System.out.println(msgString);


            Gift after = giftSerde.deserializer().deserialize("after", msg.getData());
            System.out.println(after);


            String id = StringUtils.substringBetween(msgString, "\"id\":", ",");
            Gift gift = giftFinder.findGift(Long.valueOf(id));

            if (gift != null) {
                System.out.println(gift);
            }

            try {
                consumer.acknowledge(msg);
                System.out.println("Acknowledged: " + msg.getSequenceId());
            } catch (PulsarClientException e) {
                consumer.negativeAcknowledge(msg);
                e.printStackTrace();
            }
        }
    };

    @Transactional
    @ConsumeEvent(value = "greeting")
    public void readGiftChanges(String eventMessage) throws PulsarClientException {
        System.out.println("Event msg: " + eventMessage);

        giftSerde = DebeziumSerdes.payloadJson(Gift.class);
        giftSerde.configure(Map.of("from.field", "after"), false);

        initConsumerWithListener();
    }

    protected void initConsumerWithListener() throws PulsarClientException {
        if (consumer == null) {
            consumer = getClient().newConsumer()
                    .topic(topic)
                    .subscriptionName(subscriptionName)
                    .subscriptionType(SubscriptionType.Shared)
                    .messageListener(messageListener)
                    .subscribe();
        }
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
