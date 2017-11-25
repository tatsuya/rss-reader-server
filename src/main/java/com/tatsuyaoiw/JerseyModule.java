package com.tatsuyaoiw;

import com.google.inject.AbstractModule;
import com.tatsuyaoiw.repository.FeedRepository;

public class JerseyModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(FeedRepository.class);
    }
}
