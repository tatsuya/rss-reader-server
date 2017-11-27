package com.tatsuyaoiw.rssreader.model.mapper;

import com.google.inject.Singleton;
import com.google.inject.name.Named;
import com.tatsuyaoiw.rssreader.model.Entry;

import javax.inject.Inject;
import java.util.function.Function;

import static org.apache.commons.lang3.StringUtils.substring;

@Singleton
public class EntryCustomizer implements Function<Entry, Entry> {

    private final Integer maxTitleLength;
    private final Integer maxDescriptionLength;

    @Inject
    public EntryCustomizer(@Named("maxTitleLength") Integer maxTitleLength,
                           @Named("maxDescriptionLength") Integer maxDescriptionLength) {
        this.maxTitleLength = maxTitleLength;
        this.maxDescriptionLength = maxDescriptionLength;
    }

    @Override
    public Entry apply(Entry input) {
        return Entry.builder()
                    .title(substring(input.getTitle(), 0, maxTitleLength))
                    .link(input.getLink())
                    .description(substring(input.getDescription(), 0, maxDescriptionLength))
                    .build();
    }
}
