package com.tatsuyaoiw.rssreader.service;

import com.google.inject.Singleton;
import com.tatsuyaoiw.rssreader.model.Feed;
import com.tatsuyaoiw.rssreader.model.Subscription;
import com.tatsuyaoiw.rssreader.repository.SubscriptionRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@AllArgsConstructor(onConstructor = @__(@Inject))
@Singleton
@Slf4j
public class DefaultSubscriptionService implements SubscriptionService {

    private SubscriptionRepository subscriptionRepository;
    private FeedService feedService;

    private Subscription withFeed(Subscription subscription) {
        Feed feed = feedService.get(subscription.getUrl()).orElse(Feed.EMPTY);
        return subscription.withFeed(feed);
    }

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
        if (!feedService.get(url).isPresent()) {
            log.warn("Unable to get feed with url {}", url);
            return Optional.empty();
        }
        return Optional.of(subscriptionRepository.add(url));
    }

    @Override
    public void delete(Integer id) {
        subscriptionRepository.delete(id);
    }
}
