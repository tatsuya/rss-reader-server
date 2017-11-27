package com.tatsuyaoiw.rssreader.json;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class JsonFeed {

    private final String title;
    private final String description;
    private final List<JsonEntry> entries;
}
