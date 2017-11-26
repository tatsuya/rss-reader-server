package com.tatsuyaoiw;

import com.google.common.collect.ImmutableList;
import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.tatsuyaoiw.model.Entry;
import com.tatsuyaoiw.model.mapper.EntryCustomizer;
import com.tatsuyaoiw.repository.FeedRepository;
import com.tatsuyaoiw.repository.RestFeedRepository;
import com.tatsuyaoiw.service.DefaultFeedService;
import com.tatsuyaoiw.service.FeedService;

import java.util.List;
import java.util.function.Function;

import static com.google.inject.name.Names.named;

public class JerseyModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(new TypeLiteral<List<String>>() {}).annotatedWith(named("feedUrls"))
                                                .toInstance(ImmutableList.of("http://www.wsj.com/xml/rss/3_7085.xml"));
        bind(FeedRepository.class).to(RestFeedRepository.class);

        bind(Integer.class).annotatedWith(named("maxTitleLength")).toInstance(20);
        bind(Integer.class).annotatedWith(named("maxDescriptionLength")).toInstance(140);
        bind(new TypeLiteral<Function<Entry, Entry>>() {}).to(EntryCustomizer.class);

        bind(FeedService.class).to(DefaultFeedService.class);
    }
}
