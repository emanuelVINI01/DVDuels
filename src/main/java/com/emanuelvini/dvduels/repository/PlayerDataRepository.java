package com.emanuelvini.dvduels.repository;

import com.emanuelvini.dvduels.repository.models.PlayerData;
import com.github.benmanes.caffeine.cache.AsyncLoadingCache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.RemovalListener;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.val;

import java.sql.Connection;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;


public class PlayerDataRepository {

    private Connection connection;

    private final AsyncLoadingCache<UUID, PlayerData> cache = Caffeine.newBuilder()
            .maximumSize(500)
            .expireAfterAccess(3, TimeUnit.MINUTES)
            .evictionListener((RemovalListener<UUID, PlayerData>) (key, value, cause) -> {
                if (value == null) return;
                saveData(value);
            })
            .removalListener((key, value, cause) -> {
                if (value == null) return;
                saveData(value);
            })
            .buildAsync((key, executor) -> CompletableFuture.completedFuture(selectData(key)));

    @SneakyThrows
    public PlayerDataRepository(Connection connection) {
        this.connection = connection;

        connection.prepareStatement("CREATE TABLE player_data ( uuid VARCHAR(36), wins INT, losses INT, kills INT, deaths INT, win_streak INT) ").execute();
    }

    @SneakyThrows
    public PlayerData getData(UUID uuid) {
        return cache.get(uuid).get();
    }

    public void saveAll() {
        cache.synchronous().asMap().forEach((k, v) -> {
            saveData(v);
        });
    }

    @SneakyThrows
    private PlayerData createData(UUID uuid) {
        val statement = connection.prepareStatement("INSERT INTO player_data VALUES (?, ?, ?, ?, ?, ?)");

        statement.setString(1, uuid.toString());
        for (int i = 1; i < 5; i++) {
            statement.setInt(i+1, 0);
        }

        statement.executeUpdate();

        return PlayerData
                .generate()
                .uuid(uuid)
                .build();
    }
    @SneakyThrows
    private PlayerData selectData(UUID uuid) {
        val statement = connection.prepareStatement("SELECT * FROM player_data WHERE uuid = ?");

        statement.setString(1, uuid.toString());

        val resultSet = statement.executeQuery();

        if (!resultSet.next()) return createData(uuid);

        return PlayerData
                .generate()
                .uuid(uuid)
                .wins(resultSet.getInt("wins"))
                .losses(resultSet.getInt("losses"))
                .kills(resultSet.getInt("kills"))
                .deaths(resultSet.getInt("deaths"))
                .winStreak(resultSet.getInt("win_streak"))
                .build();
    }

    @SneakyThrows
    private void saveData(PlayerData playerData) {
        val statement = connection.prepareStatement("UPDATE player_data SET wins = ?, losses = ?, kills = ?, deaths = ?, win_streak = ? WHERE uuid = ?");

        statement.setInt(1, playerData.getWins());
        statement.setInt(2, playerData.getLosses());
        statement.setInt(3, playerData.getKills());
        statement.setInt(4, playerData.getWinStreak());
        statement.setString(5, playerData.getUuid().toString());

        statement.executeUpdate();
    }

}
