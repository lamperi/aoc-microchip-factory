package name.lamperi.aoc;

import javax.inject.Named;
import javax.inject.Singleton;

import org.glassfish.jersey.server.ResourceConfig;

@Named
@Singleton
public class JerseyConfig extends ResourceConfig {
    public JerseyConfig() {
        registerEndpoints();
    }

    private void registerEndpoints() {
        register(Problem2016Day11Handler.class);
    }
}