package com.emanuelvini.dvduels;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class DvDuels extends JavaPlugin {

    public void log(String message) {
        Bukkit.getConsoleSender().sendMessage("§b[DvDuels] " + message);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        log("§cLoading plugin...");
    }
}
