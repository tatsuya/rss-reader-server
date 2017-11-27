package com.tatsuyaoiw;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.TypeLiteral;
import com.tatsuyaoiw.model.Entry;
import com.tatsuyaoiw.model.mapper.EntryCustomizer;
import com.tatsuyaoiw.repository.DataSourceProvider;
import com.tatsuyaoiw.repository.FeedRepository;
import com.tatsuyaoiw.repository.HttpFeedRepository;
import com.tatsuyaoiw.repository.SqlSubscriptionRepository;
import com.tatsuyaoiw.repository.SubscriptionRepository;
import com.tatsuyaoiw.service.DefaultFeedService;
import com.tatsuyaoiw.service.DefaultSubscriptionService;
import com.tatsuyaoiw.service.FeedService;
import com.tatsuyaoiw.service.SubscriptionService;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.jersey.server.ResourceConfig;
import org.jvnet.hk2.guice.bridge.api.GuiceBridge;
import org.jvnet.hk2.guice.bridge.api.GuiceIntoHK2Bridge;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.util.function.Function;

import static com.google.inject.name.Names.named;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class JerseyApplication extends ResourceConfig {

    @Inject
    public JerseyApplication(ServiceLocator serviceLocator) {
        packages(JerseyApplication.class.getPackage().getName());

        // Guice HK2 bridge
        // See e.g. https://github.com/t-tang/jetty-jersey-HK2-Guice-boilerplate
        GuiceBridge.getGuiceBridge().initializeGuiceBridge(serviceLocator);
        GuiceIntoHK2Bridge guiceBridge = serviceLocator.getService(GuiceIntoHK2Bridge.class);
        guiceBridge.bridgeGuiceInjector(Guice.createInjector(new JerseyModule()));
    }

    private class JerseyModule extends AbstractModule {

        @Override
        protected void configure() {
            install(new DataAccessModule());

            bind(Integer.class).annotatedWith(named("maxTitleLength")).toInstance(20);
            bind(Integer.class).annotatedWith(named("maxDescriptionLength")).toInstance(140);
            bind(new TypeLiteral<Function<Entry, Entry>>() {
            }).to(EntryCustomizer.class);

            bind(FeedService.class).to(DefaultFeedService.class);
            bind(SubscriptionService.class).to(DefaultSubscriptionService.class);
        }
    }

    private class DataAccessModule extends AbstractModule {

        private static final String JDBC_DATABASE_URL_DEFAULT = "jdbc:postgresql://localhost/postgres";

        @Override
        protected void configure() {
            String jdbcDatabaseUrl = System.getenv("JDBC_DATABASE_URL");
            String dataSourceUrl = isNotBlank(jdbcDatabaseUrl) ? jdbcDatabaseUrl : JDBC_DATABASE_URL_DEFAULT;
            bind(String.class).annotatedWith(named("dataSourceUrl")).toInstance(dataSourceUrl);
            bind(DataSource.class).toProvider(DataSourceProvider.class).asEagerSingleton();

            bind(SubscriptionRepository.class).to(SqlSubscriptionRepository.class);
            bind(FeedRepository.class).to(HttpFeedRepository.class);
        }
    }
}
