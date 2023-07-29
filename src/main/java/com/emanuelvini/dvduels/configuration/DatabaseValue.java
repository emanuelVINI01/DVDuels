package com.emanuelvini.dvduels.configuration;

import com.emanuelvini.dvduels.DvDuels;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.val;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.function.Function;

import static com.emanuelvini.dvduels.util.ColorUtil.parseColors;

@Getter
@Accessors(fluent = true)

public class DatabaseValue {

    @Getter
    private static DatabaseValue instance = new DatabaseValue();

    private String address;

    private int port;

    private String user;

    private String password;

    private String database;



    public void load(DvDuels plugin) {

        //Save its default if not exists and load to YAML.
        val databaseFile = new File(plugin.getDataFolder(), "database.yml");

        if (databaseFile.exists()) plugin.saveResource("database.yml", false);

        val databaseSection = YamlConfiguration.loadConfiguration(databaseFile);

        //Load to variables (I suggested to use (https://github.com/henrysaantos/configuration-injector/) because this.

        address = databaseSection.getString("address");
        port = databaseSection.getInt("port");
        user = databaseSection.getString("user");
        password = databaseSection.getString("password");
        database = databaseSection.getString("database");


    }

    public static <T> T get(Function<DatabaseValue, T> function) {
        return function.apply(instance);
    }

}