package com.tatsuyaoiw;

import com.google.common.collect.ImmutableList;
import com.tatsuyaoiw.json.JsonFeed;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.GenericType;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class FeedResourceTest extends JerseyTest {

    @Override
    protected Application configure() {
        return new ResourceConfig(FeedResource.class);
    }

    @Test
    public void test() throws Exception {
        List<JsonFeed> expected = ImmutableList.of(JsonFeed.builder()
                                                           .title("WSJ.com: World News")
                                                           .description("World News")
                                                           .build());
        List<JsonFeed> actual = target().path("feeds").request().get(new GenericType<List<JsonFeed>>() {});
        assertEquals(expected, actual);
    }
}