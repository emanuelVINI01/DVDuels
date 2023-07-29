package com.emanuelvini.dvduels;

import com.emanuelvini.dvduels.configuration.DatabaseValue;
import com.emanuelvini.dvduels.registry.Registry;
import com.emanuelvini.dvduels.registry.registries.ConfigurationRegistry;
import com.emanuelvini.dvduels.repository.PlayerDataRepository;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Getter;
import lombok.val;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.List;

@Getter
public class DvDuels extends JavaPlugin {

    private List<Registry> registries;

    @Getter
    private PlayerDataRepository playerDataRepository;

    public void log(String message) {
        Bukkit.getConsoleSender().sendMessage("§b[DvDuels] " + message);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        //Create the instances of Registries
        registries = List.of(new ConfigurationRegistry(this, () -> {

            try {
                val config = new HikariConfig();

                config.setJdbcUrl(String.format("jdbc:mysql://%s:%d/%s",
                        DatabaseValue.get(DatabaseValue::address),
                        DatabaseValue.get(DatabaseValue::port),
                        DatabaseValue.get(DatabaseValue::database)
                        ));

                config.setUsername(DatabaseValue.get(DatabaseValue::user));
                config.setPassword(DatabaseValue.get(DatabaseValue::password));

                config.addDataSourceProperty("cachePrepStmts", "true");
                config.addDataSourceProperty("prepStmtCacheSize", "250");
                config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

                val hikariDataSource = new HikariDataSource(config);

                val connection = hikariDataSource.getConnection();

                playerDataRepository = new PlayerDataRepository(connection);
            } catch (Exception e) {
                log("§cAn error occurred when connecting to database, try to check your §fdatabase.yml§c.");
                e.printStackTrace();
                Bukkit.getPluginManager().disablePlugin(this);
            }
        }));
    }

    @Override
    public void onEnable() {
        super.onEnable();
        log("§cEnabling plugin...");

        registries.forEach(Registry::register);

        log("§aPlugin enabled.");
    }
}
