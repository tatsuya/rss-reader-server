package com.tatsuyaoiw.rssreader.repository;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;

@Singleton
@Slf4j
public class DataSourceProvider implements Provider<DataSource> {

    private final String url;

    @Inject
    public DataSourceProvider(@Named("dataSourceUrl") String url) {
        this.url = url;
    }

    @Override
    public DataSource get() {
        log.info("Initializing datasource {}", url);
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername("rss-reader");
        config.setPassword("rss-reader");
        return new HikariDataSource(config);
    }
}
