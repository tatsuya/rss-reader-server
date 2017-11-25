package com.tatsuyaoiw;

import com.tatsuyaoiw.json.JsonEntry;
import com.tatsuyaoiw.json.JsonFeed;
import com.tatsuyaoiw.model.Entry;
import com.tatsuyaoiw.model.Feed;
import com.tatsuyaoiw.repository.FeedRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import static java.util.stream.Collectors.toList;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@AllArgsConstructor(onConstructor = @__(@Inject))
@Path("feeds")
@Produces(APPLICATION_JSON)
@Slf4j
public class FeedResource {

    private FeedRepository feedRepository;

    private static JsonFeed toJson(Feed input) {
        return JsonFeed.builder()
                       .title(input.getTitle())
                       .description(input.getDescription())
                       .entries(input.getEntries().stream()
                                     .map(FeedResource::toJson)
                                     .collect(toList()))
                       .build();
    }

    private static JsonEntry toJson(Entry input) {
        return JsonEntry.builder()
                        .title(input.getTitle())
                        .description(input.getDescription())
                        .build();
    }

    @GET
    public Response get() {
        return Response.ok(feedRepository.list().stream()
                                         .map(FeedResource::toJson)
                                         .collect(toList()))
                       .build();
    }
}
