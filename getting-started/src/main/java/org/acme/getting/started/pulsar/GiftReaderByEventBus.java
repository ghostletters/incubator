package org.acme.getting.started.pulsar;

import io.quarkus.vertx.ConsumeEvent;
import org.acme.getting.started.database.Gift;
import org.acme.getting.started.database.GiftChildren;
import org.apache.pulsar.client.api.*;
import org.apache.pulsar.shade.org.apache.commons.lang.StringUtils;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@ApplicationScoped
public class GiftReaderByEventBus {

    @Inject
    EntityManager em;

    @Transactional
//    @ConsumeEvent(value = "greeting", blocking = true)
    public void readGiftChanges(String eventMsg) throws PulsarClientException {
        Consumer<byte[]> consumer = null;
        Message msg = null;

        try {
            consumer = buildGiftConsumer();
            msg = consumer.receive();
            String msgString = new String(msg.getData());
            System.out.println("Message received... " + msgString);

            String id = StringUtils.substringBetween(msgString, "\"id\":", ",");
            System.out.println("ID: " + id);


            Gift gift = em.find(Gift.class, Long.valueOf(id));
//            List<Gift> gifts = em.createNamedQuery("Gift.findAll", Gift.class)
//                    .getResultList();
//
//            System.out.println("#########");
//            gifts.forEach(System.out::println);

            System.out.println("##############");
            System.out.println("Gift: " + gift.getId() + " - children: " +
                    gift.getGiftChildren().stream()
                            .map(GiftChildren::getId)
                            .map(Object::toString)
                            .collect(Collectors.joining(",")));

            consumer.acknowledge(msg);
        } catch (PulsarClientException e) {
            System.out.println("foodswo");
            consumer.negativeAcknowledge(msg);
            e.printStackTrace();
            throw new RuntimeException("hmm", e);
        }
    }

    private static Consumer<byte[]> buildGiftConsumer() throws PulsarClientException {
        PulsarClient client = buildPulsarClient();

        return client.newConsumer()
                .topic("public/default/foo.public.gift")
                .subscriptionName("quarkus-gift-reader")
                .subscriptionType(SubscriptionType.Shared)
                .subscriptionInitialPosition(SubscriptionInitialPosition.Earliest)
                .subscribe();
    }

    private static PulsarClient buildPulsarClient() throws PulsarClientException {
        return PulsarClient.builder()
                .serviceUrl("pulsar://localhost:6650")
                .operationTimeout(3, TimeUnit.SECONDS)
                .build();
    }
}
