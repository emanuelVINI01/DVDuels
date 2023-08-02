package com.emanuelvini.dvduels.configuration;

import com.emanuelvini.dvduels.DvDuels;
import com.emanuelvini.dvduels.util.ConfigurationUtil;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.val;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

@Getter
@Accessors(fluent = true)
public class ArenaValue {

    @Getter
    private static ArenaValue instance = new ArenaValue();

    private Location pos1;

    private Location pos2;

    public void load(DvDuels plugin) {

        val arenaSection = ConfigurationUtil.loadConfiguration(plugin, "arena.yml");

        val world = Bukkit.getWorld(arenaSection.getString("world"));

        pos1 = new Location(
                world,
                arenaSection.getDouble("pos1.x"),
                arenaSection.getDouble("pos1.y"),
                arenaSection.getDouble("pos1.z")
        );

        pos2 = new Location(
                world,
                arenaSection.getDouble("pos2.x"),
                arenaSection.getDouble("pos2.y"),
                arenaSection.getDouble("pos2.z")
        );
    }

}
