package com.tatsuyaoiw.rssreader.service;

import com.tatsuyaoiw.rssreader.model.Entry;
import com.tatsuyaoiw.rssreader.model.Feed;
import com.tatsuyaoiw.rssreader.model.Subscription;
import com.tatsuyaoiw.rssreader.model.mapper.EntryCustomizer;
import com.tatsuyaoiw.rssreader.repository.FeedRepository;
import com.tatsuyaoiw.rssreader.repository.SubscriptionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static com.google.common.collect.Lists.newArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class DefaultSubscriptionServiceTest {

    private DefaultSubscriptionService service;
    private SubscriptionRepository subscriptionRepository;
    private FeedRepository feedRepository;

    @BeforeEach
    void beforeEach() {
        subscriptionRepository = mock(SubscriptionRepository.class);
        feedRepository = mock(FeedRepository.class);
        service = new DefaultSubscriptionService(subscriptionRepository, feedRepository, new EntryCustomizer(20, 40));

        mockRepositories();
    }

    private void mockRepositories() {
        String url = "https://feeds.a.dj.com/rss/RSSOpinion.xml";

        Feed feed = Feed.builder().title("RSSOpinion").description("RSSOpinion").build();
        feed = feed.withEntries(newArrayList(Entry.builder()
                                                  .title("Sen. Dumpty of Wonderland Votes Nay on Amy Coney Barrett")
                                                  .link("https://www.wsj.com/articles/sen-dumpty-of-wonderland-votes-nay-on-amy-coney-barrett-11602521814")
                                                  .description("Democrats threaten to pack the Supreme Court, then pretend not to know what the phrase means.")
                                                  .build()));

        Subscription subscription = Subscription.builder().id(1).url(url).build();

        when(feedRepository.get(url)).thenReturn(Optional.of(feed));
        when(subscriptionRepository.add(url)).thenReturn(subscription);
        when(subscriptionRepository.get(1)).thenReturn(Optional.of(subscription));
    }

    @Test
    void add_feedFound() {
        String url = "https://feeds.a.dj.com/rss/RSSOpinion.xml";
        Optional<Subscription> ret = service.add(url);

        verify(feedRepository).get(url);
        verify(subscriptionRepository).add(url);

        assertTrue(ret.isPresent());

        Subscription subscription = ret.get();
        assertEquals(1, subscription.getId());
        assertEquals(url, subscription.getUrl());

        Feed feed = subscription.getFeed();
        assertNotNull(feed);
        assertEquals("RSSOpinion", feed.getTitle());
        assertEquals("RSSOpinion", feed.getDescription());
        assertEquals(1, feed.getEntries().size());

        Entry entry = feed.getEntries().get(0);

        assertEquals("Sen. Dumpty of Wonderland Votes Nay on Amy Coney Barrett", entry.getTitle());
        assertEquals("https://www.wsj.com/articles/sen-dumpty-of-wonderland-votes-nay-on-amy-coney-barrett-11602521814", entry.getLink());
        assertEquals("Democrats threaten to pack the Supreme Court, then pretend not to know what the phrase means.", entry.getDescription());
    }

    @Test
    void add_feedNotFound() {
        String url = "https://feeds.a.dj.com/rss/RSSWorldNews.xml";
        Optional<Subscription> ret = service.add(url);

        verify(feedRepository).get(url);
        verify(subscriptionRepository, never()).add(url);

        assertFalse(ret.isPresent());
    }

    @Test
    void get_feedFound() {
        Optional<Subscription> ret = service.get(1);

        verify(subscriptionRepository).get(1);

        assertTrue(ret.isPresent());

        Subscription subscription = ret.get();
        assertEquals(1, subscription.getId());
        assertEquals("https://feeds.a.dj.com/rss/RSSOpinion.xml", subscription.getUrl());

        Feed feed = subscription.getFeed();
        assertNotNull(feed);
        assertEquals("RSSOpinion", feed.getTitle());
        assertEquals("RSSOpinion", feed.getDescription());
        assertEquals(1, feed.getEntries().size());

        Entry entry = feed.getEntries().get(0);

        assertEquals("Sen. Dumpty of Wonde", entry.getTitle());
        assertEquals("https://www.wsj.com/articles/sen-dumpty-of-wonderland-votes-nay-on-amy-coney-barrett-11602521814", entry.getLink());
        assertEquals("Democrats threaten to pack the Supreme C", entry.getDescription());
    }

    @Test
    void get_feedNotFound() {
        Optional<Subscription> ret = service.get(2);

        verify(subscriptionRepository).get(2);

        assertFalse(ret.isPresent());
    }
}