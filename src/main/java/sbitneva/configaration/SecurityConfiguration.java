package sbitneva.configaration;

import sbitneva.command.factory.FactoryCommand;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SecurityConfiguration {
    private static final SecurityConfiguration config = new SecurityConfiguration();

    private Map<String, String> grant = new HashMap<>();

    private SecurityConfiguration() {
        grant.put(FactoryCommand.LOGIN, "ALL");
        grant.put(FactoryCommand.REGISTRATION, "ALL");
        grant.put(FactoryCommand.LOGOUT, "AUTH");
        //grant.put(FactoryCommand.USERS, "AUTH");
        grant.put("/", "ALL");
    }

    public static SecurityConfiguration getInstance() {
        return config;
    }

    public String security(String command) {
        return grant.get(command);
    }

    public Set<String> getEndPoints() {
        return grant.keySet();
    }
}
