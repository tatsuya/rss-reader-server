package com.tatsuyaoiw.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Entry {

    private final String title;
    private final String link;
    private final String description;
}

