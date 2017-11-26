package com.tatsuyaoiw.json;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class JsonEntry {

    private final String title;
    private final String description;
}