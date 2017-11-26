package com.tatsuyaoiw.repository;

import com.tatsuyaoiw.model.Feed;

public interface FeedRepository {

    Feed get(String url);
}
