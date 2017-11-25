package com.tatsuyaoiw.repository;

import com.google.common.collect.ImmutableList;
import com.google.inject.Singleton;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import com.tatsuyaoiw.json.JsonFeed;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static java.lang.String.format;

@Singleton
public class RestFeedRepository implements FeedRepository {

    private static final String DEFULT_FEED_URL = "http://www.wsj.com/xml/rss/3_7085.xml";

    private static SyndFeed getFeed(String url) {
        try (CloseableHttpClient client = HttpClients.createMinimal()) {
            HttpUriRequest request = new HttpGet(url);
            try (CloseableHttpResponse response = client.execute(request);
                 InputStream stream = response.getEntity().getContent()) {
                SyndFeedInput input = new SyndFeedInput();
                return input.build(new XmlReader(stream));
            } catch (FeedException e) {
                throw new IllegalArgumentException(format("Failed to parse feed content from url %s", url), e);
            }
        } catch (IOException e) {
            throw new IllegalArgumentException(format("Failed to get feed content from url %s", url), e);
        }
    }

    public List<JsonFeed> list() {
        SyndFeed feed = getFeed(DEFULT_FEED_URL);
        return ImmutableList.of(JsonFeed.builder()
                                        .title(feed.getTitle())
                                        .description(feed.getDescription())
                                        .build());
    }
}
