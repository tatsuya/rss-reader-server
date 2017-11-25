package com.tatsuyaoiw;

import com.tatsuyaoiw.json.JsonFeed;
import com.tatsuyaoiw.repository.FeedRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@AllArgsConstructor(onConstructor = @__(@Inject))
@Path("feeds")
@Produces(APPLICATION_JSON)
@Slf4j
public class FeedResource {

    private FeedRepository feedRepository;

    @GET
    public Response get() {
        List<JsonFeed> feeds = feedRepository.list();
        return Response.ok(feeds).build();
    }
}
