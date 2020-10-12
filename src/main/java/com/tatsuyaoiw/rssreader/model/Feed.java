package com.tatsuyaoiw.rssreader.model;

import lombok.Builder;
import lombok.Value;
import lombok.With;

import java.util.List;

import static java.util.Collections.emptyList;

@Value
@Builder
public class Feed {

    public static final Feed EMPTY = Feed.builder().build();

    String title;
    String description;
    @With
    List<Entry> entries;

    public List<Entry> getEntries() {
        return entries == null ? emptyList() : entries;
    }
}
