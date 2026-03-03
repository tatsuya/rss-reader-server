package com.tatsuyaoiw.rssreader.repository;

import com.tatsuyaoiw.rssreader.model.Feed;

import java.util.Optional;

public interface FeedRepository {

    Optional<Feed> get(String url);
}
