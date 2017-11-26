package com.tatsuyaoiw;

import com.google.common.collect.ImmutableList;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.TypeLiteral;
import com.tatsuyaoiw.model.Entry;
import com.tatsuyaoiw.model.mapper.EntryCustomizer;
import com.tatsuyaoiw.repository.FeedRepository;
import com.tatsuyaoiw.repository.RestFeedRepository;
import com.tatsuyaoiw.service.DefaultFeedService;
import com.tatsuyaoiw.service.FeedService;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.jersey.server.ResourceConfig;
import org.jvnet.hk2.guice.bridge.api.GuiceBridge;
import org.jvnet.hk2.guice.bridge.api.GuiceIntoHK2Bridge;

import javax.inject.Inject;
import java.util.List;
import java.util.function.Function;

import static com.google.inject.name.Names.named;

// Guice HK2 bridge
// See e.g. https://github.com/t-tang/jetty-jersey-HK2-Guice-boilerplate
public class JerseyApplication extends ResourceConfig {

    @Inject
    public JerseyApplication(ServiceLocator serviceLocator) {
        packages(JerseyApplication.class.getPackage().getName());

        GuiceBridge.getGuiceBridge().initializeGuiceBridge(serviceLocator);
        GuiceIntoHK2Bridge guiceBridge = serviceLocator.getService(GuiceIntoHK2Bridge.class);
        guiceBridge.bridgeGuiceInjector(Guice.createInjector(new JerseyModule()));
    }

    private class JerseyModule extends AbstractModule {

        @Override
        protected void configure() {
            bind(new TypeLiteral<List<String>>() {
            }).annotatedWith(named("feedUrls"))
              .toInstance(ImmutableList.of("http://www.wsj.com/xml/rss/3_7085.xml"));
            bind(FeedRepository.class).to(RestFeedRepository.class);

            bind(Integer.class).annotatedWith(named("maxTitleLength")).toInstance(20);
            bind(Integer.class).annotatedWith(named("maxDescriptionLength")).toInstance(140);
            bind(new TypeLiteral<Function<Entry, Entry>>() {
            }).to(EntryCustomizer.class);

            bind(FeedService.class).to(DefaultFeedService.class);
        }
    }
}
