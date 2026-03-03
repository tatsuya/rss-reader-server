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
    private final String username;
    private final String password;

    @Inject
    public DataSourceProvider(@Named("dataSourceUrl") String url,
                              @Named("dataSourceUsername") String username,
                              @Named("dataSourcePassword") String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public DataSource get() {
        log.info("Initializing datasource {}", url);
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);
        return new HikariDataSource(config);
    }
}
