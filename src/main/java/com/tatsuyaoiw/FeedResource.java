package com.tatsuyaoiw;

import com.google.common.collect.ImmutableList;
import com.tatsuyaoiw.json.JsonFeed;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("feeds")
@Produces(APPLICATION_JSON)
public class FeedResource {

    @GET
    public Response get() {
        List<JsonFeed> feeds = ImmutableList.of(JsonFeed.builder()
                                                        .title("WSJ.com: World News")
                                                        .description("World News")
                                                        .build());
        return Response.ok(feeds).build();
    }
}
