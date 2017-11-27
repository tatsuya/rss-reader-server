package com.tatsuyaoiw.resource;

import com.tatsuyaoiw.json.JsonSubscription;
import com.tatsuyaoiw.json.request.JsonSubscribeRequest;
import com.tatsuyaoiw.model.Subscription;
import com.tatsuyaoiw.repository.SubscriptionRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

import static java.util.stream.Collectors.toList;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@AllArgsConstructor(onConstructor = @__(@Inject))
@Path("subscriptions")
@Produces(APPLICATION_JSON)
@Singleton
@Slf4j
public class SubscriptionResource {

    private SubscriptionRepository repository;

    private static JsonSubscription toJson(Subscription input) {
        return JsonSubscription.builder()
                               .id(input.getId())
                               .url(input.getUrl())
                               .build();
    }

    @GET
    public Response list() {
        return Response.ok(repository.list().stream()
                                     .map(SubscriptionResource::toJson)
                                     .collect(toList()))
                       .build();
    }

    @POST
    public Response subscribe(@Context UriInfo uriInfo, JsonSubscribeRequest request) {
        Subscription subscription = repository.add(request.getUrl());
        URI resourceUri = uriInfo.getBaseUriBuilder()
                                 .path("subscriptions")
                                 .path(subscription.getId().toString())
                                 .build();
        return Response.created(resourceUri).build();
    }

    @DELETE
    @Path("{id}")
    public Response unsubscribe(@PathParam("id") Integer id) {
        repository.delete(id);
        return Response.noContent().build();
    }
}
