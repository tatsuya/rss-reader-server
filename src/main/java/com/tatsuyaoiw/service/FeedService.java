package com.tatsuyaoiw.service;

import com.tatsuyaoiw.model.Feed;

import java.util.Optional;

public interface FeedService {

    Optional<Feed> get(String url);
}
