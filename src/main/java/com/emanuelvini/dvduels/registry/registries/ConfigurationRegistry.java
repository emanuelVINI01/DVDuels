package com.emanuelvini.dvduels.registry.registries;

import com.emanuelvini.dvduels.DvDuels;
import com.emanuelvini.dvduels.configuration.ArenaValue;
import com.emanuelvini.dvduels.configuration.ConfigurationValue;
import com.emanuelvini.dvduels.configuration.DatabaseValue;
import com.emanuelvini.dvduels.configuration.MessageValue;
import com.emanuelvini.dvduels.registry.Registry;

public class ConfigurationRegistry extends Registry {



    private final Runnable whenRegistered;

    public ConfigurationRegistry(DvDuels plugin, Runnable whenRegistered) {
        super(plugin);
        this.whenRegistered = whenRegistered;
    }

    @Override
    public void register() {

        MessageValue.instance().load(plugin);
        DatabaseValue.instance().load(plugin);
        ConfigurationValue.instance().load(plugin);
        ArenaValue.instance().load(plugin);

        whenRegistered.run();
    }

    @Override
    public void unregister() {

    }
}
