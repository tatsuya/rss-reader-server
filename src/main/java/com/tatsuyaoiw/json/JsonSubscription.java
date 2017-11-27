package com.tatsuyaoiw.json;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class JsonSubscription {

    private final Integer id;
    private final String url;
    private final JsonFeed feed;
}
