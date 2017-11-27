package com.tatsuyaoiw.model;

import lombok.Builder;
import lombok.Value;
import lombok.experimental.Wither;

import java.util.List;

import static java.util.Collections.emptyList;

@Value
@Builder
public class Feed {

    public static final Feed EMPTY = Feed.builder().build();

    private final String title;
    private final String description;
    @Wither
    private final List<Entry> entries;

    public List<Entry> getEntries() {
        return entries == null ? emptyList() : entries;
    }
}
