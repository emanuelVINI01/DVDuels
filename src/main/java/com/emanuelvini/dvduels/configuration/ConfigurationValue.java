package com.emanuelvini.dvduels.configuration;

import com.emanuelvini.dvduels.DvDuels;
import com.emanuelvini.dvduels.models.Kit;
import com.emanuelvini.dvduels.util.ConfigurationUtil;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.val;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.function.Function;

@Getter
@Accessors(fluent = true)
public class ConfigurationValue {

    @Getter
    private static ConfigurationValue instance = new ConfigurationValue();
    
    
    private String defaultKit;
    
    public void load(DvDuels plugin) {
        val configurationSection = ConfigurationUtil.loadConfiguration(plugin, "configuration.yml");

        defaultKit = configurationSection.getString("default_kit");
        
    }

    public static <T> T get(Function<ConfigurationValue, T> function) {
        return function.apply(instance);
    }
    
}
