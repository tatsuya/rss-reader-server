package com.tatsuyaoiw.json;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class JsonFeed {

    private final String title;
    private final String description;
}
