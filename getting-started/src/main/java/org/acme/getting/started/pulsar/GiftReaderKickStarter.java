package org.acme.getting.started.pulsar;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import io.vertx.mutiny.core.eventbus.EventBus;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

@ApplicationScoped
public class GiftReaderKickStarter {

    private static final Logger LOG = Logger.getLogger(GiftReaderKickStarter.class);

    @Inject
    EventBus bus;

    void onStart(@Observes StartupEvent event) {
        System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxx");
        LOG.info("startup event");

        bus.sendAndForget("greeting", "hi");
    }

    void onStop(@Observes ShutdownEvent event) {
        LOG.info("termination event");
    }
}
