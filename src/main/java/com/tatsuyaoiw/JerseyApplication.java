package com.tatsuyaoiw;

import com.google.inject.Guice;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.jersey.server.ResourceConfig;
import org.jvnet.hk2.guice.bridge.api.GuiceBridge;
import org.jvnet.hk2.guice.bridge.api.GuiceIntoHK2Bridge;

import javax.inject.Inject;

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

}
