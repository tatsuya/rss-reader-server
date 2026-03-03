package com.tatsuyaoiw.rssreader.model;

import lombok.Builder;
import lombok.Value;
import lombok.With;

@Value
@Builder
public class Subscription {

    Integer id;
    String url;
    @With
    Feed feed;
}
