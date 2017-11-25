package com.tatsuyaoiw.model;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class Feed {

    private final String title;
    private final String description;
    private final List<Entry> entries;
}
