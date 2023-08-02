package com.emanuelvini.dvduels.util;

import com.emanuelvini.dvduels.DvDuels;
import lombok.val;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class ConfigurationUtil {

    public static ConfigurationSection loadConfiguration(DvDuels plugin, String path) {
        val configurationFile = new File(plugin.getDataFolder(), path);

        if (configurationFile.exists()) plugin.saveResource(path, false);

        return YamlConfiguration.loadConfiguration(configurationFile);
    }

}
