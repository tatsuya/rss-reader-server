package com.tatsuyaoiw.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Subscription {

    private final Integer id;
    private final String url;
}