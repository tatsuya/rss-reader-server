package com.tatsuyaoiw.rssreader.service;

import com.tatsuyaoiw.rssreader.model.Feed;

import java.util.Optional;

public interface FeedService {

    Optional<Feed> get(String url);
}
