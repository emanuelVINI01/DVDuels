package com.emanuelvini.dvduels.registry.registries;

import com.emanuelvini.dvduels.DvDuels;
import com.emanuelvini.dvduels.listeners.DuelListener;
import com.emanuelvini.dvduels.registry.Registry;
import org.bukkit.Bukkit;

public class ListenerRegistry extends Registry {
    public ListenerRegistry(DvDuels plugin) {
        super(plugin);
    }

    @Override
    public void register() {
        Bukkit.getPluginManager().registerEvents(new DuelListener(
                plugin.getDuelManager(), plugin.getPlayerDataRepository()
        ), plugin);
    }

    @Override
    public void unregister() {

    }
}
