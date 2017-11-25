package com.tatsuyaoiw.model;

import lombok.Builder;
import lombok.Value;
import lombok.experimental.Wither;

import java.util.List;

@Value
@Builder
public class Feed {

    private final String title;
    private final String description;
    @Wither
    private final List<Entry> entries;
}
