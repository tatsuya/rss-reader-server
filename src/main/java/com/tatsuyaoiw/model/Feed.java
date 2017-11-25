package com.tatsuyaoiw.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Feed {

    private final String title;
    private final String description;
}
