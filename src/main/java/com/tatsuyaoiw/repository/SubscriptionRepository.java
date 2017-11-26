package com.tatsuyaoiw.repository;

import com.tatsuyaoiw.model.Subscription;

import java.util.List;

public interface SubscriptionRepository {

    List<Subscription> list();

    Subscription add(String url);
}
