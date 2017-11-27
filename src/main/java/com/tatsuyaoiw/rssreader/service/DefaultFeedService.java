package com.tatsuyaoiw.rssreader.service;

import com.tatsuyaoiw.rssreader.model.Entry;
import com.tatsuyaoiw.rssreader.model.Feed;
import com.tatsuyaoiw.rssreader.repository.FeedRepository;
import lombok.AllArgsConstructor;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Optional;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

@AllArgsConstructor(onConstructor = @__(@Inject))
@Singleton
public class DefaultFeedService implements FeedService {

    private FeedRepository feedRepository;
    private Function<Entry, Entry> entryCustomizer;

    @Override
    public Optional<Feed> get(String url) {
        return feedRepository.get(url).map(this::customize);
    }

    private Feed customize(Feed input) {
        return input.withEntries(input.getEntries().stream()
                                      .map(entryCustomizer)
                                      .collect(toList()));
    }
}
