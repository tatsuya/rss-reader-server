package com.tatsuyaoiw.repository;

import com.google.common.collect.ImmutableList;
import com.tatsuyaoiw.json.JsonFeed;

import java.util.List;

public class FeedRepository {

    public List<JsonFeed> list() {
        return ImmutableList.of(JsonFeed.builder()
                                        .title("WSJ.com: World News")
                                        .description("World News")
                                        .build());
    }
}
