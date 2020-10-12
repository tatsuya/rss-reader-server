package com.tatsuyaoiw.rssreader.service;

import com.google.inject.Singleton;
import com.tatsuyaoiw.rssreader.model.Entry;
import com.tatsuyaoiw.rssreader.model.Feed;
import com.tatsuyaoiw.rssreader.model.Subscription;
import com.tatsuyaoiw.rssreader.repository.FeedRepository;
import com.tatsuyaoiw.rssreader.repository.SubscriptionRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

@AllArgsConstructor(onConstructor = @__(@Inject))
@Singleton
@Slf4j
public class DefaultSubscriptionService implements SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final FeedRepository feedRepository;
    private final Function<Entry, Entry> entryCustomizer;

    @Override
    public List<Subscription> list() {
        return subscriptionRepository.list().stream()
                                     .map(this::withFeed)
                                     .collect(toList());
    }

    @Override
    public Optional<Subscription> get(Integer id) {
        return subscriptionRepository.get(id)
                                     .map(this::withFeed);
    }

    @Override
    public Optional<Subscription> add(String url) {
        return feedRepository.get(url)
                             .map(it -> subscriptionRepository.add(url));
    }

    @Override
    public void delete(Integer id) {
        subscriptionRepository.delete(id);
    }

    private Subscription withFeed(Subscription subscription) {
        Feed feed = feedRepository.get(subscription.getUrl())
                                  .map(this::customize)
                                  .orElse(Feed.EMPTY);
        return subscription.withFeed(feed);
    }

    private Feed customize(Feed input) {
        return input.withEntries(input.getEntries().stream()
                                      .map(entryCustomizer)
                                      .collect(toList()));
    }
}
