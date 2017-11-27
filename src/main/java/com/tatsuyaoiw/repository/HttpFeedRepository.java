package com.tatsuyaoiw.repository;

import com.google.inject.Singleton;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import com.tatsuyaoiw.model.Entry;
import com.tatsuyaoiw.model.Feed;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Singleton
@Slf4j
public class HttpFeedRepository implements FeedRepository {

    private static Optional<SyndFeed> getFeed(String url) {
        log.info("Getting feed for {}", url);
        try (CloseableHttpClient client = HttpClients.createMinimal()) {
            HttpUriRequest request = new HttpGet(url);
            try (CloseableHttpResponse response = client.execute(request);
                 InputStream stream = response.getEntity().getContent()) {
                SyndFeedInput input = new SyndFeedInput();
                return Optional.of(input.build(new XmlReader(stream)));
            } catch (FeedException e) {
                log.warn("Failed to parse feed content from url {}", url);
            }
        } catch (IOException e) {
            log.warn("Failed to get feed content from url {}", url);
        }
        return Optional.empty();
    }

    private static Entry map(SyndEntry input) {
        return Entry.builder()
                    .title(input.getTitle())
                    .link(input.getLink())
                    .description(input.getDescription().getValue())
                    .build();
    }

    private static Feed map(SyndFeed input) {
        return Feed.builder()
                   .title(input.getTitle())
                   .description(input.getDescription())
                   .entries(input.getEntries().stream()
                                 .map(HttpFeedRepository::map)
                                 .collect(toList()))

                   .build();
    }

    @Override
    public Optional<Feed> get(String url) {
        return getFeed(url).map(HttpFeedRepository::map);
    }
}
