package com.tatsuyaoiw.repository;

import com.tatsuyaoiw.model.Feed;

import java.util.Optional;

public interface FeedRepository {

    Optional<Feed> get(String url);
}
