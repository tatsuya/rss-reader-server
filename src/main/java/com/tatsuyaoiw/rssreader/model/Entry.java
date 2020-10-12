package com.tatsuyaoiw.rssreader.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Entry {

    String title;
    String link;
    String description;
}

