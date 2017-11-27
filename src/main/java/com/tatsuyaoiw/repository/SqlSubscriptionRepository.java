package com.tatsuyaoiw.repository;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.tatsuyaoiw.model.Subscription;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.lang.String.format;
import static java.sql.Statement.RETURN_GENERATED_KEYS;

@Singleton
@Slf4j
public class SqlSubscriptionRepository implements SubscriptionRepository {

    private final DataSource dataSource;

    @Inject
    public SqlSubscriptionRepository(DataSource dataSource) {
        this.dataSource = dataSource;
        createTableIfNotExists();
    }

    private void createTableIfNotExists() {
        try (Connection connection = dataSource.getConnection(); Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS subscriptions (" +
                    "id SERIAL PRIMARY KEY, " +
                    "url VARCHAR(512), " +
                    "created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now()" +
                    ")");
        } catch (Exception e) {
            throw new IllegalStateException("Failed to create datasource", e);
        }
    }

    @Override
    public List<Subscription> list() {
        List<Subscription> subscriptions = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery("SELECT id, url FROM subscriptions");
            while (rs.next()) {
                subscriptions.add(Subscription.builder()
                                              .id(rs.getInt("id"))
                                              .url(rs.getString("url"))
                                              .build());
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to query database", e);
        }
        log.info("Subscriptions: {}", subscriptions);
        return subscriptions;
    }

    @Override
    public Optional<Subscription> get(Integer id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT id, url FROM subscriptions WHERE id = ?")) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return Optional.of(Subscription.builder()
                                               .id(rs.getInt("id"))
                                               .url(rs.getString("url"))
                                               .build());
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to query database", e);
        }
    }

    @Override
    public Subscription add(String url) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("INSERT INTO subscriptions (url) VALUES (?)", RETURN_GENERATED_KEYS)) {
            statement.setString(1, url);
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new IllegalStateException(format("Failed to add subscription %s, no rows affected", url));
            }
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return Subscription.builder()
                                       .id(generatedKeys.getInt(1))
                                       .url(url)
                                       .build();
                } else {
                    throw new IllegalStateException(format("Failed to add subscription %s, no ID obtained", url));
                }
            }
        } catch (Exception e) {
            throw new IllegalStateException(format("Failed to add subscription %s", url), e);
        }
    }

    @Override
    public void delete(Integer id) {
        log.info("Deleting subscription {}", id);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM subscriptions WHERE id = (?)")) {
            statement.setInt(1, id);
            int affectedRows = statement.executeUpdate();
            if (affectedRows != 1) {
                throw new IllegalStateException(format("Failed to delete subscription %s, no rows affected", id));
            }
            log.info("Deleted subscription {}", id);
        } catch (Exception e) {
            throw new IllegalStateException(format("Failed to delete subscription %s", id), e);
        }
    }
}
