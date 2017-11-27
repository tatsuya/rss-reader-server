package com.tatsuyaoiw.rssreader.model;

import lombok.Builder;
import lombok.Value;
import lombok.experimental.Wither;

@Value
@Builder
public class Subscription {

    private final Integer id;
    private final String url;
    @Wither
    private final Feed feed;
}
