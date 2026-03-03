package com.tatsuyaoiw.rssreader.repository;

import com.tatsuyaoiw.rssreader.model.Subscription;

import java.util.List;
import java.util.Optional;

public interface SubscriptionRepository {

    List<Subscription> list();

    Optional<Subscription> get(Integer id);

    Subscription add(String url);

    void delete(Integer id);
}
