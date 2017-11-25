package com.tatsuyaoiw.repository;

import com.tatsuyaoiw.json.JsonFeed;

import java.util.List;

public interface FeedRepository {

    List<JsonFeed> list();
}
