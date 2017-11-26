package com.tatsuyaoiw.service;

import com.tatsuyaoiw.model.Entry;
import com.tatsuyaoiw.model.Feed;
import com.tatsuyaoiw.repository.FeedRepository;
import com.tatsuyaoiw.repository.SubscriptionRepository;
import lombok.AllArgsConstructor;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

@AllArgsConstructor(onConstructor = @__(@Inject))
@Singleton
public class DefaultFeedService implements FeedService {

    private FeedRepository feedRepository;
    private SubscriptionRepository subscriptionRepository;
    private Function<Entry, Entry> entryCustomizer;

    @Override
    public List<Feed> list() {
        return subscriptionRepository.list().stream()
                                     .map(x -> feedRepository.get(x.getUrl()))
                                     .map(this::customize)
                                     .collect(toList());
    }

    private Feed customize(Feed input) {
        return input.withEntries(input.getEntries().stream()
                                      .map(entryCustomizer)
                                      .collect(toList()));
    }
}
