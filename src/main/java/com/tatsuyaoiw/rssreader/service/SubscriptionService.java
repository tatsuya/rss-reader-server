package com.tatsuyaoiw.rssreader.service;

import com.tatsuyaoiw.rssreader.model.Subscription;

import java.util.List;
import java.util.Optional;

public interface SubscriptionService {

    List<Subscription> list();

    Optional<Subscription> get(Integer id);

    Optional<Subscription> add(String url);

    void delete(Integer id);
}
