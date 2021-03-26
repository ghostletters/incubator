package org.acme.getting.started;

import org.acme.getting.started.database.Gift;
import org.acme.getting.started.database.SantaClausService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/hello")
public class GreetingResource {

    @Inject
    SantaClausService santaClausService;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        Gift gift = santaClausService.createGift("xx");
        String giftInfo = "here is your gift...: " + gift.getId();

        return giftInfo;
    }
}